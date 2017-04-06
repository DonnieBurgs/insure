package com.web.servlet;

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
import com.web.util.JsonUtils;
import com.web.util.Putil;
import com.web.util.StringUtils;

public class WebApplyServlet extends Dispatcher {

	private static final long serialVersionUID = 8459048427979079912L;

	public void dispatcher(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
			
        String method = Putil.getString(request.getParameter("method"));
        if(method.equals("")) {
        	index(request, response);
		} else if(method.equals("applystatus")) {
        	applystatus(request, response);
        } else if(method.equals("asSubmit")) {
            asSubmit(request, response);
		} else if(method.equals("applyResult")) {
			applyResult(request, response);
        }
	}
		
	private void index(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, Object> user = LoginManager.getUser(request);
			if(user!=null && Putil.getInt(user.get("userid"))>0) {
				request.setAttribute("user", user);
				request.setAttribute("islogin", "1");
	      	} else {
	    		request.setAttribute("islogin", "0");
			}
			forward(request,response,"/web/apply.jsp") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void applystatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, Object> user = LoginManager.getUser(request);
			if(user!=null && Putil.getInt(user.get("userid"))>0) {
				request.setAttribute("user", user);
				request.setAttribute("islogin", "1");
	      	} else {
	    		request.setAttribute("islogin", "0");
			}
			forward(request,response,"/web/applystatus.jsp") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void asSubmit(HttpServletRequest request, HttpServletResponse response) {
		try {
	        String mobile = Putil.getString(request.getParameter("mobile"));
			Map<String, Object> com = DbUtils.queryOne("select * from em_company where mobile='" + mobile + "'");
			if(com!=null) {
				int isaudit = Putil.getInt(com.get("isaudit"));
				if(isaudit==1)
					JsonUtils.write(null, 1, 0, "已通过审核。", response);
				else if(isaudit==0) 
					JsonUtils.write(null, 0, 2, "正在审核中，请耐心等候。审核通过后会有短信通知。", response);
				else if(isaudit==2) 
					JsonUtils.write(null, 0, 3, "未通过审核：" + Putil.getString(com.get("auditnote")), response);
				
			} else {
				JsonUtils.write(null, 0, 1, "未提交过资料。", response);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
private void applyResult(HttpServletRequest request, HttpServletResponse response) {
	try {
		HashMap<String, Object> user = LoginManager.getUser(request);
		if(user!=null && Putil.getInt(user.get("userid"))>0) {
			request.setAttribute("user", user);
			request.setAttribute("islogin", "1");
      	} else {
    		request.setAttribute("islogin", "0");
		}
		forward(request,response,"/web/applyResult.jsp") ;
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	
}
