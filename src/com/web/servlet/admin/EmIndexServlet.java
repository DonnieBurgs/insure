package com.web.servlet.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.servlet.Dispatcher;
import com.web.util.Putil;
import com.web.util.StringUtils;

public class EmIndexServlet extends Dispatcher {

	private static final long serialVersionUID = 8459048427979079912L;

	public void dispatcher(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
			
		String method = request.getParameter("method");
		if (StringUtils.hasLength(method)) {
			if (method.equals("index")) {
				index(request, response);
			} else if (method.equals("logout")) {
				logout(request, response);
			}
		}
	}
		
	private void index(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> user = LoginManager.checkUserLogin(request) ;
			if(user==null) {
				redirect(request, response, "/index.do");
				return;
			} else if(Putil.getInt(user.get("usertype"))<=1) {
				redirect(request, response, "/index.do");
				return;
			} else if(Putil.getInt(user.get("companyid"))==0) {
				request.setAttribute("username", Putil.getString(user.get("adminname"))) ;
				request.setAttribute("authorityno", Putil.getString(user.get("authorityno"))) ;
				request.setAttribute("isSys", "1") ;
      		} else {
      			Map<String, Object> com = DbUtils.queryOne("select p.* from em_company p where p.companyid="+Putil.getInt(user.get("companyid")));
    			String comname = "";
      			if(com!=null) {
      				comname = Putil.getString(com.get("companyname"));
    			}
				request.setAttribute("username", Putil.getString(user.get("username")) + "(" + comname + ")") ;
				request.setAttribute("authorityno", Putil.getString(user.get("authorityno"))) ;
				request.setAttribute("isSys", "0") ;
      		}
      		request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoginManager.userLogout(request) ;
		forward(request, response, "/index.jsp");
	
	}

	
}
