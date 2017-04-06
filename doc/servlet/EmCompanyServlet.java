package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emCompany</servlet-name>
		<servlet-class>com.web.servlet.admin.EmCompanyServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emCompany</servlet-name>
		<url-pattern>/emCompany.do</url-pattern>
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

public class EmCompanyServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449284155L;
	
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

		forward(request, response, "/admin/CompanyAdd.jsp");
	
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
			String companyname = Putil.getString(request.getParameter("companyname"));
			String contact = Putil.getString(request.getParameter("contact"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			int companytypeid = Putil.getInt(request.getParameter("companytypeid"));
			String startdate = Putil.getString(request.getParameter("startdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			float balance = Putil.getFloat(request.getParameter("balance"));
			String note = Putil.getString(request.getParameter("note"));
			String auditnote = Putil.getString(request.getParameter("auditnote"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			String photo1 = Putil.getString(request.getParameter("photo1")) ;

			Date date = new Date();

			String filename0 = "p0" + date.getTime()+"";
			String filename1 = "p1" + date.getTime()+"";

			boolean fileSuc0 = false ;
			boolean fileSuc1 = false ;

			String fileSuc[] = {"",""};

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
			if(photo1.length()>10) {
				int iS = 0, iE = 0 ;
				iS = photo1.indexOf("image/") ;
				iE = photo1.indexOf(";", iS) ;
				String fileEx = "" ;
				if(iS>0 && iE>iS) {
					fileEx = photo1.substring(iS+6, iE) ;
					if(fileEx.equals("jpeg")) fileEx = "jpg" ;
					filename1 += "." + fileEx ;
					iS = photo1.indexOf("base64,") ;
					fileSuc1 = PhotoUpload.savePhoto(request, photo1.substring(iS + 7), filename1);
					if(fileSuc1) {
						fileSuc[fileCount] = filename1;
						fileCount++;
					}
				}
			}
			
			StringBuilder select = new StringBuilder("insert into em_company (companyname,contact,mobile,areaid,addr,isvalid,companytypeid,startdate,expdate,createdate,balance,note,auditnote,photo1,photo2) values ("
				+ "'" + companyname.replace("'", "''") + "'"
				+ ",'" + contact.replace("'", "''") + "'"
				+ ",'" + mobile.replace("'", "''") + "'"
				+ "," + areaid + ""
				+ ",'" + addr.replace("'", "''") + "'"
				+ "," + isvalid + ""
				+ "," + companytypeid + ""
				+ ",'" + startdate.replace("'", "''") + "'"
				+ ",'" + expdate.replace("'", "''") + "'"
				+ ",'" + createdate.replace("'", "''") + "'"
				+ "," + balance + ""
				+ ",'" + note.replace("'", "''") + "'"
				+ ",'" + auditnote.replace("'", "''") + "'"
				+ ",'" + (fileSuc[0].length()>0?fileSuc[0]:"") + "'"
				+ ",'" + (fileSuc[1].length()>0?fileSuc[1]:"") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emCompany.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String companyid = Putil.getString(request.getParameter("companyid")) ;
		if(companyid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_company p where p.companyid="+companyid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/CompanyEdit.jsp");
		
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
			String companyid = Putil.getString(request.getParameter("companyid")) ;
			String companyname = Putil.getString(request.getParameter("companyname"));
			String contact = Putil.getString(request.getParameter("contact"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			int companytypeid = Putil.getInt(request.getParameter("companytypeid"));
			String startdate = Putil.getString(request.getParameter("startdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			float balance = Putil.getFloat(request.getParameter("balance"));
			String note = Putil.getString(request.getParameter("note"));
			String auditnote = Putil.getString(request.getParameter("auditnote"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			String photo1 = Putil.getString(request.getParameter("photo1")) ;

			Date date = new Date();

			String filename0 = "p0" + date.getTime()+"";
			String filename1 = "p1" + date.getTime()+"";

			boolean fileSuc0 = false ;
			boolean fileSuc1 = false ;

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
			if(photo1.length()>10) {
				int iS = 0, iE = 0 ;
				iS = photo1.indexOf("image/") ;
				iE = photo1.indexOf(";", iS) ;
				String fileEx = "" ;
				if(iS>0 && iE>iS) {
					fileEx = photo1.substring(iS+6, iE) ;
					if(fileEx.equals("jpeg")) fileEx = "jpg" ;
					filename1 += "." + fileEx ;
					iS = photo1.indexOf("base64,") ;
					fileSuc1 = PhotoUpload.savePhoto(request, photo1.substring(iS + 7), filename1);
				}
			}
			
			StringBuilder select = new StringBuilder("update em_company set "
					+ "companyname='" + companyname.replace("'", "''") + "'"
					+ ",contact='" + contact.replace("'", "''") + "'"
					+ ",mobile='" + mobile.replace("'", "''") + "'"
					+ ",areaid=" + areaid + ""
					+ ",addr='" + addr.replace("'", "''") + "'"
					+ ",isvalid=" + isvalid + ""
					+ ",companytypeid=" + companytypeid + ""
					+ ",startdate='" + startdate.replace("'", "''") + "'"
					+ ",expdate='" + expdate.replace("'", "''") + "'"
					+ ",createdate='" + createdate.replace("'", "''") + "'"
					+ ",balance=" + balance + ""
					+ ",note='" + note.replace("'", "''") + "'"
					+ ",auditnote='" + auditnote.replace("'", "''") + "'"
					+ (fileSuc0?",photo1 = '" + filename0 + "'":"")
					+ (fileSuc1?",photo2 = '" + filename1 + "'":"")
				+ " where companyid=" + companyid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emCompany.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.companyid) as total from em_company p where p.companyid>=0"
					+ (keyword.length()>0?" and p.companyname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_company p where p.companyid>=0"
					+ (keyword.length()>0?" and p.companyname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/CompanyList.jsp");
		
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
			String companyid = Putil.getString(request.getParameter("companyid"));

			StringBuilder select = new StringBuilder("delete from em_company "
					+ " where companyid=" + companyid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emCompany.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

