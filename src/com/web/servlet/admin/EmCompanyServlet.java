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

public class EmCompanyServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1458798887992L;
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

		//地区列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from em_area p where parentid=0 order by seq desc");
		request.setAttribute("areaList1", resultRows);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_companytypeid = Putil.getString(request.getParameter("key_companytypeid")) ;
		String key_isaudit = Putil.getString(request.getParameter("key_isaudit")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_companytypeid", key_companytypeid);
		request.setAttribute("key_isaudit", key_isaudit);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		forward(request, response, "/admin/CompanyAdd.jsp");
	
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
		String key_companytypeid = Putil.getString(request.getParameter("key_companytypeid")) ;
		String key_isaudit = Putil.getString(request.getParameter("key_isaudit")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_companytypeid", key_companytypeid);
		request.setAttribute("key_isaudit", key_isaudit);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		Map<String, Object> admin = LoginManager.getUser(request);
		if(admin==null) {
			return ;
		}
		try {
			String companyname = Putil.getString(request.getParameter("companyname"));
			String contact = Putil.getString(request.getParameter("contact"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int companytypeid = Putil.getInt(request.getParameter("companytypeid"));
			int servicetypeid = Putil.getInt(request.getParameter("servicetypeid"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			String startdate = Putil.getString(request.getParameter("startdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			String account = Putil.getString(request.getParameter("account"));
			String password = Putil.getString(request.getParameter("password"));
			
			StringBuilder select = new StringBuilder("insert into em_company (companyname,contact,mobile,companytypeid,servicetypeid,areaid,addr,isvalid,startdate,expdate,createdate) values ("
				+ "'" + companyname.replace("'", "''") + "'"
				+ ",'" + contact.replace("'", "''") + "'"
				+ ",'" + mobile.replace("'", "''") + "'"
				+ "," + companytypeid + ""
				+ "," + servicetypeid + ""
				+ "," + areaid + ""
				+ ",'" + addr.replace("'", "''") + "'"
				+ "," + isvalid + ""
				+ ",'" + startdate.replace("'", "''") + "'"
				+ ",'" + expdate.replace("'", "''") + "'"
				+ ",SYSDATE()"
				+ ")"
			);
			int result = DbUtils.save(select.toString());

			Map<String, Object> row = DbUtils.queryOne("select p.* from em_company p order by p.companyid desc");
			String companyid = Putil.getString(row.get("companyid")) ;			

			//生成管理员
			if(account.length()>0 && password.length()>0) {
				select = new StringBuilder("insert into em_user (account,password,username,companyid,usertype,parentid,sex,age,identity,mobile,isvalid,photo,isregist,openid,createdate,tel,addr,note,balance,devicetoken,devicetokendate,lng,lat,latlngdate,deviceid,seq,authorityid,isdelete) values ("
						+ "'" + account.replace("'", "''") + "'"
						+ ",'" + password.replace("'", "''") + "'"
						+ ",'" + account.replace("'", "''") + "'"
						+ "," + companyid + ""
						+ ",1"
						+ ",0"
						+ ",'男'"
						+ ",'0'"
						+ ",''"
						+ ",'" + account.replace("'", "''") + "'"
						+ ",1"
						+ ",''"
						+ ",0"
						+ ",''"
						+ ",SYSDATE()"
						+ ",'" + account.replace("'", "''") + "'"
						+ ",''"
						+ ",''"
						+ ",0"
						+ ",''"
						+ ",SYSDATE()"
						+ ",0"
						+ ",0"
						+ ",SYSDATE()"
						+ ",''"
						+ ",0"
						+ ",10002"
						+ ",0"
						+ ")"
					);

				DbUtils.save(select.toString());
			}

			DbUtils.save(select.toString());

	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emCompany.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_companytypeid="+key_companytypeid+"&key_isaudit="+key_isaudit+"&m="+m+"&s="+s);
		
	}

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_companytypeid = Putil.getString(request.getParameter("key_companytypeid")) ;
		String key_isaudit = Putil.getString(request.getParameter("key_isaudit")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_companytypeid", key_companytypeid);
		request.setAttribute("key_isaudit", key_isaudit);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		//地区列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from em_area p where parentid=0 order by seq desc");
		request.setAttribute("areaList1", resultRows);
		
		String companyid = Putil.getString(request.getParameter("companyid")) ;
		if(companyid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_company p where p.companyid="+companyid);
			request.setAttribute("item", row);
			if(row!=null) {
				String areaid = Putil.getString(row.get("areaid"));
				Map<String, Object> area = DbUtils.queryOne("select a2.parentid,a2.areaid from em_area a2,sc_area a3 where a3.parentid=a2.areaid and a3.areaid="+areaid);
				if(area!=null) {
					String areaid1 = Putil.getString(area.get("parentid"));
					String areaid2 = Putil.getString(area.get("areaid"));
					//地区列表
					List<Map<String, Object>> resultRows2 = DbUtils.query("select * from em_area p where parentid="+areaid1 + " order by seq desc");
					request.setAttribute("areaList2", resultRows2);
					//地区列表
					List<Map<String, Object>> resultRows3 = DbUtils.query("select * from em_area p where parentid="+areaid2 + " order by seq desc");
					request.setAttribute("areaList3", resultRows3);
					request.setAttribute("areaid1", areaid1);
					request.setAttribute("areaid2", areaid2);
					request.setAttribute("areaid", areaid);
					
				}
				
			}
			//取管理员账号
			Map<String, Object> user = DbUtils.queryOne("select p.* from em_user p where p.companyid="+companyid + " order by p.userid");
			request.setAttribute("user", user);
		}
		
		

		request.setAttribute("auth51", auth51);
		request.setAttribute("auth52", auth52);
		forward(request, response, "/admin/CompanyEdit.jsp");
		
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
		String key_companytypeid = Putil.getString(request.getParameter("key_companytypeid")) ;
		String key_isaudit = Putil.getString(request.getParameter("key_isaudit")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_companytypeid", key_companytypeid);
		request.setAttribute("key_isaudit", key_isaudit);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String companyid = Putil.getString(request.getParameter("companyid")) ;
			String companyname = Putil.getString(request.getParameter("companyname"));
			String contact = Putil.getString(request.getParameter("contact"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int companytypeid = Putil.getInt(request.getParameter("companytypeid"));
			int servicetypeid = Putil.getInt(request.getParameter("servicetypeid"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			int isaudit = Putil.getInt(request.getParameter("isaudit"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			String startdate = Putil.getString(request.getParameter("startdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			
			StringBuilder select = new StringBuilder("update em_company set "
					+ "companyname='" + companyname.replace("'", "''") + "'"
					+ ",contact='" + contact.replace("'", "''") + "'"
					+ ",mobile='" + mobile.replace("'", "''") + "'"
					+ ",companytypeid=" + companytypeid + ""
					+ ",servicetypeid=" + servicetypeid + ""
//					+ ",areaid=" + areaid + ""
					+ ",addr='" + addr.replace("'", "''") + "'"
					+ ",isaudit=" + isaudit + ""
					+ ",isvalid=" + isvalid + ""
					+ ",startdate='" + startdate.replace("'", "''") + "'"
					+ ",expdate='" + expdate.replace("'", "''") + "'"
				+ " where companyid=" + companyid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emCompany.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_companytypeid="+key_companytypeid+"&key_isaudit="+key_isaudit+"&m="+m+"&s="+s);
		
	}

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		try {
			long totalCount = 0 ;
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			String key_companytypeid = Putil.getString(request.getParameter("key_companytypeid")) ;
			String key_isaudit = Putil.getString(request.getParameter("key_isaudit")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			
			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.companyid) as total from em_company p where p.companyid>=0 and p.areaid=a.areaid"
					+ (key_companytypeid.length()>0?" and p.companytypeid=" + key_companytypeid:"")
					+ (key_isaudit.length()>0?" and p.isaudit=" + key_isaudit:"")
					+ (keyword.length()>0?" and p.companyname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_company p where p.companyid>=0"
					+ (key_companytypeid.length()>0?" and p.companytypeid=" + key_companytypeid:"")
					+ (key_isaudit.length()>0?" and p.isaudit=" + key_isaudit:"")
					+ (keyword.length()>0?" and p.companyname like '%" + keyword + "%'":"")
						+ " order by p.companyid desc limit " + (s-1)*m + "," + m + ""
						);
			
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("keyword", keyword);
			request.setAttribute("key_companytypeid", key_companytypeid);
			request.setAttribute("key_isaudit", key_isaudit);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");
			request.setAttribute("auth51", auth51);
			request.setAttribute("auth52", auth52);

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		forward(request, response, "/admin/CompanyList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_companytypeid = Putil.getString(request.getParameter("key_companytypeid")) ;
		String key_isaudit = Putil.getString(request.getParameter("key_isaudit")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_companytypeid", key_companytypeid);
		request.setAttribute("key_isaudit", key_isaudit);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String companyid = Putil.getString(request.getParameter("companyid"));

			StringBuilder select = new StringBuilder("delete from em_company "
					+ " where companyid=" + companyid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emCompany.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_companytypeid="+key_companytypeid+"&key_isaudit="+key_isaudit+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth51 = checkAuthority(51, request)!=null?"1":"";
    	auth52 = checkAuthority(52, request)!=null?"1":"";
    	if(auth51.equals("") && auth52.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

