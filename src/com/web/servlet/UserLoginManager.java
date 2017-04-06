/*
 * Created on 2005-6-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.web.servlet;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.Putil;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserLoginManager {

	/**
	 * 
	 */
	public UserLoginManager() {
		super();
	}

	/*
	public static Map<String, Object> userLogin(HttpServletRequest request) {

		String mobile = Putil.getString(request.getParameter("check_phone"));
		String pass = Putil.getString(request.getParameter("check_password"));
		Map<String, Object> user = loadUserByMobile(mobile) ;
		
		if(user==null || !Putil.getString(user.get("password")).equals(pass)) {
			return null;
		}
		request.getSession().setAttribute("userSession", user) ;
		//登录日志
		LogUtils.addLog("2", "登陆成功。", Putil.getString(user.get("userid")));
		return user;
	
	}
*/
	public static Map<String, Object> loadActiveUserByOpenid(HttpServletRequest request, String openid) {
		Map<String, Object> user = loadUserByOpenid(openid) ;
		request.getSession().setAttribute("userSession", user) ;
		return user ;
	}


	public static Map<String, Object> loadUser(HttpServletRequest request, String userid) {
		Map<String, Object> user = loadUser(userid) ;
		request.getSession().setAttribute("userSession", user) ;
		
		return user;
	
	}
	
	public static Map<String, Object> loadUser(String userid) {

		Map<String, Object> user = null ;
		
		if(userid.length()>0) {
			user = DbUtils.queryOne("select * from doc_user p where p.isvalid=1 and p.userid="+userid);
		}
			
		return user;
			
	
	}
	
	public static Map<String, Object> loadUserByMobile(String mobile) {

		Map<String, Object> user = null ;
		
		if(mobile.length()>0) {
			user = DbUtils.queryOne("select * from doc_user p where p.isvalid=1 and p.mobile='" + mobile + "'");
		}
			
		return user;
			
	
	}
	
	public static Map<String, Object> loadUserByOpenid(String openid) {

		Map<String, Object> user = null ;
		
		if(openid.length()>0) {
			user = DbUtils.queryOne("select * from doc_user p where p.isvalid=1 and p.openid='" + openid + "'");
		}
			
		return user;
			
	
	}
	
	public static Map<String, Object> loadDoctorByMobile(String mobile) {

		Map<String, Object> doctor = null ;
		
		if(mobile.length()>0) {
			doctor = DbUtils.queryOne("select * from doc_user p where p.isvalid=1 and p.mobile='" + mobile + "'");
		}
			
		return doctor;
			
	
	}
	
	public static Map<String, Object> loadDoctorByOpenid(String openid) {

		Map<String, Object> doctor = null ;
		
		if(openid.length()>0) {
			doctor = DbUtils.queryOne("select * from doc_doctor p where p.openid='" + openid + "'");
		}
			
		return doctor;
			
	
	}

	  public static Map<String, Object> checkUserLogin(HttpServletRequest req, HttpServletResponse res) {
	      return getUser(req, res) ;
	  }
	  
	  public static Map<String, Object> getUser(HttpServletRequest req, HttpServletResponse res) {
		  if(req.getSession().getAttribute("userSession")!=null) {
			  Map<String, Object> user = (Map<String, Object>)req.getSession().getAttribute("userSession") ;
			  if(user!=null) {
				  String mobile = Putil.getString(user.get("mobile")) ;
				  if(mobile.length()>0) saveCookie(res, mobile);
			  }
			  
			  return user ;
		  } else {
			  return userLoginFromCookie(req, res);
		  }
	  }
	
	  public static void userLogout(HttpServletRequest req) {
		    req.getSession().removeAttribute(("userSession")) ;

	  }
		
	public static Map<String, Object> userLoginFromCookie(HttpServletRequest req, HttpServletResponse res) {
		    Cookie[] cookies = req.getCookies();
		    String mobile = "";
		    String passwd = "";
		    String name = "";
		    String value = "";
		    Map<String, Object> user = null;
	    	Putil.printLog("userLoginFromCookie.start");
		    for (int i = 0; cookies!=null && i < cookies.length; i++) {
		      Cookie cookie = cookies[i];
		      name = cookie.getName();
		      value = cookie.getValue();
		      if ("An_mobile".equals(name)){
		    	  mobile = Putil.getString(value);
		    	  if(mobile.length()>0) {
		    		  user = loadUserByMobile(mobile);
		    		  if(user!=null) {
		    			  saveCookie(res, mobile);
				    	  Putil.printLog("userLoginFromCookie:" + mobile + " login success.");
		    		  }
		    	  }
		    	  break;
		      }
		    }
			
		    return user ;
	}
	
	public static void saveCookie(HttpServletResponse res, String mobile) {
	    //cookie处理
	    Cookie cookie  = null;
	    cookie = new Cookie("An_mobile",mobile);
	    cookie.setMaxAge(240*60*60);  //10天
	    res.addCookie(cookie);

	}
	
	public static void saveLog(int type, int linkid, String note) {
		DbUtils.save("insert into doc_syslog (type,linkid,createdate,note) values (" + type + "," + linkid + ",SYSDATE(),'" + note + "')") ;
	}


	  public static void main(String[] args) {
	  }
}
