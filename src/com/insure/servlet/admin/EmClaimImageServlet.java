package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaimImage</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmClaimImageServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaimImage</servlet-name>
		<url-pattern>/emClaimImage.do</url-pattern>
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

public class EmClaimImageServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580695876L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth50", request, response)) {
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

		forward(request, response, "/admin/ClaimImageAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth50", request, response)) {
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
			int claimid = Putil.getInt(request.getParameter("claimid"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;

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
			
			StringBuilder select = new StringBuilder("insert into em_claimimage (claimid,photourl) values ("
				+ "" + claimid + ""
				+ ",'" + (fileSuc[0].length()>0?fileSuc[0]:"") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimImage.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String id = Putil.getString(request.getParameter("id")) ;
		if(id.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claimimage p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ClaimImageEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth50", request, response)) {
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
			String id = Putil.getString(request.getParameter("id")) ;
			int claimid = Putil.getInt(request.getParameter("claimid"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;

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
			
			StringBuilder select = new StringBuilder("update em_claimimage set "
					+ "claimid=" + claimid + ""
					+ (fileSuc0?",photourl = '" + filename0 + "'":"")
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emClaimImage.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claimimage p where p.id>=0"
					+ (keyword.length()>0?" and p.claimimagename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claimimage p where p.id>=0"
					+ (keyword.length()>0?" and p.claimimagename like '%" + keyword + "%'":"")
					+ " order by p.id desc limit " + (s-1)*m + "," + m + ""
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

		forward(request, response, "/admin/ClaimImageList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth50", request, response)) {
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
			String id = Putil.getString(request.getParameter("id"));

			StringBuilder select = new StringBuilder("delete from em_claimimage "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimImage.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth49 = checkAuthority(49, request)!=null?"1":"";
    	String auth50 = checkAuthority(50, request)!=null?"1":"";
		request.setAttribute("auth49", auth49);
		request.setAttribute("auth50", auth50);
    	if(auth49.equals("") && auth50.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

