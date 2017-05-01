package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaimInfo</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmClaimInfoServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaimInfo</servlet-name>
		<url-pattern>/emClaimInfo.do</url-pattern>
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

public class EmClaimInfoServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1492585073040L;
	
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

		request.setAttribute("claimtypeMap", claimtypeMap);
		request.setAttribute("claimreasonMap", claimreasonMap);
		request.setAttribute("paytypeMap", paytypeMap);
		request.setAttribute("insuretypeMap", insuretypeMap);

		forward(request, response, "/admin/ClaimInfoAdd.jsp");
	
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
			int claimtype = Putil.getInt(request.getParameter("claimtype"));
			int claimreason = Putil.getInt(request.getParameter("claimreason"));
			String claimdate = Putil.getString(request.getParameter("claimdate"));
			int paytype = Putil.getInt(request.getParameter("paytype"));
			String insuretype1 = Putil.getString(request.getParameter("insuretype1"));
			String insuretype2 = Putil.getString(request.getParameter("insuretype2"));
			String insuretype3 = Putil.getString(request.getParameter("insuretype3"));
			float spamount = Putil.getFloat(request.getParameter("spamount"));
			int diseaseid = Putil.getInt(request.getParameter("diseaseid"));
			int determinecount = Putil.getInt(request.getParameter("determinecount"));
			int receiptcount = Putil.getInt(request.getParameter("receiptcount"));
			String mark = Putil.getString(request.getParameter("mark"));






			
			StringBuilder select = new StringBuilder("insert into em_claiminfo (claimid,claimtype,claimreason,claimdate,paytype,insuretype1,insuretype2,insuretype3,spamount,diseaseid,determinecount,receiptcount,mark) values ("
				+ "" + claimid + ""
				+ "," + claimtype + ""
				+ "," + claimreason + ""
				+ ",'" + claimdate.replace("'", "''") + "'"
				+ "," + paytype + ""
				+ ",'" + insuretype1.replace("'", "''") + "'"
				+ ",'" + insuretype2.replace("'", "''") + "'"
				+ ",'" + insuretype3.replace("'", "''") + "'"
				+ "," + spamount + ""
				+ "," + diseaseid + ""
				+ "," + determinecount + ""
				+ "," + receiptcount + ""
				+ ",'" + mark.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimInfo.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claiminfo p where p.id="+id);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/ClaimInfoEdit.jsp");
		
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
			int claimid = Putil.getInt(request.getParameter("claimid"));
			int claimtype = Putil.getInt(request.getParameter("claimtype"));
			int claimreason = Putil.getInt(request.getParameter("claimreason"));
			String claimdate = Putil.getString(request.getParameter("claimdate"));
			int paytype = Putil.getInt(request.getParameter("paytype"));
			String insuretype1 = Putil.getString(request.getParameter("insuretype1"));
			String insuretype2 = Putil.getString(request.getParameter("insuretype2"));
			String insuretype3 = Putil.getString(request.getParameter("insuretype3"));
			float spamount = Putil.getFloat(request.getParameter("spamount"));
			int diseaseid = Putil.getInt(request.getParameter("diseaseid"));
			int determinecount = Putil.getInt(request.getParameter("determinecount"));
			int receiptcount = Putil.getInt(request.getParameter("receiptcount"));
			String mark = Putil.getString(request.getParameter("mark"));




			
			StringBuilder select = new StringBuilder("update em_claiminfo set "
					+ "claimid=" + claimid + ""
					+ ",claimtype=" + claimtype + ""
					+ ",claimreason=" + claimreason + ""
					+ ",claimdate='" + claimdate.replace("'", "''") + "'"
					+ ",paytype=" + paytype + ""
					+ ",insuretype1='" + insuretype1.replace("'", "''") + "'"
					+ ",insuretype2='" + insuretype2.replace("'", "''") + "'"
					+ ",insuretype3='" + insuretype3.replace("'", "''") + "'"
					+ ",spamount=" + spamount + ""
					+ ",diseaseid=" + diseaseid + ""
					+ ",determinecount=" + determinecount + ""
					+ ",receiptcount=" + receiptcount + ""
					+ ",mark='" + mark.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emClaimInfo.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claiminfo p where p.id>=0"
					+ (keyword.length()>0?" and p.claiminfoname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claiminfo p where p.id>=0"
					+ (keyword.length()>0?" and p.claiminfoname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/ClaimInfoList.jsp");
		
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

			StringBuilder select = new StringBuilder("delete from em_claiminfo "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaimInfo.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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
    
    public final static LinkedHashMap<String, String> claimtypeMap = new LinkedHashMap<>();
    public final static HashMap<String, String> claimreasonMap = new HashMap<>();
    public final static HashMap<String, String> paytypeMap = new HashMap<>();
    public final static HashMap<String, String> insuretypeMap = new HashMap<>();
    static{
    	claimtypeMap.put("2", "门诊");
    	claimtypeMap.put("1", "购药");
    	claimtypeMap.put("3", "住院");
    	
    	claimreasonMap.put("1", "疾病");
    	claimreasonMap.put("2", "意外");
    	
    	paytypeMap.put("1", "现金");
    	paytypeMap.put("2", "个人转账");
    	paytypeMap.put("3", "支票（转账支票）");
    	paytypeMap.put("4", "对公转账");
    	
    	insuretypeMap.put("01", "门诊费用");
    	insuretypeMap.put("02", "住院费用");
    	insuretypeMap.put("03", "住院费用");
    	insuretypeMap.put("04", "重大疾病");
    	insuretypeMap.put("05", "身故");
    	insuretypeMap.put("06", "残疾/烧伤");
    	insuretypeMap.put("07", "保费豁免");
    	insuretypeMap.put("08", "生育费用");
    }
}

