package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emInsuranceGroup</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmInsuranceGroupServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emInsuranceGroup</servlet-name>
		<url-pattern>/emInsuranceGroup.do</url-pattern>
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

public class EmInsuranceGroupServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580695073L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth28", request, response)) {
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

		forward(request, response, "/admin/InsuranceGroupAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth28", request, response)) {
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
			int policyid = Putil.getInt(request.getParameter("policyid"));
			String insurancegroupname = Putil.getString(request.getParameter("insurancegroupname"));
			String insurancegroupcode = Putil.getString(request.getParameter("insurancegroupcode"));






			
			StringBuilder select = new StringBuilder("insert into em_insurancegroup (policyid,insurancegroupname,insurancegroupcode) values ("
				+ "" + policyid + ""
				+ ",'" + insurancegroupname.replace("'", "''") + "'"
				+ ",'" + insurancegroupcode.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsuranceGroup.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s+"&policyid="+Putil.getString(request.getParameter("policyid")));
		
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
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_insurancegroup p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/InsuranceGroupEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth28", request, response)) {
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

		int policyid = Putil.getInt(request.getParameter("policyid"));
		try {
			String id = Putil.getString(request.getParameter("id")) ;
			String insurancegroupname = Putil.getString(request.getParameter("insurancegroupname"));
			String insurancegroupcode = Putil.getString(request.getParameter("insurancegroupcode"));




			
			StringBuilder select = new StringBuilder("update em_insurancegroup set "
					+ "policyid=" + policyid + ""
					+ ",insurancegroupname='" + insurancegroupname.replace("'", "''") + "'"
					+ ",insurancegroupcode='" + insurancegroupcode.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emInsuranceGroup.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s+ "&policyid=" + policyid);
		
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
			int policyid = Putil.getInt(request.getParameter("policyid"));
			

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_insurancegroup p where p.id>=0"
					+ (keyword.length()>0?" and p.insurancegroupname like '%" + keyword + "%'":"")
					+ (policyid != 0 ? " and p.policyid = " + policyid : ""));
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_insurancegroup p where p.id>=0"
					+ (keyword.length()>0?" and p.insurancegroupname like '%" + keyword + "%'":"")
					+ (policyid != 0 ? " and p.policyid = " + policyid : "")
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

		forward(request, response, "/admin/InsuranceGroupList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth28", request, response)) {
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

			StringBuilder select = new StringBuilder("delete from em_insurancegroup "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsuranceGroup.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth27 = checkAuthority(27, request)!=null?"1":"";
    	String auth28 = checkAuthority(28, request)!=null?"1":"";
		request.setAttribute("auth27", auth27);
		request.setAttribute("auth28", auth28);
    	if(auth27.equals("") && auth28.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
    
    @Override
    public void def(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

    	int policyid = Putil.getInt(request.getParameter("policyid"));
    	List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		// 列表分页语句
		resultRows = DbUtils.query("select p.* from em_insurancegroup p where p.id>=0"
				+ (policyid != 0 ? " and p.policyid = " + policyid : "")
				+ " order by p.id desc"
			);
		toJson(resultRows, response);
    }
}

