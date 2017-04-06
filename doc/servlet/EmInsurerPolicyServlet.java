package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emInsurerPolicy</servlet-name>
		<servlet-class>com.web.servlet.admin.EmInsurerPolicyServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emInsurerPolicy</servlet-name>
		<url-pattern>/emInsurerPolicy.do</url-pattern>
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

public class EmInsurerPolicyServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449283469L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth22 = request.getAttribute("auth22");
    	if(auth22.equals("")) {
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

		forward(request, response, "/admin/InsurerPolicyAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth22 = request.getAttribute("auth22");
    	if(auth22.equals("")) {
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
			int policyid = Putil.getInt(request.getParameter("policyid"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			int attachedtoid = Putil.getInt(request.getParameter("attachedtoid"));
			String periodbegin = Putil.getString(request.getParameter("periodbegin"));
			String periodend = Putil.getString(request.getParameter("periodend"));
			String policystate = Putil.getString(request.getParameter("policystate"));
			String shistate = Putil.getString(request.getParameter("shistate"));
			String shiarea = Putil.getString(request.getParameter("shiarea"));
			String joblocal = Putil.getString(request.getParameter("joblocal"));
			String relation = Putil.getString(request.getParameter("relation"));
			String clientid = Putil.getString(request.getParameter("clientid"));
			String jobnumber = Putil.getString(request.getParameter("jobnumber"));
			String bankname = Putil.getString(request.getParameter("bankname"));
			String accountname = Putil.getString(request.getParameter("accountname"));
			String accountnumber = Putil.getString(request.getParameter("accountnumber"));
			String clientid = Putil.getString(request.getParameter("clientid"));






			
			StringBuilder select = new StringBuilder("insert into em_insurerpolicy (policyid,insuredid,attachedtoid,periodbegin,periodend,policystate,shistate,shiarea,joblocal,relation,clientid,jobnumber,bankname,accountname,accountnumber,clientid) values ("
				+ "" + policyid + ""
				+ "," + insuredid + ""
				+ "," + attachedtoid + ""
				+ ",'" + periodbegin.replace("'", "''") + "'"
				+ ",'" + periodend.replace("'", "''") + "'"
				+ ",'" + policystate.replace("'", "''") + "'"
				+ ",'" + shistate.replace("'", "''") + "'"
				+ ",'" + shiarea.replace("'", "''") + "'"
				+ ",'" + joblocal.replace("'", "''") + "'"
				+ ",'" + relation.replace("'", "''") + "'"
				+ ",'" + clientid.replace("'", "''") + "'"
				+ ",'" + jobnumber.replace("'", "''") + "'"
				+ ",'" + bankname.replace("'", "''") + "'"
				+ ",'" + accountname.replace("'", "''") + "'"
				+ ",'" + accountnumber.replace("'", "''") + "'"
				+ ",'" + clientid.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsurerPolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String insurerpolicyid = Putil.getString(request.getParameter("insurerpolicyid")) ;
		if(insurerpolicyid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_insurerpolicy p where p.insurerpolicyid="+insurerpolicyid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/InsurerPolicyEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth22 = request.getAttribute("auth22");
    	if(auth22.equals("")) {
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
			String insurerpolicyid = Putil.getString(request.getParameter("insurerpolicyid")) ;
			int policyid = Putil.getInt(request.getParameter("policyid"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			int attachedtoid = Putil.getInt(request.getParameter("attachedtoid"));
			String periodbegin = Putil.getString(request.getParameter("periodbegin"));
			String periodend = Putil.getString(request.getParameter("periodend"));
			String policystate = Putil.getString(request.getParameter("policystate"));
			String shistate = Putil.getString(request.getParameter("shistate"));
			String shiarea = Putil.getString(request.getParameter("shiarea"));
			String joblocal = Putil.getString(request.getParameter("joblocal"));
			String relation = Putil.getString(request.getParameter("relation"));
			String clientid = Putil.getString(request.getParameter("clientid"));
			String jobnumber = Putil.getString(request.getParameter("jobnumber"));
			String bankname = Putil.getString(request.getParameter("bankname"));
			String accountname = Putil.getString(request.getParameter("accountname"));
			String accountnumber = Putil.getString(request.getParameter("accountnumber"));
			String clientid = Putil.getString(request.getParameter("clientid"));




			
			StringBuilder select = new StringBuilder("update em_insurerpolicy set "
					+ "policyid=" + policyid + ""
					+ ",insuredid=" + insuredid + ""
					+ ",attachedtoid=" + attachedtoid + ""
					+ ",periodbegin='" + periodbegin.replace("'", "''") + "'"
					+ ",periodend='" + periodend.replace("'", "''") + "'"
					+ ",policystate='" + policystate.replace("'", "''") + "'"
					+ ",shistate='" + shistate.replace("'", "''") + "'"
					+ ",shiarea='" + shiarea.replace("'", "''") + "'"
					+ ",joblocal='" + joblocal.replace("'", "''") + "'"
					+ ",relation='" + relation.replace("'", "''") + "'"
					+ ",clientid='" + clientid.replace("'", "''") + "'"
					+ ",jobnumber='" + jobnumber.replace("'", "''") + "'"
					+ ",bankname='" + bankname.replace("'", "''") + "'"
					+ ",accountname='" + accountname.replace("'", "''") + "'"
					+ ",accountnumber='" + accountnumber.replace("'", "''") + "'"
					+ ",clientid='" + clientid.replace("'", "''") + "'"
				+ " where insurerpolicyid=" + insurerpolicyid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emInsurerPolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.insurerpolicyid) as total from em_insurerpolicy p where p.insurerpolicyid>=0"
					+ (keyword.length()>0?" and p.insurerpolicyname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_insurerpolicy p where p.insurerpolicyid>=0"
					+ (keyword.length()>0?" and p.insurerpolicyname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/InsurerPolicyList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth22 = request.getAttribute("auth22");
    	if(auth22.equals("")) {
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
			String insurerpolicyid = Putil.getString(request.getParameter("insurerpolicyid"));

			StringBuilder select = new StringBuilder("delete from em_insurerpolicy "
					+ " where insurerpolicyid=" + insurerpolicyid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsurerPolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth21 = checkAuthority(21, request)!=null?"1":"";
    	String auth22 = checkAuthority(22, request)!=null?"1":"";
		request.setAttribute("auth21", auth21);
		request.setAttribute("auth22", auth22);
    	if(auth21.equals("") && auth22.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

