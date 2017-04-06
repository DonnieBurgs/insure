package com.web.servlet.admin;
/*
	<servlet>
		<servlet-name>emCouponCode</servlet-name>
		<servlet-class>com.web.servlet.admin.EmCouponCodeServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emCouponCode</servlet-name>
		<url-pattern>/emCouponCode.do</url-pattern>
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

public class EmCouponCodeServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488468701487L;
	private String auth69 = "";
	private String auth70 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth70.equals("")) {
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

		forward(request, response, "/admin/CouponCodeAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth70.equals("")) {
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
			int couponid = Putil.getInt(request.getParameter("couponid"));
			int userid = Putil.getInt(request.getParameter("userid"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			int isused = Putil.getInt(request.getParameter("isused"));
			String useddate = Putil.getString(request.getParameter("useddate"));
			int orderid = Putil.getInt(request.getParameter("orderid"));
			String note = Putil.getString(request.getParameter("note"));
			int seq = Putil.getInt(request.getParameter("seq"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));






			
			StringBuilder select = new StringBuilder("insert into em_couponcode (couponid,userid,createdate,isused,useddate,orderid,note,seq,isdelete) values ("
				+ "" + couponid + ""
				+ "," + userid + ""
				+ ",'" + createdate.replace("'", "''") + "'"
				+ "," + isused + ""
				+ ",'" + useddate.replace("'", "''") + "'"
				+ "," + orderid + ""
				+ ",'" + note.replace("'", "''") + "'"
				+ "," + seq + ""
				+ ",0"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emCouponCode.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String couponcodeid = Putil.getString(request.getParameter("couponcodeid")) ;
		if(couponcodeid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_couponcode p where p.couponcodeid="+couponcodeid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/CouponCodeEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth70.equals("")) {
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
			String couponcodeid = Putil.getString(request.getParameter("couponcodeid")) ;
			int userid = Putil.getInt(request.getParameter("userid"));
			int isused = Putil.getInt(request.getParameter("isused"));
			int orderid = Putil.getInt(request.getParameter("orderid"));
			String note = Putil.getString(request.getParameter("note"));
			int seq = Putil.getInt(request.getParameter("seq"));




			
			StringBuilder select = new StringBuilder("update em_couponcode set "
					+ "userid=" + userid + ""
					+ ",isused=" + isused + ""
					+ ",orderid=" + orderid + ""
					+ ",note='" + note.replace("'", "''") + "'"
					+ ",seq=" + seq + ""
				+ " where couponcodeid=" + couponcodeid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emCouponCode.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.couponcodeid) as total from em_couponcode p where p.couponcodeid>0"
					+ (keyword.length()>0?" and p.couponcodename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_couponcode p where p.couponcodeid>0"
					+ (keyword.length()>0?" and p.couponcodename like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/CouponCodeList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth70.equals("")) {
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
			String couponcodeid = Putil.getString(request.getParameter("couponcodeid"));

			StringBuilder select = new StringBuilder("delete from em_couponcode "
					+ " where couponcodeid=" + couponcodeid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emCouponCode.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth69 = checkAuthority(69, request)!=null?"1":"";
    	auth70 = checkAuthority(70, request)!=null?"1":"";
		request.setAttribute("auth69", auth69);
		request.setAttribute("auth70", auth70);
    	if(auth69.equals("") && auth70.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

