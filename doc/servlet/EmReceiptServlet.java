package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emReceipt</servlet-name>
		<servlet-class>com.web.servlet.admin.EmReceiptServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emReceipt</servlet-name>
		<url-pattern>/emReceipt.do</url-pattern>
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

public class EmReceiptServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449284039L;
	
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

		forward(request, response, "/admin/ReceiptAdd.jsp");
	
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
			int claimid = Putil.getInt(request.getParameter("claimid"));
			String receiptnumber = Putil.getString(request.getParameter("receiptnumber"));
			int hospitalid = Putil.getInt(request.getParameter("hospitalid"));
			String visitdate = Putil.getString(request.getParameter("visitdate"));
			String hospitaldate = Putil.getString(request.getParameter("hospitaldate"));
			String dischargedate = Putil.getString(request.getParameter("dischargedate"));
			float fundpaid = Putil.getFloat(request.getParameter("fundpaid"));
			float cashpaid = Putil.getFloat(request.getParameter("cashpaid"));
			float total = Putil.getFloat(request.getParameter("total"));






			
			StringBuilder select = new StringBuilder("insert into em_receipt (claimid,receiptnumber,hospitalid,visitdate,hospitaldate,dischargedate,fundpaid,cashpaid,total) values ("
				+ "" + claimid + ""
				+ ",'" + receiptnumber.replace("'", "''") + "'"
				+ "," + hospitalid + ""
				+ ",'" + visitdate.replace("'", "''") + "'"
				+ ",'" + hospitaldate.replace("'", "''") + "'"
				+ ",'" + dischargedate.replace("'", "''") + "'"
				+ "," + fundpaid + ""
				+ "," + cashpaid + ""
				+ "," + total + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emReceipt.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String receiptid = Putil.getString(request.getParameter("receiptid")) ;
		if(receiptid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_receipt p where p.receiptid="+receiptid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ReceiptEdit.jsp");
		
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
			String receiptid = Putil.getString(request.getParameter("receiptid")) ;
			int claimid = Putil.getInt(request.getParameter("claimid"));
			String receiptnumber = Putil.getString(request.getParameter("receiptnumber"));
			int hospitalid = Putil.getInt(request.getParameter("hospitalid"));
			String visitdate = Putil.getString(request.getParameter("visitdate"));
			String hospitaldate = Putil.getString(request.getParameter("hospitaldate"));
			String dischargedate = Putil.getString(request.getParameter("dischargedate"));
			float fundpaid = Putil.getFloat(request.getParameter("fundpaid"));
			float cashpaid = Putil.getFloat(request.getParameter("cashpaid"));
			float total = Putil.getFloat(request.getParameter("total"));




			
			StringBuilder select = new StringBuilder("update em_receipt set "
					+ "claimid=" + claimid + ""
					+ ",receiptnumber='" + receiptnumber.replace("'", "''") + "'"
					+ ",hospitalid=" + hospitalid + ""
					+ ",visitdate='" + visitdate.replace("'", "''") + "'"
					+ ",hospitaldate='" + hospitaldate.replace("'", "''") + "'"
					+ ",dischargedate='" + dischargedate.replace("'", "''") + "'"
					+ ",fundpaid=" + fundpaid + ""
					+ ",cashpaid=" + cashpaid + ""
					+ ",total=" + total + ""
				+ " where receiptid=" + receiptid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emReceipt.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.receiptid) as total from em_receipt p where p.receiptid>=0"
					+ (keyword.length()>0?" and p.receiptname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_receipt p where p.receiptid>=0"
					+ (keyword.length()>0?" and p.receiptname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/ReceiptList.jsp");
		
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
			String receiptid = Putil.getString(request.getParameter("receiptid"));

			StringBuilder select = new StringBuilder("delete from em_receipt "
					+ " where receiptid=" + receiptid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emReceipt.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

