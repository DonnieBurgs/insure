package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emDisease</servlet-name>
		<servlet-class>com.web.servlet.admin.EmDiseaseServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emDisease</servlet-name>
		<url-pattern>/emDisease.do</url-pattern>
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

public class EmDiseaseServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491555808491L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth52", request, response)) {
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

		forward(request, response, "/admin/DiseaseAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth52", request, response)) {
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
			int diseasename = Putil.getInt(request.getParameter("diseasename"));
			String diseasecode = Putil.getString(request.getParameter("diseasecode"));
			String pinyin = Putil.getString(request.getParameter("pinyin"));
			int critical = Putil.getInt(request.getParameter("critical"));
			String domain = Putil.getString(request.getParameter("domain"));






			
			StringBuilder select = new StringBuilder("insert into em_disease (diseasename,diseasecode,pinyin,critical,domain) values ("
				+ "" + diseasename + ""
				+ ",'" + diseasecode.replace("'", "''") + "'"
				+ ",'" + pinyin.replace("'", "''") + "'"
				+ "," + critical + ""
				+ ",'" + domain.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emDisease.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String diseaseid = Putil.getString(request.getParameter("diseaseid")) ;
		if(diseaseid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_disease p where p.diseaseid="+diseaseid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/DiseaseEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth52", request, response)) {
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
			String diseaseid = Putil.getString(request.getParameter("diseaseid")) ;
			int diseasename = Putil.getInt(request.getParameter("diseasename"));
			String diseasecode = Putil.getString(request.getParameter("diseasecode"));
			String pinyin = Putil.getString(request.getParameter("pinyin"));
			int critical = Putil.getInt(request.getParameter("critical"));
			String domain = Putil.getString(request.getParameter("domain"));




			
			StringBuilder select = new StringBuilder("update em_disease set "
					+ "diseasename=" + diseasename + ""
					+ ",diseasecode='" + diseasecode.replace("'", "''") + "'"
					+ ",pinyin='" + pinyin.replace("'", "''") + "'"
					+ ",critical=" + critical + ""
					+ ",domain='" + domain.replace("'", "''") + "'"
				+ " where diseaseid=" + diseaseid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emDisease.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.diseaseid) as total from em_disease p where p.diseaseid>=0"
					+ (keyword.length()>0?" and p.diseasename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_disease p where p.diseaseid>=0"
					+ (keyword.length()>0?" and p.diseasename like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/DiseaseList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth52", request, response)) {
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
			String diseaseid = Putil.getString(request.getParameter("diseaseid"));

			StringBuilder select = new StringBuilder("delete from em_disease "
					+ " where diseaseid=" + diseaseid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emDisease.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth51 = checkAuthority(51, request)!=null?"1":"";
    	String auth52 = checkAuthority(52, request)!=null?"1":"";
		request.setAttribute("auth51", auth51);
		request.setAttribute("auth52", auth52);
    	if(auth51.equals("") && auth52.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

