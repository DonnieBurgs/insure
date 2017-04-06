package com.insure.servlet.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.Putil;
import com.web.util.StringUtils;

public class EmAuthorityServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 8459048427979079912L;
	private String auth83 = "";
	private String auth84 = "";
	
	private String authorityStr[] = {
			""		//1
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,"投保公司查看"		//11
			,"投保公司管理"
			,"承保公司查看"
			,"承保公司管理"
			,"团保查看"
			,"团保管理"
			,"方案查看"
			,"方案管理"
			,""
			,""
			,"个人保单查看"		//21
			,"个人保单管理"
			,""
			,""
			,"被保险人查看"
			,"被保险人管理"
			,"险种组查看"
			,"险种组管理"
			,"险种查看"
			,"险种管理"
			,"团保方案查看"		//31
			,"团保方案管理"
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,"案卷查看"		//41
			,"案卷管理"
			,"案件查看"
			,"案件管理"
			,"案件复核"
			,""
			,""
			,""
			,"影像查看"
			,"影像上传"
			,"疾病查看"		//51
			,"疾病管理"
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""		//61
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""		//71
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,""
			,"管理员查看"		//81
			,"管理员管理"
			,"权限查看"
			,"权限管理"
			,"登录日志"
			,"操作日志"
			,"参数查看"
			,"参数管理"
			,""
			,""
			,""
			,""
			,""
			,""
	} ;

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth84.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		request.setAttribute("authorityStr", authorityStr);
		forward(request, response, "/admin/AuthorityAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth84.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		Connection connection = DbUtils.getConnection();
		Statement statement = null;
		Statement innerStatement = null;
		ResultSet resultSet = null;
		ResultSet addonResultSet = null;
		
		try {
			statement = connection.createStatement();
			innerStatement = connection.createStatement();

			String authorityname = Putil.getString(request.getParameter("authorityname")) ;
			String authorityno = "" ; //"000000000000000000000000000000000000000000000000000000000000" ;
			String note = Putil.getString(request.getParameter("note")) ;
			String ctype = Putil.getString(request.getParameter("ctype")) ;
			if(ctype.length()==0) ctype = "0" ;
			for(int i=0;i<99;i++) {
				if(Putil.getString(request.getParameter("a"+(i+1))).equals("1")) {
					authorityno += "1" ;
				} else {
					authorityno += "0" ;
				}
			}
			
			//StringBuilder select = new StringBuilder("insert into em_authority (authorityname,authorityno,note) values (seq_crm_sms.nextval,'" + authorityname + ""
			StringBuilder select = new StringBuilder("insert into em_authority (authorityname,authorityno,ctype,note) values ('" + authorityname + ""
					+ "','" + authorityno + "'"
					+ "," + ctype + ""
					+ ",'" + note + "')"
					
					);

			int result = statement.executeUpdate(select.toString());
	
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils.close(addonResultSet, resultSet, innerStatement, statement, connection);
		}

		redirect(request, response, "/emAuthority.do?method=list");
		
	}

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = Putil.getString(request.getParameter("id"));
		Map<String, Object> row = DbUtils.queryOne("select * from em_authority p where p.authorityid="+id);

		request.setAttribute("authorityStr", authorityStr);
		request.setAttribute("item", row);
		forward(request, response, "/admin/AuthorityEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth84.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		Connection connection = DbUtils.getConnection();
		Statement statement = null;
		Statement innerStatement = null;
		ResultSet resultSet = null;
		ResultSet addonResultSet = null;

		try {
			statement = connection.createStatement();
			innerStatement = connection.createStatement();

			String authorityid = Putil.getString(request.getParameter("authorityid"));
			String authorityname = Putil.getString(request.getParameter("authorityname")) ;
			String ctype = Putil.getString(request.getParameter("ctype")) ;
			if(ctype.length()==0) ctype = "0" ;
			String authorityno = "" ;
			String note = Putil.getString(request.getParameter("note")) ;
			for(int i=0;i<99;i++) {
				if(Putil.getString(request.getParameter("a"+(i+1))).equals("1")) {
					authorityno += "1" ;
				} else {
					authorityno += "0" ;
				}
			}
			StringBuilder select = new StringBuilder("update em_authority set "
					+ "authorityname = '" + authorityname + "'"
					+ ",authorityno = '" + authorityno + "'"
					+ ",ctype = " + ctype + ""
					+ ",note = '" + note + "'"
					+ " where authorityid=" + authorityid + "" 
					);

			int result = statement.executeUpdate(select.toString());

		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils.close(addonResultSet, resultSet, innerStatement, statement, connection);
		}

		redirect(request, response, "/emAuthority.do?method=list");
		
	}

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = DbUtils.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			long totalCount = 0 ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			

			// 列表
			if(s==0) s = 0 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.authorityid) as total from em_authority p");
			resultSet = statement.executeQuery(countSql.toString());
			if (resultSet.next()) {
				totalCount = resultSet.getLong(1);
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.authorityid, p.authorityname, p.authorityno, p.ctype, p.note from em_authority p"
						+ " order by p.ctype,p.authorityid limit " + s + "," + (s+m) + ""
						);
			
			System.out.println("resultRows"+resultRows.size());
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");

		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils.close(resultSet, statement, connection);
		}

		forward(request, response, "/admin/AuthorityList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth84.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		Connection connection = DbUtils.getConnection();
		Statement statement = null;
		Statement innerStatement = null;
		ResultSet resultSet = null;
		ResultSet addonResultSet = null;

		try {
			statement = connection.createStatement();
			innerStatement = connection.createStatement();

			String authorityid = Putil.getString(request.getParameter("authorityid"));

			StringBuilder select = new StringBuilder("delete from em_authority "
					+ " where authorityid=" + authorityid + "" 
					);

			int result = statement.executeUpdate(select.toString());

		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils.close(addonResultSet, resultSet, innerStatement, statement, connection);
		}

		redirect(request, response, "/emAuthority.do?method=list");
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth83 = checkAuthority(45, request)!=null?"1":"";
    	auth84 = checkAuthority(46, request)!=null?"1":"";
		request.setAttribute("auth83", auth83);
		request.setAttribute("auth84", auth84);

    	if(auth83.equals("") && auth84.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    }


}

