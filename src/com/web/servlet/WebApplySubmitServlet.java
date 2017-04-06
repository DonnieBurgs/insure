package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.FileUploadA;
import com.web.util.JsonUtils;
import com.web.util.Putil;


public class WebApplySubmitServlet extends BaseHttpServlet
{

	private static final long serialVersionUID = 1L;
	private static final String filepath = "/root/web/upload/apply/" ;

    public WebApplySubmitServlet()
    {
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
    	    request.setCharacterEncoding("UTF-8");
    		FileUploadA upload = new FileUploadA();
    		upload.parseRequest(request) ;
    	   	add(request, response, upload) ;
			
    	} catch(Exception e) {
    		e.printStackTrace(System.out) ;
    	}
	}

    public void add(HttpServletRequest request, HttpServletResponse response, FileUploadA upload)
        throws ServletException, IOException
    {
    	String filename = "" ;
    	String filename2 = "" ;
    	
    	try {
			String jsonStr ="" ;

			String name = Putil.getString(upload.getParameter("form-name"));
			String nickname = Putil.getString(upload.getParameter("form-nickname"));
			String province = Putil.getString(upload.getParameter("province"));
			String city = Putil.getString(upload.getParameter("city"));
			String tel = Putil.getString(upload.getParameter("form-tel"));
			String telCode = Putil.getString(upload.getParameter("form-telCode"));
			String password = Putil.getString(upload.getParameter("form-password"));
			String applyType = Putil.getString(upload.getParameter("applyType"));
			int companytypeid = 0;
			if(applyType.equals("in")) {
				companytypeid = 1;
			}
			
			if(!EmAppApiServlet.checkCode(tel, telCode)) {
				request.setAttribute("applyresult", "4");
				forward(request,response,"/web/applyResult.jsp") ;
				return;
				
			}
			
			Map<String, Object> company = DbUtils.queryOne("select * from em_company where mobile='" + tel + "'");
			if(company!=null && Putil.getInt(company.get("companyid"))>0) {
				request.setAttribute("applyresult", "3");
				forward(request,response,"/web/applyResult.jsp") ;
				return;
			}
			//保存图片
			filename = upload.saveFile("form-file", filepath, new Date().getTime()+"");
			if(filename.length()==0) {
				JsonUtils.write(null, 0, 4, "上传失败！.", response);
				return;
			} else if(filename.indexOf("-1")==0) {
				JsonUtils.write(null, 0, 4, "上传失败,文件太大！", response);
				return;
			} else if(filename.indexOf("-2")==0) {
				JsonUtils.write(null, 0, 4, "上传失败,文件太大！", response);
				return;
			} else {
				
			}
			filename2 = upload.saveFile("form-file2", filepath, (new Date().getTime()+1) +"");
			if(filename.length()==0) {
				JsonUtils.write(null, 0, 4, "上传失败！.", response);
				return;
			} else if(filename.indexOf("-1")==0) {
				JsonUtils.write(null, 0, 4, "上传失败,文件太大！", response);
				return;
			} else if(filename.indexOf("-2")==0) {
				JsonUtils.write(null, 0, 4, "上传失败,文件太大！", response);
				return;
			} else {
				
			}
			
			StringBuilder select = new StringBuilder("insert into em_company (companyname,contact,mobile,areaid,addr,isvalid,companytypeid,servicetypeid,startdate,expdate,createdate,balance,note,isaudit,auditnote,photo1,photo2) values ("
					+ "'" + nickname.replace("'", "''") + "'"
					+ ",'" + name.replace("'", "''") + "'"
					+ ",'" + tel.replace("'", "''") + "'"
					+ ",0"
					+ ",'" + (province+city).replace("'", "''") + "'"
					+ ",0"
					+ "," + companytypeid + ""
					+ ",0"
					+ ",SYSDATE()"
					+ ",SYSDATE()"
					+ ",SYSDATE()"
					+ ",0"
					+ ",''"
					+ ",0"
					+ ",''"
					+ ",'" + filename.replace("'", "''") + "'"
					+ ",'" + filename2.replace("'", "''") + "'"
					+ ")"
				);

			int result = DbUtils.save(select.toString());
			if(result==1) {
				request.setAttribute("applyresult", "1");
				EmAppApiServlet.updateCode(tel);
			} else {
				request.setAttribute("applyresult", "2");
				
			}

			forward(request,response,"/web/applyResult.jsp") ;

    	} catch(Exception e) {
    		e.printStackTrace(System.out) ;
		} finally {
    	}

        
    }
    
	
	public static Map<String, Object> getCompany(String mobile) {
		Map<String, Object> user = DbUtils.queryOne("select * from em_user where mobile='" + mobile + "'");
		return user;
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


}
