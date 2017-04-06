package com.web.servlet.admin;
/*
	<servlet>
		<servlet-name>emMessage</servlet-name>
		<servlet-class>com.web.servlet.admin.EmMessageServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emMessage</servlet-name>
		<url-pattern>/emMessage.do</url-pattern>
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

public class EmMessageServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488532703985L;
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

		forward(request, response, "/admin/MessageAdd.jsp");
	
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
			int messagesessionid = Putil.getInt(request.getParameter("messagesessionid"));
			int groupid = Putil.getInt(request.getParameter("groupid"));
			int messagetypeid = Putil.getInt(request.getParameter("messagetypeid"));
			int sender = Putil.getInt(request.getParameter("sender"));
			int receiver = Putil.getInt(request.getParameter("receiver"));
			String content = Putil.getString(request.getParameter("content"));
			int groupseq = Putil.getInt(request.getParameter("groupseq"));
			int senderread = Putil.getInt(request.getParameter("senderread"));
			int receiverread = Putil.getInt(request.getParameter("receiverread"));
			int senderdelete = Putil.getInt(request.getParameter("senderdelete"));
			int receiverdelete = Putil.getInt(request.getParameter("receiverdelete"));
			String createdate = Putil.getString(request.getParameter("createdate"));






			
			StringBuilder select = new StringBuilder("insert into em_message (messagesessionid,groupid,messagetypeid,sender,receiver,content,groupseq,senderread,receiverread,senderdelete,receiverdelete,createdate) values ("
				+ "" + messagesessionid + ""
				+ "," + groupid + ""
				+ "," + messagetypeid + ""
				+ "," + sender + ""
				+ "," + receiver + ""
				+ ",'" + content.replace("'", "''") + "'"
				+ "," + groupseq + ""
				+ "," + senderread + ""
				+ "," + receiverread + ""
				+ "," + senderdelete + ""
				+ "," + receiverdelete + ""
				+ ",'" + createdate.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emMessage.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String messageid = Putil.getString(request.getParameter("messageid")) ;
		if(messageid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_message p where p.messageid="+messageid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/MessageEdit.jsp");
		
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
			String messageid = Putil.getString(request.getParameter("messageid")) ;
			int messagesessionid = Putil.getInt(request.getParameter("messagesessionid"));
			int groupid = Putil.getInt(request.getParameter("groupid"));
			int messagetypeid = Putil.getInt(request.getParameter("messagetypeid"));
			int sender = Putil.getInt(request.getParameter("sender"));
			int receiver = Putil.getInt(request.getParameter("receiver"));
			String content = Putil.getString(request.getParameter("content"));
			int groupseq = Putil.getInt(request.getParameter("groupseq"));
			int senderread = Putil.getInt(request.getParameter("senderread"));
			int receiverread = Putil.getInt(request.getParameter("receiverread"));
			int senderdelete = Putil.getInt(request.getParameter("senderdelete"));
			int receiverdelete = Putil.getInt(request.getParameter("receiverdelete"));
			String createdate = Putil.getString(request.getParameter("createdate"));




			
			StringBuilder select = new StringBuilder("update em_message set "
					+ "messagesessionid=" + messagesessionid + ""
					+ ",groupid=" + groupid + ""
					+ ",messagetypeid=" + messagetypeid + ""
					+ ",sender=" + sender + ""
					+ ",receiver=" + receiver + ""
					+ ",content='" + content.replace("'", "''") + "'"
					+ ",groupseq=" + groupseq + ""
					+ ",senderread=" + senderread + ""
					+ ",receiverread=" + receiverread + ""
					+ ",senderdelete=" + senderdelete + ""
					+ ",receiverdelete=" + receiverdelete + ""
					+ ",createdate='" + createdate.replace("'", "''") + "'"
				+ " where messageid=" + messageid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emMessage.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.messageid) as total from em_message p where p.messageid>=0"
					+ (keyword.length()>0?" and p.messagename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_message p where p.messageid>=0"
					+ (keyword.length()>0?" and p.messagename like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/MessageList.jsp");
		
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
			String messageid = Putil.getString(request.getParameter("messageid"));

			StringBuilder select = new StringBuilder("delete from em_message "
					+ " where messageid=" + messageid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emMessage.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

