package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emSyslog</servlet-name>
		<servlet-class>com.web.servlet.admin.EmSyslogServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emSyslog</servlet-name>
		<url-pattern>/emSyslog.do</url-pattern>
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

public class EmSyslogServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491555808609L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth86", request, response)) {
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

		forward(request, response, "/admin/SyslogAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth86", request, response)) {
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
			int type = Putil.getInt(request.getParameter("type"));
			int linkid = Putil.getInt(request.getParameter("linkid"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String note = Putil.getString(request.getParameter("note"));






			
			StringBuilder select = new StringBuilder("insert into em_syslog (type,linkid,createdate,note) values ("
				+ "" + type + ""
				+ "," + linkid + ""
				+ ",'" + createdate.replace("'", "''") + "'"
				+ ",'" + note.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emSyslog.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String syslogid = Putil.getString(request.getParameter("syslogid")) ;
		if(syslogid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_syslog p where p.syslogid="+syslogid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/SyslogEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth86", request, response)) {
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
			String syslogid = Putil.getString(request.getParameter("syslogid")) ;
			int type = Putil.getInt(request.getParameter("type"));
			int linkid = Putil.getInt(request.getParameter("linkid"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String note = Putil.getString(request.getParameter("note"));




			
			StringBuilder select = new StringBuilder("update em_syslog set "
					+ "type=" + type + ""
					+ ",linkid=" + linkid + ""
					+ ",createdate='" + createdate.replace("'", "''") + "'"
					+ ",note='" + note.replace("'", "''") + "'"
				+ " where syslogid=" + syslogid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emSyslog.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.syslogid) as total from em_syslog p where p.syslogid>=0"
					+ (keyword.length()>0?" and p.syslogname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_syslog p where p.syslogid>=0"
					+ (keyword.length()>0?" and p.syslogname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/SyslogList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth86", request, response)) {
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
			String syslogid = Putil.getString(request.getParameter("syslogid"));

			StringBuilder select = new StringBuilder("delete from em_syslog "
					+ " where syslogid=" + syslogid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emSyslog.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth85 = checkAuthority(85, request)!=null?"1":"";
    	String auth86 = checkAuthority(86, request)!=null?"1":"";
		request.setAttribute("auth85", auth85);
		request.setAttribute("auth86", auth86);
    	if(auth85.equals("") && auth86.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

