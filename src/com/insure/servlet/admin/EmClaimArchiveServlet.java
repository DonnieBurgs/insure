package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaimArchive</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmClaimArchiveServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaimArchive</servlet-name>
		<url-pattern>/emClaimArchive.do</url-pattern>
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

public class EmClaimArchiveServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580695406L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth42", request, response)) {
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

		forward(request, response, "/admin/ClaimArchiveAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth42", request, response)) {
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
			int groupinsurancepolicyid = Putil.getInt(request.getParameter("groupinsurancepolicyid"));
			String acceptdate = Putil.getString(request.getParameter("acceptdate"));
			String claimarchivenumber = Putil.getString(request.getParameter("claimarchivenumber"));






			
			StringBuilder select = new StringBuilder("insert into em_claimarchive (groupinsurancepolicyid,acceptdate,claimarchivenumber) values ("
				+ "" + groupinsurancepolicyid + ""
				+ ",'" + acceptdate.replace("'", "''") + "'"
				+ ",'" + claimarchivenumber.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimArchive.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String id = Putil.getString(request.getParameter("id")) ;
		if(id.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claimarchive p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ClaimArchiveEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth42", request, response)) {
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
			String id = Putil.getString(request.getParameter("id")) ;
			int groupinsurancepolicyid = Putil.getInt(request.getParameter("groupinsurancepolicyid"));
			String acceptdate = Putil.getString(request.getParameter("acceptdate"));
			String claimarchivenumber = Putil.getString(request.getParameter("claimarchivenumber"));




			
			StringBuilder select = new StringBuilder("update em_claimarchive set "
					+ "groupinsurancepolicyid=" + groupinsurancepolicyid + ""
					+ ",acceptdate='" + acceptdate.replace("'", "''") + "'"
					+ ",claimarchivenumber='" + claimarchivenumber.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emClaimArchive.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claimarchive p where p.id>=0"
					+ (keyword.length()>0?" and p.claimarchivename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claimarchive p where p.id>=0"
					+ (keyword.length()>0?" and p.claimarchivename like '%" + keyword + "%'":"")
					+ " order by p.id desc limit " + (s-1)*m + "," + m + ""
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

		forward(request, response, "/admin/ClaimArchiveList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth42", request, response)) {
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
			String id = Putil.getString(request.getParameter("id"));

			StringBuilder select = new StringBuilder("delete from em_claimarchive "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimArchive.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth41 = checkAuthority(41, request)!=null?"1":"";
    	String auth42 = checkAuthority(42, request)!=null?"1":"";
		request.setAttribute("auth41", auth41);
		request.setAttribute("auth42", auth42);
    	if(auth41.equals("") && auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
    
    @Override
    public void def(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

		String keyword = Putil.getString(request.getParameter("term")) ;
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		String sql = "select p.* from em_claimarchive p where p.id>=0"
				+ (keyword.length()>0?" and p.claimarchivenumber like '%" + keyword + "%'":"")
				+ " order by p.id desc limit 50";
    	resultRows = DbUtils.query(sql);
		
    	if(resultRows != null && !resultRows.isEmpty()){
    		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    		for (Map<String, Object> map : resultRows) {
    			Map<String, Object> row = new HashMap<>();
    			row.put("id", map.get("id"));
    			row.put("label", map.get("claimarchivenumber"));
    			row.put("value", map.get("claimarchivenumber"));
    			rows.add(row);
			}
    		toJson(rows, response);
    	}else {
			toJson(new ArrayList<>(), response);
		}
    	
    }
}

