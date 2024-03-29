package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emDetermine</servlet-name>
		<servlet-class>com.web.servlet.admin.EmDetermineServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emDetermine</servlet-name>
		<url-pattern>/emDetermine.do</url-pattern>
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

public class EmDetermineServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491555808482L;
	
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

		forward(request, response, "/admin/DetermineAdd.jsp");
	
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
		}
		try {
			int claimid = Putil.getInt(request.getParameter("claimid"));
			int diseaseid = Putil.getInt(request.getParameter("diseaseid"));
			String claimtype = Putil.getString(request.getParameter("claimtype"));
			int claimstatus = Putil.getInt(request.getParameter("claimstatus"));
			float cwfamount = Putil.getFloat(request.getParameter("cwfamount"));
			float tfpfamount = Putil.getFloat(request.getParameter("tfpfamount"));
			float sqamount = Putil.getFloat(request.getParameter("sqamount"));
			String dereason = Putil.getString(request.getParameter("dereason"));
			float yyfamount = Putil.getFloat(request.getParameter("yyfamount"));
			float jsbyyfamount = Putil.getFloat(request.getParameter("jsbyyfamount"));
			float zfyyfamount = Putil.getFloat(request.getParameter("zfyyfamount"));
			String fzyyfremark = Putil.getString(request.getParameter("fzyyfremark"));
			float yyftcamount = Putil.getFloat(request.getParameter("yyftcamount"));
			String yyfremark = Putil.getString(request.getParameter("yyfremark"));
			float jcfamount = Putil.getFloat(request.getParameter("jcfamount"));
			float gkjjcfamount = Putil.getFloat(request.getParameter("gkjjcfamount"));
			int gkjjcjgpool = Putil.getInt(request.getParameter("gkjjcjgpool"));
			float zfjcfamount = Putil.getFloat(request.getParameter("zfjcfamount"));
			String zfjcfremark = Putil.getString(request.getParameter("zfjcfremark"));
			float jcftcamount = Putil.getFloat(request.getParameter("jcftcamount"));
			String jcftcremark = Putil.getString(request.getParameter("jcftcremark"));






			
			StringBuilder select = new StringBuilder("insert into em_determine (claimid,diseaseid,claimtype,claimstatus,cwfamount,tfpfamount,sqamount,dereason,yyfamount,jsbyyfamount,zfyyfamount,fzyyfremark,yyftcamount,yyfremark,jcfamount,gkjjcfamount,gkjjcjgpool,zfjcfamount,zfjcfremark,jcftcamount,jcftcremark) values ("
				+ "" + claimid + ""
				+ "," + diseaseid + ""
				+ ",'" + claimtype.replace("'", "''") + "'"
				+ "," + claimstatus + ""
				+ "," + cwfamount + ""
				+ "," + tfpfamount + ""
				+ "," + sqamount + ""
				+ ",'" + dereason.replace("'", "''") + "'"
				+ "," + yyfamount + ""
				+ "," + jsbyyfamount + ""
				+ "," + zfyyfamount + ""
				+ ",'" + fzyyfremark.replace("'", "''") + "'"
				+ "," + yyftcamount + ""
				+ ",'" + yyfremark.replace("'", "''") + "'"
				+ "," + jcfamount + ""
				+ "," + gkjjcfamount + ""
				+ "," + gkjjcjgpool + ""
				+ "," + zfjcfamount + ""
				+ ",'" + zfjcfremark.replace("'", "''") + "'"
				+ "," + jcftcamount + ""
				+ ",'" + jcftcremark.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emDetermine.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String determineid = Putil.getString(request.getParameter("determineid")) ;
		if(determineid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_determine p where p.determineid="+determineid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/DetermineEdit.jsp");
		
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
			String determineid = Putil.getString(request.getParameter("determineid")) ;
			int claimid = Putil.getInt(request.getParameter("claimid"));
			int diseaseid = Putil.getInt(request.getParameter("diseaseid"));
			String claimtype = Putil.getString(request.getParameter("claimtype"));
			int claimstatus = Putil.getInt(request.getParameter("claimstatus"));
			float cwfamount = Putil.getFloat(request.getParameter("cwfamount"));
			float tfpfamount = Putil.getFloat(request.getParameter("tfpfamount"));
			float sqamount = Putil.getFloat(request.getParameter("sqamount"));
			String dereason = Putil.getString(request.getParameter("dereason"));
			float yyfamount = Putil.getFloat(request.getParameter("yyfamount"));
			float jsbyyfamount = Putil.getFloat(request.getParameter("jsbyyfamount"));
			float zfyyfamount = Putil.getFloat(request.getParameter("zfyyfamount"));
			String fzyyfremark = Putil.getString(request.getParameter("fzyyfremark"));
			float yyftcamount = Putil.getFloat(request.getParameter("yyftcamount"));
			String yyfremark = Putil.getString(request.getParameter("yyfremark"));
			float jcfamount = Putil.getFloat(request.getParameter("jcfamount"));
			float gkjjcfamount = Putil.getFloat(request.getParameter("gkjjcfamount"));
			int gkjjcjgpool = Putil.getInt(request.getParameter("gkjjcjgpool"));
			float zfjcfamount = Putil.getFloat(request.getParameter("zfjcfamount"));
			String zfjcfremark = Putil.getString(request.getParameter("zfjcfremark"));
			float jcftcamount = Putil.getFloat(request.getParameter("jcftcamount"));
			String jcftcremark = Putil.getString(request.getParameter("jcftcremark"));




			
			StringBuilder select = new StringBuilder("update em_determine set "
					+ "claimid=" + claimid + ""
					+ ",diseaseid=" + diseaseid + ""
					+ ",claimtype='" + claimtype.replace("'", "''") + "'"
					+ ",claimstatus=" + claimstatus + ""
					+ ",cwfamount=" + cwfamount + ""
					+ ",tfpfamount=" + tfpfamount + ""
					+ ",sqamount=" + sqamount + ""
					+ ",dereason='" + dereason.replace("'", "''") + "'"
					+ ",yyfamount=" + yyfamount + ""
					+ ",jsbyyfamount=" + jsbyyfamount + ""
					+ ",zfyyfamount=" + zfyyfamount + ""
					+ ",fzyyfremark='" + fzyyfremark.replace("'", "''") + "'"
					+ ",yyftcamount=" + yyftcamount + ""
					+ ",yyfremark='" + yyfremark.replace("'", "''") + "'"
					+ ",jcfamount=" + jcfamount + ""
					+ ",gkjjcfamount=" + gkjjcfamount + ""
					+ ",gkjjcjgpool=" + gkjjcjgpool + ""
					+ ",zfjcfamount=" + zfjcfamount + ""
					+ ",zfjcfremark='" + zfjcfremark.replace("'", "''") + "'"
					+ ",jcftcamount=" + jcftcamount + ""
					+ ",jcftcremark='" + jcftcremark.replace("'", "''") + "'"
				+ " where determineid=" + determineid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emDetermine.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.determineid) as total from em_determine p where p.determineid>=0"
					+ (keyword.length()>0?" and p.determinename like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_determine p where p.determineid>=0"
					+ (keyword.length()>0?" and p.determinename like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/DetermineList.jsp");
		
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
			String determineid = Putil.getString(request.getParameter("determineid"));

			StringBuilder select = new StringBuilder("delete from em_determine "
					+ " where determineid=" + determineid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emDetermine.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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
}

