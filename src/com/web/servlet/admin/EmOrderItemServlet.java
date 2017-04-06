package com.web.servlet.admin;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.*;

public class EmOrderItemServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488465992943L;
	private String auth11 = "";
	private String auth12 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
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

		forward(request, response, "/admin/OrderItemAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
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
			String orderitemname = Putil.getString(request.getParameter("orderitemname"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			float price = Putil.getFloat(request.getParameter("price"));
			float cost = Putil.getFloat(request.getParameter("cost"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			int userid = Putil.getInt(request.getParameter("userid"));
			String note = Putil.getString(request.getParameter("note"));






			
			StringBuilder select = new StringBuilder("insert into em_orderitem (orderitemname,companyid,price,cost,createdate,userid,note) values ("
				+ "'" + orderitemname.replace("'", "''") + "'"
				+ "," + companyid + ""
				+ "," + price + ""
				+ "," + cost + ""
				+ ",'" + createdate.replace("'", "''") + "'"
				+ "," + userid + ""
				+ ",'" + note.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emOrderItem.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String orderitemid = Putil.getString(request.getParameter("orderitemid")) ;
		if(orderitemid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_orderitem p where p.orderitemid="+orderitemid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/OrderItemEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
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
			String orderitemid = Putil.getString(request.getParameter("orderitemid")) ;
			String orderitemname = Putil.getString(request.getParameter("orderitemname"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			float price = Putil.getFloat(request.getParameter("price"));
			float cost = Putil.getFloat(request.getParameter("cost"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			int userid = Putil.getInt(request.getParameter("userid"));
			String note = Putil.getString(request.getParameter("note"));




			
			StringBuilder select = new StringBuilder("update em_orderitem set "
					+ "orderitemname='" + orderitemname.replace("'", "''") + "'"
					+ ",companyid=" + companyid + ""
					+ ",price=" + price + ""
					+ ",cost=" + cost + ""
					+ ",createdate='" + createdate.replace("'", "''") + "'"
					+ ",userid=" + userid + ""
					+ ",note='" + note.replace("'", "''") + "'"
				+ " where orderitemid=" + orderitemid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emOrderItem.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.orderitemid) as total from em_orderitem p where p.orderitemid>=0"
					+ (keyword.length()>0?" and p.orderitemname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_orderitem p where p.orderitemid>=0"
					+ (keyword.length()>0?" and p.orderitemname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/OrderItemList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
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
			String orderitemid = Putil.getString(request.getParameter("orderitemid"));

			StringBuilder select = new StringBuilder("delete from em_orderitem "
					+ " where orderitemid=" + orderitemid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emOrderItem.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth11 = checkAuthority(11, request)!=null?"1":"";
    	auth12 = checkAuthority(12, request)!=null?"1":"";
		request.setAttribute("auth11", auth11);
		request.setAttribute("auth12", auth12);
    	if(auth11.equals("") && auth12.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

