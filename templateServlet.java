package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>em##ClassName##</servlet-name>
		<servlet-class>com.web.servlet.admin.Em##ClassName##Servlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>em##ClassName##</servlet-name>
		<url-pattern>/em##ClassName##.do</url-pattern>
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

public class Em##ClassName##Servlet extends UserSecureDispatcher {

	private static final long serialVersionUID = ##Serial##L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth##AuthorNum2## = request.getAttribute("auth##AuthorNum2##");
    	if(auth##AuthorNum2##.equals("")) {
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

		forward(request, response, "/admin/##ClassName##Add.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth##AuthorNum2## = request.getAttribute("auth##AuthorNum2##");
    	if(auth##AuthorNum2##.equals("")) {
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
##Parameter##
##str_date##
##str_filename##
##str_fileSuc##
##str_fileSucA##
##str_fileCount##
##str_if##			
			StringBuilder select = new StringBuilder("insert into em_##ClassNameLower## (##ColumnName##) values ("
##Values##				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/em##ClassName##.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String ##ClassNameLower##id = Putil.getString(request.getParameter("##ClassNameLower##id")) ;
		if(##ClassNameLower##id.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_##ClassNameLower## p where p.##ClassNameLower##id="+##ClassNameLower##id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/##ClassName##Edit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth##AuthorNum2## = request.getAttribute("auth##AuthorNum2##");
    	if(auth##AuthorNum2##.equals("")) {
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
			String ##ClassNameLower##id = Putil.getString(request.getParameter("##ClassNameLower##id")) ;
##Parameter##
##str_date##
##str_filename##
##str_fileSuc##
##str_if_update##			
			StringBuilder select = new StringBuilder("update em_##ClassNameLower## set "
##UpdateColumns##				+ " where ##ClassNameLower##id=" + ##ClassNameLower##id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/em##ClassName##.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.##ClassNameLower##id) as total from em_##ClassNameLower## p where p.##ClassNameLower##id>=0"
					+ (keyword.length()>0?" and p.##ClassNameLower##name like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_##ClassNameLower## p where p.##ClassNameLower##id>=0"
					+ (keyword.length()>0?" and p.##ClassNameLower##name like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/##ClassName##List.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth##AuthorNum2## = request.getAttribute("auth##AuthorNum2##");
    	if(auth##AuthorNum2##.equals("")) {
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
			String ##ClassNameLower##id = Putil.getString(request.getParameter("##ClassNameLower##id"));

			StringBuilder select = new StringBuilder("delete from em_##ClassNameLower## "
					+ " where ##ClassNameLower##id=" + ##ClassNameLower##id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/em##ClassName##.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth##AuthorNum1## = checkAuthority(##AuthorNum1##, request)!=null?"1":"";
    	String auth##AuthorNum2## = checkAuthority(##AuthorNum2##, request)!=null?"1":"";
		request.setAttribute("auth##AuthorNum1##", auth##AuthorNum1##);
		request.setAttribute("auth##AuthorNum2##", auth##AuthorNum2##);
    	if(auth##AuthorNum1##.equals("") && auth##AuthorNum2##.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

