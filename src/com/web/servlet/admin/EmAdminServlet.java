package com.web.servlet.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.web.db.DbUtils;
import com.web.util.ExcelCommon;
import com.web.util.PhotoUpload;
import com.web.util.PushUtils;
import com.web.util.Putil;
import com.web.util.StringUtils;

public class EmAdminServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 8459048427979079912L;
	private String auth81 = "";
	private String auth82 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth82.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);
		String key_usertype = Putil.getString(request.getParameter("key_usertype"));
		request.setAttribute("key_usertype", key_usertype);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		//权限列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from  em_authority p where p.ctype=2 order by p.authorityid");
		request.setAttribute("aList", resultRows);
		//公司列表
		List<Map<String, Object>> cRows = DbUtils.query("select * from em_company p order by companyid desc");
		request.setAttribute("cList", cRows);

		forward(request, response, "/admin/AdminAdd.jsp");

		
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth82.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_usertype = Putil.getString(request.getParameter("key_usertype"));
		request.setAttribute("key_usertype", key_usertype);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		String username = Putil.getString(request.getParameter("username")) ;
		String account = Putil.getString(request.getParameter("account")) ;
		int companyid = Putil.getInt(request.getParameter("companyid")) ;
		int usertype = Putil.getInt(request.getParameter("usertype")) ;
		int authorityid = Putil.getInt(request.getParameter("authorityid")) ;
		String password = Putil.getString(request.getParameter("password")) ;
		String sex = Putil.getString(request.getParameter("sex")) ;
		String age = Putil.getString(request.getParameter("age")) ;
		String identity = Putil.getString(request.getParameter("identity")) ;
		String mobile = Putil.getString(request.getParameter("mobile")) ;
		String isvalid = Putil.getString(request.getParameter("isvalid")) ;
		String tel = Putil.getString(request.getParameter("tel")) ;
		String addr = Putil.getString(request.getParameter("addr")) ;
		String note = Putil.getString(request.getParameter("note")) ;
		String seq = Putil.getString(request.getParameter("seq")) ;
		String photo = Putil.getString(request.getParameter("photo")) ;
		if(seq.length()==0) seq = "1000" ;
		
		Map<String, Object> userTest = DbUtils.queryOne("select p.* from em_user p where p.account='"+account + "' or p.mobile='" + mobile +"'");
		if(userTest!=null && Putil.getInt(userTest.get("userid"))>0) {
    		this.prompt(response, "账号已存在，请联系管理员！");
    		return;
		}
		
		Date date = new Date();
		String filename = "p" + date.getTime()+"";
		boolean fileSuc = false ;
		if(photo.length()>20) {
			//data:image/png;base64,
			int iS = 0, iE = 0 ;
			iS = photo.indexOf("image/") ;
			iE = photo.indexOf(";", iS) ;
			String fileEx = "" ;
			if(iS>0 && iE>iS) {
				fileEx = photo.substring(iS+6, iE) ;
				if(fileEx.equals("jpeg")) fileEx = "jpg" ;
				filename += "." + fileEx ;
				iS = photo.indexOf("base64,") ;
				fileSuc = PhotoUpload.savePhoto(request, photo.substring(iS + 7), filename);
				
			}
		} else if(photo.length()>10) {
			fileSuc = true;
			filename = photo;
		}
		StringBuilder select = new StringBuilder("insert into em_user (username,account,userno,password,usertype,companyid,authorityid,sex,age,identity,mobile,photo,isvalid,isregist,openid,createdate,tel,addr,note,devicetoken,devicetokendate,seq) values ('" + username + "'"
				+",'" + account + "'"
				+",''"
				+",'" + password + "'"
				+",'" + usertype + "'"
				+"," + companyid + ""
				+"," + authorityid + ""
				+",'" + sex + "'"
				+",'" + age + "'"
				+",'" + identity + "'"
				+",'" + mobile + "'"
				+ ",'" + (fileSuc?filename:"") + "'"
				+"," + isvalid + ""
				+",0"
				+",''"
				+",SYSDATE()"
				+",'" + tel + "'"
				+",'" + addr + "'"
				+",'" + note + "'"
				+",''"
				+",SYSDATE()"
				+"," + seq + ""
				+ ")"
				
				);
		DbUtils.save(select.toString());
		redirect(request, response, "/emAdmin.do?method=list&uf_parentid="+uf_parentid +"&key_usertype="+key_usertype+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}

	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_usertype = Putil.getString(request.getParameter("key_usertype"));
		request.setAttribute("key_usertype", key_usertype);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		String userid = Putil.getString(request.getParameter("userid")) ;
		if(userid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select * from em_user p where p.userid="+userid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/AdminView.jsp");
		
	}

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);
		
		String updateBatch = Putil.getString(request.getParameter("updateBatch")) ;
		if(updateBatch.equals("1")) {
			forward(request, response, "/admin/AdminUpdateBatch.jsp");
			return;
		}
		


		String key_usertype = Putil.getString(request.getParameter("key_usertype"));
		request.setAttribute("key_usertype", key_usertype);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");
		
		//权限列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from  em_authority p where p.ctype=2 order by p.authorityid");
		request.setAttribute("aList", resultRows);
		//公司列表
		List<Map<String, Object>> cRows = DbUtils.query("select * from em_company p order by companyid desc");
		request.setAttribute("cList", cRows);

		String userid = Putil.getString(request.getParameter("userid")) ;
		if(userid.length()==0) {
    		this.prompt(response, "资料错误，请联系管理员！");
    		return ;
		}
		Map<String, Object> row1 = DbUtils.queryOne("select p.* from em_user p where p.userid="+userid
				+ (userTYPE==2?"":" and p.companyid="+companyID)
				);
		if(row1==null) {
	   		this.prompt(response, "资料错误，请联系管理员！.");
    		return ;
			
		}
		request.setAttribute("item", row1);
		
		forward(request, response, "/admin/AdminEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth82.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);
		String key_usertype = Putil.getString(request.getParameter("key_usertype"));
		request.setAttribute("key_usertype", key_usertype);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");
		
		int userid = Putil.getInt(request.getParameter("userid")) ;
		String username = Putil.getString(request.getParameter("username")) ;
		String account = Putil.getString(request.getParameter("account")) ;
		String userno = Putil.getString(request.getParameter("userno")) ;
		String companyid = Putil.getString(request.getParameter("companyid")) ;
		String usertype = Putil.getString(request.getParameter("usertype")) ;
		String authorityid = Putil.getString(request.getParameter("authorityid")) ;
		String password = Putil.getString(request.getParameter("password")) ;
		String sex = Putil.getString(request.getParameter("sex")) ;
		String age = Putil.getString(request.getParameter("age")) ;
		String identity = Putil.getString(request.getParameter("identity")) ;
		String mobile = Putil.getString(request.getParameter("mobile")) ;
		String isvalid = Putil.getString(request.getParameter("isvalid")) ;
		String tel = Putil.getString(request.getParameter("tel")) ;
		String addr = Putil.getString(request.getParameter("addr")) ;
		String note = Putil.getString(request.getParameter("note")) ;
		String seq = Putil.getString(request.getParameter("seq")) ;
		String photo = Putil.getString(request.getParameter("photo")) ;
		if(seq.length()==0) seq = "1000" ;
		
		Map<String, Object> userTest = DbUtils.queryOne("select p.* from em_user p where (p.account='"+account + "' or p.mobile='" + mobile +"') and userid<>" + userid);
		if(userTest!=null && Putil.getInt(userTest.get("userid"))>0) {
    		this.prompt(response, "账号已存在，请联系管理员！");
    		return;
		}

		Date date = new Date();
		String filename = "p" + date.getTime()+"";
		boolean fileSuc = false ;
		if(photo.length()>20) {
			//data:image/png;base64,
			int iS = 0, iE = 0 ;
			iS = photo.indexOf("image/") ;
			iE = photo.indexOf(";", iS) ;
			String fileEx = "" ;
			if(iS>0 && iE>iS) {
				fileEx = photo.substring(iS+6, iE) ;
				if(fileEx.equals("jpeg")) fileEx = "jpg" ;
				filename += "." + fileEx ;
				iS = photo.indexOf("base64,") ;
				fileSuc = PhotoUpload.savePhoto(request, photo.substring(iS + 7), filename);
				
			}
		} else if(photo.length()>10) {
			fileSuc = true;
			filename = photo;
		}
		StringBuilder select = new StringBuilder("update em_user set "
				+ "username = '" + username + "'"
				+ (companyid.length()>0?",companyid = " + companyid + "":"")
				+ (usertype.length()>0?",usertype = " + usertype + "":"")
				+ (authorityid.length()>0?",authorityid = " + authorityid + "":"")
				+ ",account = '" + account + "'"
				+ (password.length()>0?",password = '" + password + "'":"")
				+ ",userno = '" + userno + "'"
				+",sex='" + sex + "'"
				+",age='" + age + "'"
				+",identity='" + identity + "'"
				+",mobile='" + mobile + "'"
				+ (fileSuc?",photo = '" + filename + "'":"")
				+",isvalid=" + isvalid + ""
				+",tel='" + tel + "'"
				+",addr='" + addr + "'"
				+",note='" + note + "'"
				+",seq=" + seq + ""
				+",isdelete=0"
				+ " where userid=" + userid + "" 
				);

		int result = DbUtils.save(select.toString());



		redirect(request, response, "/emAdmin.do?method=list&uf_parentid="+uf_parentid +"&key_usertype="+key_usertype+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
//		request.setAttribute("uf_parentid", uf_parentid);
		String report = Putil.getString(request.getParameter("report")) ;
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

		try {
			long totalCount = 0 ;
			String key_usertype = Putil.getString(request.getParameter("key_usertype"));
			request.setAttribute("key_usertype", key_usertype);
			int key_isvalid = 0 ;
			String k_isvalid = Putil.getString(request.getParameter("key_isvalid"));
			if(k_isvalid.length()==0)
				key_isvalid = 99;
			else
				key_isvalid = Putil.getInt(k_isvalid);
			request.setAttribute("key_isvalid", key_isvalid);
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			
			//公司列表
			List<Map<String, Object>> cRows = DbUtils.query("select * from em_company p order by companyid desc");
			request.setAttribute("cList", cRows);

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 20 ;
			if(report.equals("1")) {
				s = 1;
				m = 1000;
			}

			StringBuilder countSql = new StringBuilder("select count(p.userid) as total from em_user p where p.isdelete=0"
					+ (userTYPE==2?"":" and p.companyid="+companyID)
					+ (keyword.length()==0?"":" and (p.username like '%"+keyword+"%' or p.account like '%"+keyword+"%' or p.mobile like '%"+keyword+"%')")
					+ (key_usertype.length()==0?" and p.usertype<>1":" and p.usertype=" + key_usertype)
					+ (key_isvalid==99?"":" and p.isvalid=" + key_isvalid)
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
				if(s>1 && totalCount<=m) s = 1;
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_user p where p.isdelete=0"
					+ (userTYPE==2?"":" and p.companyid="+companyID)
					+ (keyword.length()==0?"":" and (p.username like '%"+keyword+"%' or p.account like '%"+keyword+"%' or p.mobile like '%"+keyword+"%')")
					+ (key_usertype.length()==0?" and p.usertype<>1":" and p.usertype=" + key_usertype)
					+ (key_isvalid==99?"":" and p.isvalid=" + key_isvalid)
					+ " order by p.seq desc limit " + (s-1)*m + "," + m + ""
				);
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("keyword", keyword);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");
				
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		if(report.equals("1")) {
			reportExcel2(request, response, resultRows) ;
		} else
			forward(request, response, "/admin/AdminList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth82.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_usertype = Putil.getString(request.getParameter("key_usertype"));
		request.setAttribute("key_usertype", key_usertype);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String userid = Putil.getString(request.getParameter("userid"));
			String t = Putil.getString(request.getParameter("t"));
			StringBuilder select = new StringBuilder("update em_user set isdelete=1 where userid =" + userid + " and companyid="+companyID + "");
			DbUtils.save(select.toString());

			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emAdmin.do?method=list&uf_parentid="+uf_parentid +"&key_usertype="+key_usertype+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
	public static void deleteUser(int companyID, String users) {
		ArrayList<String> batchSql = new ArrayList<String>();
		StringBuilder select = new StringBuilder("delete from em_user where userid in (" + users + ") and companyid="+companyID + "");
		batchSql.add(select.toString());
		
		DbUtils.updateBatch(batchSql);
	}

	
	public static void deleteUserByPosition(int companyID, String users) {
		ArrayList<String> batchSql = new ArrayList<String>();
		StringBuilder select = new StringBuilder("delete from em_user where userid in (" + users + ") and companyid="+companyID + "");
		batchSql.add(select.toString());
		
		DbUtils.updateBatch(batchSql);
	}


    public void reportExcel2(HttpServletRequest req, HttpServletResponse res
    		, List<Map<String, Object>> userList) {
    	String title[] = {"职位ID","职员ID","职员姓名","职员编号","职员类别","手机","性别","年龄","身份证","家庭电话","地址","后台密码","是否有效","照片","备注","顺序号"};
		try {
			
			XSSFWorkbook wb = new XSSFWorkbook(); 
			XSSFSheet s = wb.createSheet(); 
			ExcelCommon.initStyle(wb);
			wb.setSheetName(0, "职员资料"); 
			int rownum = 0 ;
			
			XSSFRow row = s.createRow(rownum);
			s.setColumnWidth(0, 256*10);
			s.setColumnWidth(1, 256*10);
			s.setColumnWidth(2, 256*20);
			s.setColumnWidth(3, 256*20);
			s.setColumnWidth(4, 256*10);
			s.setColumnWidth(5, 256*20);
			s.setColumnWidth(6, 256*10);
			s.setColumnWidth(7, 256*10);
			s.setColumnWidth(8, 256*30);
			s.setColumnWidth(9, 256*10);
			s.setColumnWidth(10, 256*30);
			s.setColumnWidth(11, 256*10);
			s.setColumnWidth(12, 256*10);
			s.setColumnWidth(13, 256*10);
			s.setColumnWidth(14, 256*10);
			s.setColumnWidth(15, 256*10);
			
			row.setHeight((short)600);
			rownum++;
			row = s.createRow(rownum);
			row.setHeight((short)800);
			ExcelCommon.setCell(s, row, 0, "职员资料", ExcelCommon.titleStyle1, ExcelCommon.font16Bold);
			s.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rownum,rownum,0,title.length-1));
			
			rownum++;
			row = s.createRow(rownum);
			row.setHeight((short)320);
			ExcelCommon.setCell(s, row, 0, "时间：" + Putil.format4(), ExcelCommon.leftStyle1, ExcelCommon.font12);
			s.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rownum,rownum,0,title.length-1));
			
			rownum++;
        	row = s.createRow(rownum);
        	for(int i=0;i<title.length;i++) {
        		ExcelCommon.setCell(s, row, i, title[i]);
    		}
    		
        	if(userList!=null) {
            	for(int i=0;i<userList.size();i++) {
            		Map<String, Object> user = userList.get(i);
            		    	
               		rownum++;
	            	row = s.createRow(rownum);
	            	int col=0;
	        		ExcelCommon.setCell(s, row, col, Putil.getString(user.get("positionid")));
	        		col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("userid")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("username")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("userno")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getInt(user.get("usertype"))==1?"管理员":"保安");
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("mobile")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("sex")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("age")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("identity")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("tel")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("addr")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("password")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getInt(user.get("isvalid"))==1?"有效":"无效");
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("photo")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("note")));
	            	col++;
	            	ExcelCommon.setCell(s, row, col, Putil.getString(user.get("seq")));
	            	
            	}
    			        		
        	}
        	
        	
        	//导入说明
			XSSFSheet s1 = wb.createSheet(); 
			wb.setSheetName(1, "导入说明"); 
			rownum = 0 ;
			
			s1.setColumnWidth(0, 256*20);
			s1.setColumnWidth(1, 256*80);
			s1.setColumnWidth(2, 256*20);

			row = s1.createRow(rownum);
        	int col=0;
    		ExcelCommon.setCell(s, row, col, "");
    		col++;
    		ExcelCommon.setCell(s, row, col, "请保留表头格式，不要修改，第5行开始为有效数据，中间不要有空行。", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "");
    		col++;
    		ExcelCommon.setCell(s, row, col, "职位ID、职员姓名、手机号码为必填项，其他可不填写。", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "职位ID");
    		col++;
    		ExcelCommon.setCell(s, row, col, "对应后台“公司架构管理”的职位ID进行填写。", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "职员ID");
    		col++;
    		ExcelCommon.setCell(s, row, col, "不需要填写。", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "职员姓名");
    		col++;
    		ExcelCommon.setCell(s, row, col, "必填项", ExcelCommon.leftStyle);
			

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "职员类别");
    		col++;
    		ExcelCommon.setCell(s, row, col, "填写“管理员”或“保安”，不填默认为保安", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "手机");
    		col++;
    		ExcelCommon.setCell(s, row, col, "必填项", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "性别");
    		col++;
    		ExcelCommon.setCell(s, row, col, "填写“男”或“女”，不填默认为男", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "是否有效");
    		col++;
    		ExcelCommon.setCell(s, row, col, "填写“有效”或“无效”，不填默认为有效", ExcelCommon.leftStyle);

    		rownum++;
			row = s1.createRow(rownum);
        	col=0;
    		ExcelCommon.setCell(s, row, col, "照片");
    		col++;
    		ExcelCommon.setCell(s, row, col, "填写图片名，例如：XXXX.jpg。图片尺寸建议100X150像素。", ExcelCommon.leftStyle);
    		
        	res.setContentType("application/binary;charset=ISO8859_1");  
            res.setHeader("Content-Disposition","attachment;filename=user.xlsx");
        	OutputStream o = res.getOutputStream();
			wb.write(o) ;
			o.flush();
			o.close();
        	
		} catch(Exception e) {
			e.printStackTrace(System.out) ;
		}

    	
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth81 = checkAuthority(45, request)!=null?"1":"";
    	auth82 = checkAuthority(46, request)!=null?"1":"";
		request.setAttribute("auth81", auth81);
		request.setAttribute("auth82", auth82);

    	if(auth81.equals("") && auth82.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    }
}

