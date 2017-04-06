/*
 * Created on 2005-6-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.web.servlet.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
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
public class LoginManager {

	/**
	 * 
	 */
	public LoginManager() {
		super();
	}

	
	public static Map<String, Object> userLogin(HttpServletRequest request) {
		boolean loginSuccess = false ;
		Map<String, Object> user = new HashMap<String, Object>() ;
		
		try {
			String j_username = Putil.getString(request.getParameter("j_username"));
			String j_password = Putil.getString(request.getParameter("j_password"));
			String userid = "" ;
			String logMsg = "" ;
			
			if(j_username.length()>0 && j_password.length()>0) {
				//admin登录
				user = DbUtils.queryOne("select p.*,a.authorityno,a.authorityname from sc_admin p, sc_authority a"
						+ " where p.isvalid=1 and p.authorityid=a.authorityid and p.account='"+j_username + "'");
				if(user!=null) {
					if(Putil.getString(user.get("password")).equals(j_password)) {
						loginSuccess = true ;
						request.getSession().setAttribute("userSession", user) ;
						
						userid = Putil.getString(user.get("adminid")) ;
						logMsg = j_username + " 登录成功！" ;
					} else {
						userid = Putil.getString(user.get("adminid")) ;
						logMsg = j_username + " 登录失败！密码错误" ;
					}
					
				} else {
					//User登录
					user = DbUtils.queryOne("select p.*,a.authorityno,a.authorityname from sc_user p, sc_authority a"
							+ " where p.isvalid=1 and p.authorityid=a.authorityid and p.account='"+j_username + "'");
					if(user!=null) {
						if(Putil.getString(user.get("password")).equals(j_password)) {
							loginSuccess = true ;
							request.getSession().setAttribute("userSession", user) ;
							
							userid = Putil.getString(user.get("userid")) ;
							logMsg = j_username + " 登录成功！" ;
						} else {
							userid = Putil.getString(user.get("userid")) ;
							logMsg = j_username + " 登录失败！密码错误" ;
						}
						
					} else {
						userid = "0" ;
						logMsg = j_username + " 登录失败！账号或密码错误" ;
					}
					userid = "0" ;
					logMsg = j_username + " 登录失败！账号或密码错误" ;
				}
			} else {
				userid = "0" ;
				logMsg = j_username + " 登录失败！账号或密码错误" ;
			}
			
			//登录日志
			DbUtils.save("insert into sc_loginlog (adminid,createdate,note) values (" + userid + ",NOW(),'" + logMsg + "')") ;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		if(loginSuccess) {
			
			return user;
			
		} else {
			return null;

		}
	
	}
	
	public static HashMap<String, Object> loadActiveUserByOpenid(HttpServletRequest request, String openid) {
		HashMap<String, Object> user = loadActiveUserByOpenid(openid) ;
		request.getSession().setAttribute("userSession", user) ;
		return user ;
	}
	
	public static HashMap<String, Object> loadActiveUserByOpenid(String openid) {

		Connection connection = DbUtils.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		HashMap<String, Object> user = null ;
		
		try {
			statement = connection.createStatement();
			
			if(openid.length()>0) {
				resultSet = statement.executeQuery("select p.*,c.companyname,c.companyno,a.authorityno from sc_admin p, payCompany c, sc_authority a"
						+ " where p.islocked=0 and p.isactive=1 and p.companyid=c.companyid and p.authorityid=a.authorityid and p.wechat='"+openid + "'");
				if(resultSet.next()) {
					user = new HashMap<String, Object>() ;
					user.put("userid", Putil.getString(resultSet.getString("userid")));
					user.put("username", Putil.getString(resultSet.getString("username")));
					user.put("account", Putil.getString(resultSet.getString("account")));
					user.put("passwd", Putil.getString(resultSet.getString("passwd")));
					user.put("roleid", Putil.getString(resultSet.getString("roleid")));
					user.put("mobile", Putil.getString(resultSet.getString("mobile")));
					user.put("sex", Putil.getString(resultSet.getString("sex")));
					user.put("email", Putil.getString(resultSet.getString("email")));
					user.put("nickname", Putil.getString(resultSet.getString("nickname")));
					user.put("isactive", Putil.getString(resultSet.getString("isactive")));
					user.put("companyid", Putil.getString(resultSet.getString("companyid")));
					user.put("companyname", Putil.getString(resultSet.getString("companyname")));
					user.put("companyno", Putil.getString(resultSet.getString("companyno")));
					user.put("authorityno", Putil.getString(resultSet.getString("authorityno")));
					user.put("ctype", Putil.getString(resultSet.getString("ctype")));
					user.put("op", Putil.getString(resultSet.getString("wechat")));
					
				}
				if(resultSet != null) resultSet.close();
			}
			

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils.close(resultSet, statement, connection);
		}
		
		return user;
			
		
	
	}
	
	public static Map<String, Object> loadUser(HttpServletRequest request, String userid) {
		Map<String, Object> user = loadUser(userid) ;
		request.getSession().setAttribute("userSession", user) ;
		
		return user;
	
	}
	
	public static Map<String, Object> loadUser(String userid) {
		Map<String, Object> user = null ;
		
		if(userid.length()>0) {
			user = DbUtils.queryOne("select p.*,a.authorityno,a.authorityname from sc_user p, sc_authority a"
					+ " where p.isvalid=1 and p.authorityid=a.authorityid and p.userid="+userid + "");
			
		}
			
		return user;
	
	}
	
	public static Map<String, Object> loadAdmin(HttpServletRequest request, String adminid) {
		Map<String, Object> user = loadAdmin(adminid) ;
		request.getSession().setAttribute("userSession", user) ;
		
		return user;
	
	}
	
	public static Map<String, Object> loadAdmin(String adminid) {
		Map<String, Object> user = null ;
		
		if(adminid.length()>0) {
			user = DbUtils.queryOne("select p.*,a.authorityno,a.authorityname from sc_admin p, sc_authority a"
					+ " where p.isvalid=1 and p.authorityid=a.authorityid and p.adminid="+adminid + "");
			
		}
			
		return user;
	}

	  public static HashMap<String, Object> checkUserLogin(HttpServletRequest req) {
	      return getUser(req) ;
	  }
	  
	  public static HashMap<String, Object> getUser(HttpServletRequest req) {
		  HashMap<String, Object> user = (HashMap<String, Object>)req.getSession().getAttribute("userSession") ;
		  return user ;
	  }
	  
	  public static HashMap<String, Object> checkUserAuthority(int aIdx, HttpServletRequest req) {
		  HashMap<String, Object> user = getUser(req) ;
		  String authorityno = Putil.getString(user.get("authorityno")) ;
		  if(authorityno.length()>0 && aIdx>0 && aIdx<=99) {
			  if(Putil.getString(authorityno.substring(aIdx-1, aIdx)).equals("1")) {
				  return user ;
			  } else 
				  return null ;
		  } else {
			  return null ;
		  }
	  }
	  
	  public static boolean checkUserAuthority(int aIdx, String authorityno) {
		  if(authorityno.length()>0 && aIdx>0 && aIdx<=99) {
			  if(Putil.getString(authorityno.substring(aIdx-1, aIdx)).equals("1")) {
				  return true ;
			  } else 
				  return false ;
		  } else {
			  return false ;
		  }
	  }
	
	  public static void userLogout(HttpServletRequest req) {
		    req.getSession().removeAttribute(("userSession")) ;

	  }

	  public static void main(String[] args) {
	  }
}
