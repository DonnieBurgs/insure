package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaimSettlement</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmClaimSettlementServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaimSettlement</servlet-name>
		<url-pattern>/emClaimSettlement.do</url-pattern>
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

public class EmClaimSettlementServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1492500870628L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth44", request, response)) {
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

		forward(request, response, "/admin/ClaimSettlementAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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
			int receiptid = Putil.getInt(request.getParameter("receiptid"));
			float jpamount = Putil.getFloat(request.getParameter("jpamount"));
			float pfrate = Putil.getFloat(request.getParameter("pfrate"));
			float yyfpfje = Putil.getFloat(request.getParameter("yyfpfje"));
			String jcfpfje = Putil.getString(request.getParameter("jcfpfje"));
			float cwfpfje = Putil.getFloat(request.getParameter("cwfpfje"));
			float pfamount = Putil.getFloat(request.getParameter("pfamount"));
			String yyfremark = Putil.getString(request.getParameter("yyfremark"));






			
			StringBuilder select = new StringBuilder("insert into em_claimsettlement (receiptid,jpamount,pfrate,yyfpfje,jcfpfje,cwfpfje,pfamount,yyfremark) values ("
				+ "" + receiptid + ""
				+ "," + jpamount + ""
				+ "," + pfrate + ""
				+ "," + yyfpfje + ""
				+ ",'" + jcfpfje.replace("'", "''") + "'"
				+ "," + cwfpfje + ""
				+ "," + pfamount + ""
				+ ",'" + yyfremark.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimSettlement.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claimsettlement p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ClaimSettlementEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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
			int receiptid = Putil.getInt(request.getParameter("receiptid"));
			float jpamount = Putil.getFloat(request.getParameter("jpamount"));
			float pfrate = Putil.getFloat(request.getParameter("pfrate"));
			float yyfpfje = Putil.getFloat(request.getParameter("yyfpfje"));
			String jcfpfje = Putil.getString(request.getParameter("jcfpfje"));
			float cwfpfje = Putil.getFloat(request.getParameter("cwfpfje"));
			float pfamount = Putil.getFloat(request.getParameter("pfamount"));
			String yyfremark = Putil.getString(request.getParameter("yyfremark"));




			
			StringBuilder select = new StringBuilder("update em_claimsettlement set "
					+ "receiptid=" + receiptid + ""
					+ ",jpamount=" + jpamount + ""
					+ ",pfrate=" + pfrate + ""
					+ ",yyfpfje=" + yyfpfje + ""
					+ ",jcfpfje='" + jcfpfje.replace("'", "''") + "'"
					+ ",cwfpfje=" + cwfpfje + ""
					+ ",pfamount=" + pfamount + ""
					+ ",yyfremark='" + yyfremark.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emClaimSettlement.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claimsettlement p where p.id>=0"
					+ (keyword.length()>0?" and p.claimsettlementname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claimsettlement p where p.id>=0"
					+ (keyword.length()>0?" and p.claimsettlementname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/ClaimSettlementList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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

			StringBuilder select = new StringBuilder("delete from em_claimsettlement "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimSettlement.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

