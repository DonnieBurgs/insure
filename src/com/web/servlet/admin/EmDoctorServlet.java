package com.web.servlet.admin;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.*;

public class EmDoctorServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488465992977L;
	private String auth45 = "";
	private String auth46 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth46.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		//公司列表
		List<Map<String, Object>> companyList = DbUtils.query("select p.* from em_company p where p.companytypeid=1 order by p.companyid desc");
		request.setAttribute("companyList", companyList);
		//医护类型列表
		List<Map<String, Object>> doctortypeList = DbUtils.query("select p.* from em_doctortype p order by p.seq desc");
		request.setAttribute("doctortypeList", doctortypeList);
		//科室列表
		List<Map<String, Object>> specialtyList = DbUtils.query("select p.* from em_specialty p order by p.seq desc");
		request.setAttribute("specialtyList", specialtyList);

		forward(request, response, "/admin/DoctorAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth46.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		Map<String, Object> admin = LoginManager.getUser(request);
		if(admin==null) {
			return ;
		}
		try {
			String doctorname = Putil.getString(request.getParameter("doctorname"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			int userid = Putil.getInt(request.getParameter("userid"));
			int doctortypeid = Putil.getInt(request.getParameter("doctortypeid"));
			int specialtyid = Putil.getInt(request.getParameter("specialtyid"));
			String expert = Putil.getString(request.getParameter("expert"));
			String note = Putil.getString(request.getParameter("note"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			float price = Putil.getFloat(request.getParameter("price"));
			int seq = Putil.getInt(request.getParameter("seq"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));

			Date date = new Date();

			String filename0 = "p0" + date.getTime()+"";

			boolean fileSuc0 = false ;

			String fileSuc[] = {""};

			int fileCount = 0 ;

			if(photo0.length()>10) {
				int iS = 0, iE = 0 ;
				iS = photo0.indexOf("image/") ;
				iE = photo0.indexOf(";", iS) ;
				String fileEx = "" ;
				if(iS>0 && iE>iS) {
					fileEx = photo0.substring(iS+6, iE) ;
					if(fileEx.equals("jpeg")) fileEx = "jpg" ;
					filename0 += "." + fileEx ;
					iS = photo0.indexOf("base64,") ;
					fileSuc0 = PhotoUpload.savePhoto(request, photo0.substring(iS + 7), filename0);
					if(fileSuc0) {
						fileSuc[fileCount] = filename0;
						fileCount++;
					}
				}
			}
			
			StringBuilder select = new StringBuilder("insert into em_doctor (doctorname,mobile,companyid,userid,doctortypeid,specialtyid,expert,note,photo,price,seq,isdelete) values ("
				+ "'" + doctorname.replace("'", "''") + "'"
				+ ",'" + mobile.replace("'", "''") + "'"
				+ "," + companyid + ""
				+ "," + userid + ""
				+ "," + doctortypeid + ""
				+ "," + specialtyid + ""
				+ ",'" + expert.replace("'", "''") + "'"
				+ ",'" + note.replace("'", "''") + "'"
				+ ",'" + (fileSuc[0].length()>0?fileSuc[0]:"") + "'"
				+ "," + price + ""
				+ "," + seq + ""
				+ ",0"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emDoctor.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		String doctorid = Putil.getString(request.getParameter("doctorid")) ;
		if(doctorid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_doctor p where p.doctorid="+doctorid);
			request.setAttribute("item", row);
		}
		
		//公司列表
		List<Map<String, Object>> companyList = DbUtils.query("select p.* from em_company p where p.companytypeid=1 order by p.companyid desc");
		request.setAttribute("companyList", companyList);
		//医护类型列表
		List<Map<String, Object>> doctortypeList = DbUtils.query("select p.* from em_doctortype p order by p.seq desc");
		request.setAttribute("doctortypeList", doctortypeList);
		//科室列表
		List<Map<String, Object>> specialtyList = DbUtils.query("select p.* from em_specialty p order by p.seq desc");
		request.setAttribute("specialtyList", specialtyList);

		forward(request, response, "/admin/DoctorEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth46.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String doctorid = Putil.getString(request.getParameter("doctorid")) ;
			String doctorname = Putil.getString(request.getParameter("doctorname"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			int userid = Putil.getInt(request.getParameter("userid"));
			int doctortypeid = Putil.getInt(request.getParameter("doctortypeid"));
			int specialtyid = Putil.getInt(request.getParameter("specialtyid"));
			String expert = Putil.getString(request.getParameter("expert"));
			String note = Putil.getString(request.getParameter("note"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			float price = Putil.getFloat(request.getParameter("price"));
			int seq = Putil.getInt(request.getParameter("seq"));
//			int isdelete = Putil.getInt(request.getParameter("isdelete"));

			Date date = new Date();

			String filename0 = "p0" + date.getTime()+"";

			boolean fileSuc0 = false ;

			if(photo0.length()>10) {
				int iS = 0, iE = 0 ;
				iS = photo0.indexOf("image/") ;
				iE = photo0.indexOf(";", iS) ;
				String fileEx = "" ;
				if(iS>0 && iE>iS) {
					fileEx = photo0.substring(iS+6, iE) ;
					if(fileEx.equals("jpeg")) fileEx = "jpg" ;
					filename0 += "." + fileEx ;
					iS = photo0.indexOf("base64,") ;
					fileSuc0 = PhotoUpload.savePhoto(request, photo0.substring(iS + 7), filename0);
				}
			}
			
			StringBuilder select = new StringBuilder("update em_doctor set "
					+ "doctorname='" + doctorname.replace("'", "''") + "'"
					+ ",mobile='" + mobile.replace("'", "''") + "'"
					+ ",companyid=" + companyid + ""
					+ ",userid=" + userid + ""
					+ ",doctortypeid=" + doctortypeid + ""
					+ ",specialtyid=" + specialtyid + ""
					+ ",expert='" + expert.replace("'", "''") + "'"
					+ ",note='" + note.replace("'", "''") + "'"
					+ (fileSuc0?",photo = '" + filename0 + "'":"")
					+ ",price=" + price + ""
					+ ",seq=" + seq + ""
				+ " where doctorid=" + doctorid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emDoctor.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		try {
			long totalCount = 0 ;
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.doctorid) as total from em_doctor p, em_company c, em_doctortype dt, em_specialty s where p.doctorid>=0"
					+ " and p.companyid=c.companyid and p.doctortypeid=dt.doctortypeid and p.specialtyid=s.specialtyid"
					+ (keyword.length()>0?" and p.doctorname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.*,c.companyname,dt.doctortypename,s.specialtyname from em_doctor p, em_company c, em_doctortype dt, em_specialty s where p.doctorid>=0"
					+ " and p.companyid=c.companyid and p.doctortypeid=dt.doctortypeid and p.specialtyid=s.specialtyid"
					+ (keyword.length()>0?" and p.doctorname like '%" + keyword + "%'":"")
					+ " order by p.seq desc limit " + (s-1)*m + "," + m + ""
				);
			
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("keyword", keyword);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		forward(request, response, "/admin/DoctorList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth46.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String doctorid = Putil.getString(request.getParameter("doctorid"));

			StringBuilder select = new StringBuilder("delete from em_doctor "
					+ " where doctorid=" + doctorid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emDoctor.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth45 = checkAuthority(45, request)!=null?"1":"";
    	auth46 = checkAuthority(46, request)!=null?"1":"";
		request.setAttribute("auth45", auth45);
		request.setAttribute("auth46", auth46);
    	if(auth45.equals("") && auth46.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

