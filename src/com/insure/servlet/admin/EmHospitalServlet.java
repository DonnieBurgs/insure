package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emHospital</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmHospitalServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emHospital</servlet-name>
		<url-pattern>/emHospital.do</url-pattern>
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

public class EmHospitalServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580695973L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth52", request, response)) {
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

		forward(request, response, "/admin/HospitalAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth52", request, response)) {
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
			String hospitalname = Putil.getString(request.getParameter("hospitalname"));
			String grade = Putil.getString(request.getParameter("grade"));
			String hospitalcode = Putil.getString(request.getParameter("hospitalcode"));
			String pinyin = Putil.getString(request.getParameter("pinyin"));
			String domain = Putil.getString(request.getParameter("domain"));
			String province = Putil.getString(request.getParameter("province"));
			String region = Putil.getString(request.getParameter("region"));
			String county = Putil.getString(request.getParameter("county"));






			
			StringBuilder select = new StringBuilder("insert into em_hospital (hospitalname,grade,hospitalcode,pinyin,domain,province,region,county) values ("
				+ "'" + hospitalname.replace("'", "''") + "'"
				+ ",'" + grade.replace("'", "''") + "'"
				+ ",'" + hospitalcode.replace("'", "''") + "'"
				+ ",'" + pinyin.replace("'", "''") + "'"
				+ ",'" + domain.replace("'", "''") + "'"
				+ ",'" + province.replace("'", "''") + "'"
				+ ",'" + region.replace("'", "''") + "'"
				+ ",'" + county.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emHospital.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_hospital p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/HospitalEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth52", request, response)) {
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
			String hospitalname = Putil.getString(request.getParameter("hospitalname"));
			String grade = Putil.getString(request.getParameter("grade"));
			String hospitalcode = Putil.getString(request.getParameter("hospitalcode"));
			String pinyin = Putil.getString(request.getParameter("pinyin"));
			String domain = Putil.getString(request.getParameter("domain"));
			String province = Putil.getString(request.getParameter("province"));
			String region = Putil.getString(request.getParameter("region"));
			String county = Putil.getString(request.getParameter("county"));




			
			StringBuilder select = new StringBuilder("update em_hospital set "
					+ "hospitalname='" + hospitalname.replace("'", "''") + "'"
					+ ",grade='" + grade.replace("'", "''") + "'"
					+ ",hospitalcode='" + hospitalcode.replace("'", "''") + "'"
					+ ",pinyin='" + pinyin.replace("'", "''") + "'"
					+ ",domain='" + domain.replace("'", "''") + "'"
					+ ",province='" + province.replace("'", "''") + "'"
					+ ",region='" + region.replace("'", "''") + "'"
					+ ",county='" + county.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emHospital.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_hospital p where p.id>=0"
					+ (keyword.length()>0?" and p.hospitalname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_hospital p where p.id>=0"
					+ (keyword.length()>0?" and p.hospitalname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/HospitalList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth52", request, response)) {
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

			StringBuilder select = new StringBuilder("delete from em_hospital "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emHospital.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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
    
    @Override
    public void def(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    		throws ServletException, IOException {
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
    	resultRows = DbUtils.query("select p.* from em_hospital p where p.id>=0");
    	toJson(resultRows, httpservletresponse);
    }
}

