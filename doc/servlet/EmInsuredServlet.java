package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emInsured</servlet-name>
		<servlet-class>com.web.servlet.admin.EmInsuredServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emInsured</servlet-name>
		<url-pattern>/emInsured.do</url-pattern>
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

public class EmInsuredServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449283685L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth26 = request.getAttribute("auth26");
    	if(auth26.equals("")) {
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

		forward(request, response, "/admin/InsuredAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth26 = request.getAttribute("auth26");
    	if(auth26.equals("")) {
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
			String insuredname = Putil.getString(request.getParameter("insuredname"));
			String gender = Putil.getString(request.getParameter("gender"));
			String idnumber = Putil.getString(request.getParameter("idnumber"));
			String passport = Putil.getString(request.getParameter("passport"));
			String birthdate = Putil.getString(request.getParameter("birthdate"));
			String employer = Putil.getString(request.getParameter("employer"));
			String jobnumber = Putil.getString(request.getParameter("jobnumber"));
			String bankname = Putil.getString(request.getParameter("bankname"));
			String accountname = Putil.getString(request.getParameter("accountname"));
			String accountnumber = Putil.getString(request.getParameter("accountnumber"));
			String email = Putil.getString(request.getParameter("email"));
			String department = Putil.getString(request.getParameter("department"));






			
			StringBuilder select = new StringBuilder("insert into em_insured (insuredname,gender,idnumber,passport,birthdate,employer,jobnumber,bankname,accountname,accountnumber,email,department) values ("
				+ "'" + insuredname.replace("'", "''") + "'"
				+ ",'" + gender.replace("'", "''") + "'"
				+ ",'" + idnumber.replace("'", "''") + "'"
				+ ",'" + passport.replace("'", "''") + "'"
				+ ",'" + birthdate.replace("'", "''") + "'"
				+ ",'" + employer.replace("'", "''") + "'"
				+ ",'" + jobnumber.replace("'", "''") + "'"
				+ ",'" + bankname.replace("'", "''") + "'"
				+ ",'" + accountname.replace("'", "''") + "'"
				+ ",'" + accountnumber.replace("'", "''") + "'"
				+ ",'" + email.replace("'", "''") + "'"
				+ ",'" + department.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsured.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String insuredid = Putil.getString(request.getParameter("insuredid")) ;
		if(insuredid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_insured p where p.insuredid="+insuredid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/InsuredEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth26 = request.getAttribute("auth26");
    	if(auth26.equals("")) {
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
			String insuredid = Putil.getString(request.getParameter("insuredid")) ;
			String insuredname = Putil.getString(request.getParameter("insuredname"));
			String gender = Putil.getString(request.getParameter("gender"));
			String idnumber = Putil.getString(request.getParameter("idnumber"));
			String passport = Putil.getString(request.getParameter("passport"));
			String birthdate = Putil.getString(request.getParameter("birthdate"));
			String employer = Putil.getString(request.getParameter("employer"));
			String jobnumber = Putil.getString(request.getParameter("jobnumber"));
			String bankname = Putil.getString(request.getParameter("bankname"));
			String accountname = Putil.getString(request.getParameter("accountname"));
			String accountnumber = Putil.getString(request.getParameter("accountnumber"));
			String email = Putil.getString(request.getParameter("email"));
			String department = Putil.getString(request.getParameter("department"));




			
			StringBuilder select = new StringBuilder("update em_insured set "
					+ "insuredname='" + insuredname.replace("'", "''") + "'"
					+ ",gender='" + gender.replace("'", "''") + "'"
					+ ",idnumber='" + idnumber.replace("'", "''") + "'"
					+ ",passport='" + passport.replace("'", "''") + "'"
					+ ",birthdate='" + birthdate.replace("'", "''") + "'"
					+ ",employer='" + employer.replace("'", "''") + "'"
					+ ",jobnumber='" + jobnumber.replace("'", "''") + "'"
					+ ",bankname='" + bankname.replace("'", "''") + "'"
					+ ",accountname='" + accountname.replace("'", "''") + "'"
					+ ",accountnumber='" + accountnumber.replace("'", "''") + "'"
					+ ",email='" + email.replace("'", "''") + "'"
					+ ",department='" + department.replace("'", "''") + "'"
				+ " where insuredid=" + insuredid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emInsured.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.insuredid) as total from em_insured p where p.insuredid>=0"
					+ (keyword.length()>0?" and p.insuredname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_insured p where p.insuredid>=0"
					+ (keyword.length()>0?" and p.insuredname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/InsuredList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth26 = request.getAttribute("auth26");
    	if(auth26.equals("")) {
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
			String insuredid = Putil.getString(request.getParameter("insuredid"));

			StringBuilder select = new StringBuilder("delete from em_insured "
					+ " where insuredid=" + insuredid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsured.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth25 = checkAuthority(25, request)!=null?"1":"";
    	String auth26 = checkAuthority(26, request)!=null?"1":"";
		request.setAttribute("auth25", auth25);
		request.setAttribute("auth26", auth26);
    	if(auth25.equals("") && auth26.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

