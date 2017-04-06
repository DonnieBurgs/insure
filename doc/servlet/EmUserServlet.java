package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emUser</servlet-name>
		<servlet-class>com.web.servlet.admin.EmUserServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emUser</servlet-name>
		<url-pattern>/emUser.do</url-pattern>
	</servlet-mapping>

 */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.*;

public class EmUserServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449284224L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth82 = request.getAttribute("auth82");
    	if(auth82.equals("")) {
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

		forward(request, response, "/admin/UserAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth82 = request.getAttribute("auth82");
    	if(auth82.equals("")) {
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
			String account = Putil.getString(request.getParameter("account"));
			String password = Putil.getString(request.getParameter("password"));
			String username = Putil.getString(request.getParameter("username"));
			String nickname = Putil.getString(request.getParameter("nickname"));
			String userno = Putil.getString(request.getParameter("userno"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			int usertype = Putil.getInt(request.getParameter("usertype"));
			int parentid = Putil.getInt(request.getParameter("parentid"));
			String sex = Putil.getString(request.getParameter("sex"));
			String age = Putil.getString(request.getParameter("age"));
			String identity = Putil.getString(request.getParameter("identity"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			int isregist = Putil.getInt(request.getParameter("isregist"));
			String openid = Putil.getString(request.getParameter("openid"));
			String qrurl = Putil.getString(request.getParameter("qrurl"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String tel = Putil.getString(request.getParameter("tel"));
			String addr = Putil.getString(request.getParameter("addr"));
			String note = Putil.getString(request.getParameter("note"));
			float balance = Putil.getFloat(request.getParameter("balance"));
			String devicetoken = Putil.getString(request.getParameter("devicetoken"));
			String devicetokendate = Putil.getString(request.getParameter("devicetokendate"));
			float lng = Putil.getFloat(request.getParameter("lng"));
			float lat = Putil.getFloat(request.getParameter("lat"));
			String latlngdate = Putil.getString(request.getParameter("latlngdate"));
			String deviceid = Putil.getString(request.getParameter("deviceid"));
			int seq = Putil.getInt(request.getParameter("seq"));
			int authorityid = Putil.getInt(request.getParameter("authorityid"));
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
			
			StringBuilder select = new StringBuilder("insert into em_user (account,password,username,nickname,userno,companyid,usertype,parentid,sex,age,identity,mobile,isvalid,photo,isregist,openid,qrurl,createdate,tel,addr,note,balance,devicetoken,devicetokendate,lng,lat,latlngdate,deviceid,seq,authorityid,isdelete) values ("
				+ "'" + account.replace("'", "''") + "'"
				+ ",'" + password.replace("'", "''") + "'"
				+ ",'" + username.replace("'", "''") + "'"
				+ ",'" + nickname.replace("'", "''") + "'"
				+ ",'" + userno.replace("'", "''") + "'"
				+ "," + companyid + ""
				+ "," + usertype + ""
				+ "," + parentid + ""
				+ ",'" + sex.replace("'", "''") + "'"
				+ ",'" + age.replace("'", "''") + "'"
				+ ",'" + identity.replace("'", "''") + "'"
				+ ",'" + mobile.replace("'", "''") + "'"
				+ "," + isvalid + ""
				+ ",'" + (fileSuc[0].length()>0?fileSuc[0]:"") + "'"
				+ "," + isregist + ""
				+ ",'" + openid.replace("'", "''") + "'"
				+ ",'" + qrurl.replace("'", "''") + "'"
				+ ",'" + createdate.replace("'", "''") + "'"
				+ ",'" + tel.replace("'", "''") + "'"
				+ ",'" + addr.replace("'", "''") + "'"
				+ ",'" + note.replace("'", "''") + "'"
				+ "," + balance + ""
				+ ",'" + devicetoken.replace("'", "''") + "'"
				+ ",'" + devicetokendate.replace("'", "''") + "'"
				+ "," + lng + ""
				+ "," + lat + ""
				+ ",'" + latlngdate.replace("'", "''") + "'"
				+ ",'" + deviceid.replace("'", "''") + "'"
				+ "," + seq + ""
				+ "," + authorityid + ""
				+ "," + isdelete + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emUser.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String userid = Putil.getString(request.getParameter("userid")) ;
		if(userid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_user p where p.userid="+userid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/UserEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth82 = request.getAttribute("auth82");
    	if(auth82.equals("")) {
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
			String userid = Putil.getString(request.getParameter("userid")) ;
			String account = Putil.getString(request.getParameter("account"));
			String password = Putil.getString(request.getParameter("password"));
			String username = Putil.getString(request.getParameter("username"));
			String nickname = Putil.getString(request.getParameter("nickname"));
			String userno = Putil.getString(request.getParameter("userno"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			int usertype = Putil.getInt(request.getParameter("usertype"));
			int parentid = Putil.getInt(request.getParameter("parentid"));
			String sex = Putil.getString(request.getParameter("sex"));
			String age = Putil.getString(request.getParameter("age"));
			String identity = Putil.getString(request.getParameter("identity"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			int isregist = Putil.getInt(request.getParameter("isregist"));
			String openid = Putil.getString(request.getParameter("openid"));
			String qrurl = Putil.getString(request.getParameter("qrurl"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String tel = Putil.getString(request.getParameter("tel"));
			String addr = Putil.getString(request.getParameter("addr"));
			String note = Putil.getString(request.getParameter("note"));
			float balance = Putil.getFloat(request.getParameter("balance"));
			String devicetoken = Putil.getString(request.getParameter("devicetoken"));
			String devicetokendate = Putil.getString(request.getParameter("devicetokendate"));
			float lng = Putil.getFloat(request.getParameter("lng"));
			float lat = Putil.getFloat(request.getParameter("lat"));
			String latlngdate = Putil.getString(request.getParameter("latlngdate"));
			String deviceid = Putil.getString(request.getParameter("deviceid"));
			int seq = Putil.getInt(request.getParameter("seq"));
			int authorityid = Putil.getInt(request.getParameter("authorityid"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));

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
			
			StringBuilder select = new StringBuilder("update em_user set "
					+ "account='" + account.replace("'", "''") + "'"
					+ ",password='" + password.replace("'", "''") + "'"
					+ ",username='" + username.replace("'", "''") + "'"
					+ ",nickname='" + nickname.replace("'", "''") + "'"
					+ ",userno='" + userno.replace("'", "''") + "'"
					+ ",companyid=" + companyid + ""
					+ ",usertype=" + usertype + ""
					+ ",parentid=" + parentid + ""
					+ ",sex='" + sex.replace("'", "''") + "'"
					+ ",age='" + age.replace("'", "''") + "'"
					+ ",identity='" + identity.replace("'", "''") + "'"
					+ ",mobile='" + mobile.replace("'", "''") + "'"
					+ ",isvalid=" + isvalid + ""
					+ (fileSuc0?",photo = '" + filename0 + "'":"")
					+ ",isregist=" + isregist + ""
					+ ",openid='" + openid.replace("'", "''") + "'"
					+ ",qrurl='" + qrurl.replace("'", "''") + "'"
					+ ",createdate='" + createdate.replace("'", "''") + "'"
					+ ",tel='" + tel.replace("'", "''") + "'"
					+ ",addr='" + addr.replace("'", "''") + "'"
					+ ",note='" + note.replace("'", "''") + "'"
					+ ",balance=" + balance + ""
					+ ",devicetoken='" + devicetoken.replace("'", "''") + "'"
					+ ",devicetokendate='" + devicetokendate.replace("'", "''") + "'"
					+ ",lng=" + lng + ""
					+ ",lat=" + lat + ""
					+ ",latlngdate='" + latlngdate.replace("'", "''") + "'"
					+ ",deviceid='" + deviceid.replace("'", "''") + "'"
					+ ",seq=" + seq + ""
					+ ",authorityid=" + authorityid + ""
					+ ",isdelete=" + isdelete + ""
				+ " where userid=" + userid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emUser.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.userid) as total from em_user p where p.userid>=0"
					+ (keyword.length()>0?" and p.username like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_user p where p.userid>=0"
					+ (keyword.length()>0?" and p.username like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/UserList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth82 = request.getAttribute("auth82");
    	if(auth82.equals("")) {
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
			String userid = Putil.getString(request.getParameter("userid"));

			StringBuilder select = new StringBuilder("delete from em_user "
					+ " where userid=" + userid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emUser.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth81 = checkAuthority(81, request)!=null?"1":"";
    	String auth82 = checkAuthority(82, request)!=null?"1":"";
		request.setAttribute("auth81", auth81);
		request.setAttribute("auth82", auth82);
    	if(auth81.equals("") && auth82.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

