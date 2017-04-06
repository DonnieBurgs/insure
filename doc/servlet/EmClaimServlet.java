package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaim</servlet-name>
		<servlet-class>com.web.servlet.admin.EmClaimServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaim</servlet-name>
		<url-pattern>/emClaim.do</url-pattern>
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

public class EmClaimServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449283921L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth44 = request.getAttribute("auth44");
    	if(auth44.equals("")) {
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

		forward(request, response, "/admin/ClaimAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth44 = request.getAttribute("auth44");
    	if(auth44.equals("")) {
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
			int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
			String serialnumber = Putil.getString(request.getParameter("serialnumber"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			String bardh = Putil.getString(request.getParameter("bardh"));






			
			StringBuilder select = new StringBuilder("insert into em_claim (claimarchiveid,serialnumber,insuredid,bardh) values ("
				+ "" + claimarchiveid + ""
				+ ",'" + serialnumber.replace("'", "''") + "'"
				+ "," + insuredid + ""
				+ ",'" + bardh.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String claimid = Putil.getString(request.getParameter("claimid")) ;
		if(claimid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claim p where p.claimid="+claimid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ClaimEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth44 = request.getAttribute("auth44");
    	if(auth44.equals("")) {
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
			String claimid = Putil.getString(request.getParameter("claimid")) ;
			int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
			String serialnumber = Putil.getString(request.getParameter("serialnumber"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			String bardh = Putil.getString(request.getParameter("bardh"));




			
			StringBuilder select = new StringBuilder("update em_claim set "
					+ "claimarchiveid=" + claimarchiveid + ""
					+ ",serialnumber='" + serialnumber.replace("'", "''") + "'"
					+ ",insuredid=" + insuredid + ""
					+ ",bardh='" + bardh.replace("'", "''") + "'"
				+ " where claimid=" + claimid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.claimid) as total from em_claim p where p.claimid>=0"
					+ (keyword.length()>0?" and p.claimname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claim p where p.claimid>=0"
					+ (keyword.length()>0?" and p.claimname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/ClaimList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth44 = request.getAttribute("auth44");
    	if(auth44.equals("")) {
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
			String claimid = Putil.getString(request.getParameter("claimid"));

			StringBuilder select = new StringBuilder("delete from em_claim "
					+ " where claimid=" + claimid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth43 = checkAuthority(43, request)!=null?"1":"";
    	String auth44 = checkAuthority(44, request)!=null?"1":"";
		request.setAttribute("auth43", auth43);
		request.setAttribute("auth44", auth44);
    	if(auth43.equals("") && auth44.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

