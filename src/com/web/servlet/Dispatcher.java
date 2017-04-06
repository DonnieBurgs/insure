package com.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;

import com.web.util.Putil;


public class Dispatcher extends BaseHttpServlet
{

    protected static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    
    public int web_id = 0 ;

    public Dispatcher()
    {
    }

    public void add(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void blank(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void delete(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void def(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {

    	try{
        	String method = Putil.getString(request.getParameter("method"));
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
    		
    	} catch(Exception e) {
    		Putil.printLog("dispatcher error:" + request.getQueryString() + ":" + request.getRequestURI()) ;
    		e.printStackTrace(System.out) ;
    	}
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        dispatcher(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        dispatcher(request, response);
    }

    public void error(HttpServletRequest request, HttpServletResponse response, String errorText, String backURI, String errJspURI)
    {
        request.setAttribute("error", errorText);
        request.setAttribute("uri", backURI);
        forward(request, response, errJspURI);
    }

    public void fill(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void forward(HttpServletRequest request, HttpServletResponse response, String uri)
    {
        RequestDispatcher rd = getServletContext().getRequestDispatcher(uri);
        try{
            response.setCharacterEncoding("UTF-8") ;
            rd.forward(request, response);
        }
        catch (IOException ex) {
          return;
        }
        catch (ServletException ex) {
          return;
        }
    }

    public void init()
        throws ServletException
    {
    }

    public void list(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void prompt(HttpServletRequest request, HttpServletResponse response, String text)
    {
//    	request.setAttribute("msgtype", "1");
//    	request.setAttribute("msg", text);
//		forward(request, response, Parameter.p_web_dir[web_id] + "/showmsg.jsp");

    	
      try{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Note</title>");
        out.print("<SCRIPT LANGUAGE=\"JavaScript\">");
        out.print("alert('" + text + "');");
        out.print("history.back();");
        out.print("</SCRIPT></head><BODY>" + "页面正在跳转，请稍候......" + "</BODY></html>");
      }
        catch (IOException ex) {
          return ;
        }
        return;
        
    }

    public void redirect(HttpServletRequest request, HttpServletResponse response, String uri)
    {
      try{
          response.setCharacterEncoding("UTF-8") ;
          response.sendRedirect(uri);
      } catch (IOException ex) {
          return ;
      }
      return;
    }
    
    public void showMsg(HttpServletRequest request, HttpServletResponse response, 
    		String msg, String forwardurl) {
//    	request.setAttribute("msgtype", "2");
//    	request.setAttribute("msg", msg);
//    	request.setAttribute("forwardurl", forwardurl);
//		forward(request, response, Parameter.p_web_dir[web_id] + "/showmsg.jsp");
    	
        try{
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Note</title>");
            out.print("<SCRIPT LANGUAGE=\"JavaScript\">");
            out.print("alert('" + msg + "');");
            out.print("location.href='" + forwardurl + "';");
            out.print("</SCRIPT></head><BODY>" + "页面正在跳转，请稍候......" + "</BODY></html>");
          }
            catch (IOException ex) {
              return ;
            }
            return;
    	
    }

    public void update(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }

    public void view(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
    }
    
	public static String download_pd(String urlstr) {
//		System.out.println(urlstr + "," + path + "," + filename) ;
		boolean r = false ;
		URL    url = null;
		try{
		   url = new URL(urlstr);
		}catch(Exception e){
		   System.out.println(e.toString());
		   return "" ;
		}
		
	    StringBuffer sb = new StringBuffer() ;
		try{
	        InputStream ins = url.openStream();
//		      FileInputStream fin = new FileInputStream(url) ;
		      byte b[] = new byte[300000] ;
		      int rleng = ins.read(b) ;
		      while(rleng!=-1) {
		    	  r = true ;
		    	  sb.append(new String(b,0,rleng,"utf-8")) ;
//		      	sb.append(new String(b,0,rleng,"gbk")) ; //"utf-8")) ;
		      	  rleng = ins.read(b) ;
		      }
		} catch(Exception e){
			   System.out.println(e.toString());	
	           return "" ;
		}
	      
	    return sb.toString() ;

	}

}
