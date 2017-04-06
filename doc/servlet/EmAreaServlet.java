package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emArea</servlet-name>
		<servlet-class>com.web.servlet.admin.EmAreaServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emArea</servlet-name>
		<url-pattern>/emArea.do</url-pattern>
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

public class EmAreaServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449284376L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth52 = request.getAttribute("auth52");
    	if(auth52.equals("")) {
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

		forward(request, response, "/admin/AreaAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth52 = request.getAttribute("auth52");
    	if(auth52.equals("")) {
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
			String areaname = Putil.getString(request.getParameter("areaname"));
			String fullname = Putil.getString(request.getParameter("fullname"));
			int parentid = Putil.getInt(request.getParameter("parentid"));
			String path = Putil.getString(request.getParameter("path"));
			int seq = Putil.getInt(request.getParameter("seq"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));






			
			StringBuilder select = new StringBuilder("insert into em_area (areaname,fullname,parentid,path,seq,isdelete) values ("
				+ "'" + areaname.replace("'", "''") + "'"
				+ ",'" + fullname.replace("'", "''") + "'"
				+ "," + parentid + ""
				+ ",'" + path.replace("'", "''") + "'"
				+ "," + seq + ""
				+ "," + isdelete + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emArea.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String areaid = Putil.getString(request.getParameter("areaid")) ;
		if(areaid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_area p where p.areaid="+areaid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/AreaEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth52 = request.getAttribute("auth52");
    	if(auth52.equals("")) {
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
			String areaid = Putil.getString(request.getParameter("areaid")) ;
			String areaname = Putil.getString(request.getParameter("areaname"));
			String fullname = Putil.getString(request.getParameter("fullname"));
			int parentid = Putil.getInt(request.getParameter("parentid"));
			String path = Putil.getString(request.getParameter("path"));
			int seq = Putil.getInt(request.getParameter("seq"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));




			
			StringBuilder select = new StringBuilder("update em_area set "
					+ "areaname='" + areaname.replace("'", "''") + "'"
					+ ",fullname='" + fullname.replace("'", "''") + "'"
					+ ",parentid=" + parentid + ""
					+ ",path='" + path.replace("'", "''") + "'"
					+ ",seq=" + seq + ""
					+ ",isdelete=" + isdelete + ""
				+ " where areaid=" + areaid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emArea.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.areaid) as total from em_area p where p.areaid>=0"
					+ (keyword.length()>0?" and p.areaname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_area p where p.areaid>=0"
					+ (keyword.length()>0?" and p.areaname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/AreaList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth52 = request.getAttribute("auth52");
    	if(auth52.equals("")) {
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
			String areaid = Putil.getString(request.getParameter("areaid"));

			StringBuilder select = new StringBuilder("delete from em_area "
					+ " where areaid=" + areaid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emArea.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

