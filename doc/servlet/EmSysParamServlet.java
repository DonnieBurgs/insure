package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emSysParam</servlet-name>
		<servlet-class>com.web.servlet.admin.EmSysParamServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emSysParam</servlet-name>
		<url-pattern>/emSysParam.do</url-pattern>
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

public class EmSysParamServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491449284424L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth88 = request.getAttribute("auth88");
    	if(auth88.equals("")) {
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

		forward(request, response, "/admin/SysParamAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth88 = request.getAttribute("auth88");
    	if(auth88.equals("")) {
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
			int companyid = Putil.getInt(request.getParameter("companyid"));
			int alarmnumcheckin = Putil.getInt(request.getParameter("alarmnumcheckin"));
			int alarmtimecheckin = Putil.getInt(request.getParameter("alarmtimecheckin"));
			int alarmnumsign = Putil.getInt(request.getParameter("alarmnumsign"));
			int alarmtimesign = Putil.getInt(request.getParameter("alarmtimesign"));
			int alarmnumleave = Putil.getInt(request.getParameter("alarmnumleave"));
			int alarmtimeleave = Putil.getInt(request.getParameter("alarmtimeleave"));






			
			StringBuilder select = new StringBuilder("insert into em_sysparam (companyid,alarmnumcheckin,alarmtimecheckin,alarmnumsign,alarmtimesign,alarmnumleave,alarmtimeleave) values ("
				+ "" + companyid + ""
				+ "," + alarmnumcheckin + ""
				+ "," + alarmtimecheckin + ""
				+ "," + alarmnumsign + ""
				+ "," + alarmtimesign + ""
				+ "," + alarmnumleave + ""
				+ "," + alarmtimeleave + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emSysParam.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String sysparamid = Putil.getString(request.getParameter("sysparamid")) ;
		if(sysparamid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_sysparam p where p.sysparamid="+sysparamid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/SysParamEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth88 = request.getAttribute("auth88");
    	if(auth88.equals("")) {
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
			String sysparamid = Putil.getString(request.getParameter("sysparamid")) ;
			int companyid = Putil.getInt(request.getParameter("companyid"));
			int alarmnumcheckin = Putil.getInt(request.getParameter("alarmnumcheckin"));
			int alarmtimecheckin = Putil.getInt(request.getParameter("alarmtimecheckin"));
			int alarmnumsign = Putil.getInt(request.getParameter("alarmnumsign"));
			int alarmtimesign = Putil.getInt(request.getParameter("alarmtimesign"));
			int alarmnumleave = Putil.getInt(request.getParameter("alarmnumleave"));
			int alarmtimeleave = Putil.getInt(request.getParameter("alarmtimeleave"));




			
			StringBuilder select = new StringBuilder("update em_sysparam set "
					+ "companyid=" + companyid + ""
					+ ",alarmnumcheckin=" + alarmnumcheckin + ""
					+ ",alarmtimecheckin=" + alarmtimecheckin + ""
					+ ",alarmnumsign=" + alarmnumsign + ""
					+ ",alarmtimesign=" + alarmtimesign + ""
					+ ",alarmnumleave=" + alarmnumleave + ""
					+ ",alarmtimeleave=" + alarmtimeleave + ""
				+ " where sysparamid=" + sysparamid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emSysParam.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.sysparamid) as total from em_sysparam p where p.sysparamid>=0"
					+ (keyword.length()>0?" and p.sysparamname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_sysparam p where p.sysparamid>=0"
					+ (keyword.length()>0?" and p.sysparamname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/SysParamList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String auth88 = request.getAttribute("auth88");
    	if(auth88.equals("")) {
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
			String sysparamid = Putil.getString(request.getParameter("sysparamid"));

			StringBuilder select = new StringBuilder("delete from em_sysparam "
					+ " where sysparamid=" + sysparamid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emSysParam.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth87 = checkAuthority(87, request)!=null?"1":"";
    	String auth88 = checkAuthority(88, request)!=null?"1":"";
		request.setAttribute("auth87", auth87);
		request.setAttribute("auth88", auth88);
    	if(auth87.equals("") && auth88.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

