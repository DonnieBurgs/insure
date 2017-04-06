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

public class EmNoticeServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488465993311L;
	private String auth59 = "";
	private String auth60 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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

		forward(request, response, "/admin/NoticeAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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
			String title = Putil.getString(request.getParameter("title"));
			String content = Putil.getString(request.getParameter("content"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			int isshow = Putil.getInt(request.getParameter("isshow"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));
			int userid = Putil.getInt(request.getParameter("userid"));
			
			StringBuilder select = new StringBuilder("insert into em_notice (title,content,createdate,expdate,isshow,isdelete,userid) values ("
				+ "'" + title.replace("'", "''") + "'"
				+ ",'" + content.replace("'", "''") + "'"
				+ ",'" + createdate.replace("'", "''") + "'"
				+ ",'" + expdate.replace("'", "''") + "'"
				+ "," + isshow + ""
				+ "," + isdelete + ""
				+ "," + userid + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emNotice.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String noticeid = Putil.getString(request.getParameter("noticeid")) ;
		if(noticeid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_notice p where p.noticeid="+noticeid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/NoticeEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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
			String noticeid = Putil.getString(request.getParameter("noticeid")) ;
			String title = Putil.getString(request.getParameter("title"));
			String content = Putil.getString(request.getParameter("content"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			int isshow = Putil.getInt(request.getParameter("isshow"));
			int isdelete = Putil.getInt(request.getParameter("isdelete"));
			int userid = Putil.getInt(request.getParameter("userid"));




			
			StringBuilder select = new StringBuilder("update em_notice set "
					+ "title='" + title.replace("'", "''") + "'"
					+ ",content='" + content.replace("'", "''") + "'"
					+ ",createdate='" + createdate.replace("'", "''") + "'"
					+ ",expdate='" + expdate.replace("'", "''") + "'"
					+ ",isshow=" + isshow + ""
					+ ",isdelete=" + isdelete + ""
					+ ",userid=" + userid + ""
				+ " where noticeid=" + noticeid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emNotice.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.noticeid) as total from em_notice p where p.noticeid>=0"
					+ (keyword.length()>0?" and p.noticename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_notice p where p.noticeid>=0"
					+ (keyword.length()>0?" and p.noticename like '%" + keyword + "%'":"")
					+ " order by p.noticeid desc limit " + (s-1)*m + "," + m + ""
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

		forward(request, response, "/admin/NoticeList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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
			String noticeid = Putil.getString(request.getParameter("noticeid"));

			StringBuilder select = new StringBuilder("delete from em_notice "
					+ " where noticeid=" + noticeid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emNotice.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth59 = checkAuthority(59, request)!=null?"1":"";
    	auth60 = checkAuthority(60, request)!=null?"1":"";
		request.setAttribute("auth59", auth59);
		request.setAttribute("auth60", auth60);
    	if(auth59.equals("") && auth60.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

