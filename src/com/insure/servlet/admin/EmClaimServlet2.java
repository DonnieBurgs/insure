package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaim</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmClaimServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaim</servlet-name>
		<url-pattern>/emClaim.do</url-pattern>
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

public class EmClaimServlet2 extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580695501L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth44", request, response)) {
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

		forward(request, response, "/admin/ClaimAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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
		}int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
		try {
			
			String serialnumber = Putil.getString(request.getParameter("serialnumber"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			String bardh = Putil.getString(request.getParameter("bardh"));






			
			StringBuilder select = new StringBuilder("insert into em_claim (claimarchiveid,serialnumber,insuredid,bardh) values ("
				+ "" + claimarchiveid + ""
				+ ",'" + serialnumber.replace("'", "''") + "'"
				+ "," + insuredid + ""
				+ ",'" + bardh.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s+"&keyword="+claimarchiveid);
		
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
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claim p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ClaimEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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
			int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
			String serialnumber = Putil.getString(request.getParameter("serialnumber"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			String bardh = Putil.getString(request.getParameter("bardh"));




			
			StringBuilder select = new StringBuilder("update em_claim set "
					+ "claimarchiveid=" + claimarchiveid + ""
					+ ",serialnumber='" + serialnumber.replace("'", "''") + "'"
					+ ",insuredid=" + insuredid + ""
					+ ",bardh='" + bardh.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claim p where p.id>=0"
					+ (keyword.length()>0?" and p.claimname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claim p where p.id>=0"
					+ (keyword.length()>0?" and p.claimname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/ClaimList2.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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

			StringBuilder select = new StringBuilder("delete from em_claim "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth43 = checkAuthority(43, request)!=null?"1":"";
    	String auth44 = checkAuthority(44, request)!=null?"1":"";
		request.setAttribute("auth43", auth43);
		request.setAttribute("auth44", auth44);
    	if(auth43.equals("") && auth44.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
    
    @Override
    public void def(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	  String method = request.getParameter("method");
          if("autocomplete".equals(method)) {
              autocomplate(request, response);
          } else {
        	  listImage(request, response);
          }
          
    	
    }
    
    private void autocomplate(HttpServletRequest request, HttpServletResponse response) {
    	String keyword = Putil.getString(request.getParameter("term")) ;
    	String claimarchiveid = Putil.getString(request.getParameter("claimarchiveid")) ;
		String inc = Putil.getString(request.getParameter("inc")) ;
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder("select p.* from em_claim p where p.id>=0");
		if(claimarchiveid.length() > 0)
			sql.append(" and p.claimarchiveid = '" + claimarchiveid + "'");
		if(keyword.length() > 0){
			sql.append(" and p.serialnumber like '%" + keyword + "%'");

			if(StringUtils.hasLength(inc)){
				String[] arr = inc.split(",");
				for (String string : arr) {
					sql.append(" or p." + string + " like '%" + keyword + "%'");
				}
			}
		}
		sql.append(" order by p.id desc limit 50");
    	resultRows = DbUtils.query(sql.toString());
		
    	if(resultRows != null && !resultRows.isEmpty()){
    		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    		for (Map<String, Object> map : resultRows) {
    			Map<String, Object> row = new HashMap<>();
    			row.put("id", map.get("id"));
    			row.put("label", map.get("serialnumber"));
    			row.put("value", map.get("serialnumber"));
    			rows.add(row);
			}
    		toJson(rows, response);
    	}else {
			toJson(new ArrayList<>(), response);
		}
	}

	private void listImage(HttpServletRequest request, HttpServletResponse response){
    	String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		try {
			long totalCount = 0 ;
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			
			
			int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claim p where p.id>=0"
					+ (claimarchiveid >0?" and p.claimarchiveid = " + claimarchiveid : "")
					+ (insuredid >0?" and p.insuredid = " + insuredid : "")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claim p where p.id>=0"
					+ (claimarchiveid >0?" and p.claimarchiveid = " + claimarchiveid : "")
					+ (insuredid >0?" and p.insuredid = " + insuredid : "")
					+ " order by p.id desc limit " + (s-1)*m + "," + m + ""
				);
			
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("keyword", keyword);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");
			request.setAttribute("claimarchiveid", claimarchiveid);
			request.setAttribute("insuredid", insuredid);
			request.setAttribute("emClaimArchive", request.getParameter("emClaimArchive"));
			request.setAttribute("emInsured", request.getParameter("emInsured"));

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		forward(request, response, "/admin/ClaimListImage.jsp");
    }
}

