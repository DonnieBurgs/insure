package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.web.util.Putil;

public class UserSecureDispatcher extends Dispatcher
{
	protected static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	public Map<String, Object> userSession = null ;
	public int companyID = 0;

	public UserSecureDispatcher()
    {
    }

    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {

    	userSession = LoginManager.checkUserLogin(request) ;
    	if(userSession==null) {
    		redirect(request,response,"/index.jsp") ;
    		return ;
      	}
    	companyID = Putil.getInt(userSession.get("companyid"));
    	
    	if(checkAuthority(request,response)==false) return ;
      	
        String method = request.getParameter("method");
        if("blank".equals(method)) {
            blank(request, response);
        } else if("add".equals(method)) {
            add(request, response);
        } else if("list".equals(method)) {
            list(request, response);
        } else if("view".equals(method)) {
            view(request, response);
        } else if("fill".equals(method)) {
            fill(request, response);
        } else if("update".equals(method)) {
            update(request, response);
        } else if("delete".equals(method)) {
            delete(request, response);
        } else {
          	def(request, response);
        }

    }
    
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	return false;
    }
    
    public Map<String,Object> checkAuthority(int aIdx, HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> user = LoginManager.checkUserAuthority(aIdx, request) ;
    	if(user==null) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return null ;
    	} else {
    		return user ;
    	}
    }
    
    public Map<String,Object> checkAuthority(int aIdx, HttpServletRequest request) {
    	Map<String, Object> user = LoginManager.checkUserAuthority(aIdx, request) ;
    	if(user==null) {
    		return null ;
    	} else {
    		return user ;
    	}
    }


}
