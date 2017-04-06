package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emGroupInsurancePolicy</servlet-name>
		<servlet-class>com.web.servlet.admin.EmGroupInsurancePolicyServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emGroupInsurancePolicy</servlet-name>
		<url-pattern>/emGroupInsurancePolicy.do</url-pattern>
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

public class EmGroupInsurancePolicyServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449283785L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth32 = request.getAttribute("auth32");
    	if(auth32.equals("")) {
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

		forward(request, response, "/admin/GroupInsurancePolicyAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth32 = request.getAttribute("auth32");
    	if(auth32.equals("")) {
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
			int applicantcompanyid = Putil.getInt(request.getParameter("applicantcompanyid"));
			int insurercompanyid = Putil.getInt(request.getParameter("insurercompanyid"));
			String groupinsurancepolicyname = Putil.getString(request.getParameter("groupinsurancepolicyname"));
			String policynumber = Putil.getString(request.getParameter("policynumber"));
			float premium = Putil.getFloat(request.getParameter("premium"));
			String periodbegin = Putil.getString(request.getParameter("periodbegin"));
			String periodend = Putil.getString(request.getParameter("periodend"));
			int policyfaid = Putil.getInt(request.getParameter("policyfaid"));
			String ywxpolicynumb = Putil.getString(request.getParameter("ywxpolicynumb"));






			
			StringBuilder select = new StringBuilder("insert into em_groupinsurancepolicy (applicantcompanyid,insurercompanyid,groupinsurancepolicyname,policynumber,premium,periodbegin,periodend,policyfaid,ywxpolicynumb) values ("
				+ "" + applicantcompanyid + ""
				+ "," + insurercompanyid + ""
				+ ",'" + groupinsurancepolicyname.replace("'", "''") + "'"
				+ ",'" + policynumber.replace("'", "''") + "'"
				+ "," + premium + ""
				+ ",'" + periodbegin.replace("'", "''") + "'"
				+ ",'" + periodend.replace("'", "''") + "'"
				+ "," + policyfaid + ""
				+ ",'" + ywxpolicynumb.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emGroupInsurancePolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String groupinsurancepolicyid = Putil.getString(request.getParameter("groupinsurancepolicyid")) ;
		if(groupinsurancepolicyid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_groupinsurancepolicy p where p.groupinsurancepolicyid="+groupinsurancepolicyid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/GroupInsurancePolicyEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth32 = request.getAttribute("auth32");
    	if(auth32.equals("")) {
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
			String groupinsurancepolicyid = Putil.getString(request.getParameter("groupinsurancepolicyid")) ;
			int applicantcompanyid = Putil.getInt(request.getParameter("applicantcompanyid"));
			int insurercompanyid = Putil.getInt(request.getParameter("insurercompanyid"));
			String groupinsurancepolicyname = Putil.getString(request.getParameter("groupinsurancepolicyname"));
			String policynumber = Putil.getString(request.getParameter("policynumber"));
			float premium = Putil.getFloat(request.getParameter("premium"));
			String periodbegin = Putil.getString(request.getParameter("periodbegin"));
			String periodend = Putil.getString(request.getParameter("periodend"));
			int policyfaid = Putil.getInt(request.getParameter("policyfaid"));
			String ywxpolicynumb = Putil.getString(request.getParameter("ywxpolicynumb"));




			
			StringBuilder select = new StringBuilder("update em_groupinsurancepolicy set "
					+ "applicantcompanyid=" + applicantcompanyid + ""
					+ ",insurercompanyid=" + insurercompanyid + ""
					+ ",groupinsurancepolicyname='" + groupinsurancepolicyname.replace("'", "''") + "'"
					+ ",policynumber='" + policynumber.replace("'", "''") + "'"
					+ ",premium=" + premium + ""
					+ ",periodbegin='" + periodbegin.replace("'", "''") + "'"
					+ ",periodend='" + periodend.replace("'", "''") + "'"
					+ ",policyfaid=" + policyfaid + ""
					+ ",ywxpolicynumb='" + ywxpolicynumb.replace("'", "''") + "'"
				+ " where groupinsurancepolicyid=" + groupinsurancepolicyid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emGroupInsurancePolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.groupinsurancepolicyid) as total from em_groupinsurancepolicy p where p.groupinsurancepolicyid>=0"
					+ (keyword.length()>0?" and p.groupinsurancepolicyname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_groupinsurancepolicy p where p.groupinsurancepolicyid>=0"
					+ (keyword.length()>0?" and p.groupinsurancepolicyname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/GroupInsurancePolicyList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth32 = request.getAttribute("auth32");
    	if(auth32.equals("")) {
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
			String groupinsurancepolicyid = Putil.getString(request.getParameter("groupinsurancepolicyid"));

			StringBuilder select = new StringBuilder("delete from em_groupinsurancepolicy "
					+ " where groupinsurancepolicyid=" + groupinsurancepolicyid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emGroupInsurancePolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth31 = checkAuthority(31, request)!=null?"1":"";
    	String auth32 = checkAuthority(32, request)!=null?"1":"";
		request.setAttribute("auth31", auth31);
		request.setAttribute("auth32", auth32);
    	if(auth31.equals("") && auth32.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

