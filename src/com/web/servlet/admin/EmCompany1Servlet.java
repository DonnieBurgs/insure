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

public class EmCompany1Servlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1458798887992L;
	private String auth41 = "";
	private String auth42 = "";

	

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);
		request.setAttribute("s", Putil.getString(request.getParameter("s")));

		//地区列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from em_area p where parentid=0 order by seq desc");
		request.setAttribute("areaList1", resultRows);
		
		if(companyID>1) {
			Map<String, Object> row = DbUtils.queryOne("select p.*,pd.productname from em_company p,sc_product pd where p.productid=pd.productid and p.companyid="+companyID);
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
			Map<String, Object> user = DbUtils.queryOne("select p.* from em_user p,sc_position po where p.positionid=po.positionid and po.parentid=0 and p.usertype=1 and p.companyid="+companyID + " order by p.userid");
			request.setAttribute("user", user);
		}
		

		request.setAttribute("auth41", auth41);
		request.setAttribute("auth42", auth42);
		forward(request, response, "/admin/CompanyEdit1.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth42.equals("")) {
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
			String companyid = Putil.getString(request.getParameter("companyid")) ;
			String companyname = Putil.getString(request.getParameter("companyname"));
			String contact = Putil.getString(request.getParameter("contact"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			
			StringBuilder select = new StringBuilder("update em_company set "
					+ "companyname='" + companyname.replace("'", "''") + "'"
					+ ",contact='" + contact.replace("'", "''") + "'"
					+ ",mobile='" + mobile.replace("'", "''") + "'"
					+ ",areaid=" + areaid + ""
					+ ",addr='" + addr.replace("'", "''") + "'"
				+ " where companyid=" + companyid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emCompany1.do?method=fill&s=1");
		
	}

    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth41 = checkAuthority(41, request)!=null?"1":"";
    	auth42 = checkAuthority(42, request)!=null?"1":"";
    	if(auth41.equals("") && auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

