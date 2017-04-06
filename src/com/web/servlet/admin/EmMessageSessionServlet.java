package com.web.servlet.admin;
/*
	<servlet>
		<servlet-name>emMessageSession</servlet-name>
		<servlet-class>com.web.servlet.admin.EmMessageSessionServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emMessageSession</servlet-name>
		<url-pattern>/emMessageSession.do</url-pattern>
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

public class EmMessageSessionServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488532703968L;
	private String auth51 = "";
	private String auth52 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		forward(request, response, "/admin/MessageSessionAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			int userid1 = Putil.getInt(request.getParameter("userid1"));
			int userid2 = Putil.getInt(request.getParameter("userid2"));
			int messagecount = Putil.getInt(request.getParameter("messagecount"));
			int isnew1 = Putil.getInt(request.getParameter("isnew1"));
			int isnew2 = Putil.getInt(request.getParameter("isnew2"));
			int lastmessageid = Putil.getInt(request.getParameter("lastmessageid"));
			int companyid = Putil.getInt(request.getParameter("companyid"));






			
			StringBuilder select = new StringBuilder("insert into em_messagesession (userid1,userid2,messagecount,isnew1,isnew2,lastmessageid,companyid) values ("
				+ "" + userid1 + ""
				+ "," + userid2 + ""
				+ "," + messagecount + ""
				+ "," + isnew1 + ""
				+ "," + isnew2 + ""
				+ "," + lastmessageid + ""
				+ "," + companyid + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emMessageSession.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String messagesessionid = Putil.getString(request.getParameter("messagesessionid")) ;
		if(messagesessionid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_messagesession p where p.messagesessionid="+messagesessionid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/MessageSessionEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			String messagesessionid = Putil.getString(request.getParameter("messagesessionid")) ;
			int userid1 = Putil.getInt(request.getParameter("userid1"));
			int userid2 = Putil.getInt(request.getParameter("userid2"));
			int messagecount = Putil.getInt(request.getParameter("messagecount"));
			int isnew1 = Putil.getInt(request.getParameter("isnew1"));
			int isnew2 = Putil.getInt(request.getParameter("isnew2"));
			int lastmessageid = Putil.getInt(request.getParameter("lastmessageid"));
			int companyid = Putil.getInt(request.getParameter("companyid"));




			
			StringBuilder select = new StringBuilder("update em_messagesession set "
					+ "userid1=" + userid1 + ""
					+ ",userid2=" + userid2 + ""
					+ ",messagecount=" + messagecount + ""
					+ ",isnew1=" + isnew1 + ""
					+ ",isnew2=" + isnew2 + ""
					+ ",lastmessageid=" + lastmessageid + ""
					+ ",companyid=" + companyid + ""
				+ " where messagesessionid=" + messagesessionid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emMessageSession.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.messagesessionid) as total from em_messagesession p where p.messagesessionid>=0"
					+ (keyword.length()>0?" and p.messagesessionname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_messagesession p where p.messagesessionid>=0"
					+ (keyword.length()>0?" and p.messagesessionname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/MessageSessionList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			String messagesessionid = Putil.getString(request.getParameter("messagesessionid"));

			StringBuilder select = new StringBuilder("delete from em_messagesession "
					+ " where messagesessionid=" + messagesessionid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emMessageSession.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth51 = checkAuthority(51, request)!=null?"1":"";
    	auth52 = checkAuthority(52, request)!=null?"1":"";
		request.setAttribute("auth51", auth51);
		request.setAttribute("auth52", auth52);
    	if(auth51.equals("") && auth52.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

