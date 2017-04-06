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

public class EmInformationServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488465993275L;
	private String auth59 = "";
	private String auth60 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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

		forward(request, response, "/admin/InformationAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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

		Map<String, Object> admin = LoginManager.getUser(request);
		if(admin==null) {
			return ;
		}
		try {
			String title = Putil.getString(request.getParameter("title"));
			String content = Putil.getString(request.getParameter("content"));
			String url = Putil.getString(request.getParameter("url")) ;
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			String createdate = Putil.getString(request.getParameter("createdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			int isshow = Putil.getInt(request.getParameter("isshow"));
			int userid = Putil.getInt(request.getParameter("userid"));

			Date date = new Date();

			String filename0 = "p0" + date.getTime()+"";

			boolean fileSuc0 = false ;

			String fileSuc[] = {""};

			int fileCount = 0 ;

			if(photo0.length()>10) {
				int iS = 0, iE = 0 ;
				iS = photo0.indexOf("image/") ;
				iE = photo0.indexOf(";", iS) ;
				String fileEx = "" ;
				if(iS>0 && iE>iS) {
					fileEx = photo0.substring(iS+6, iE) ;
					if(fileEx.equals("jpeg")) fileEx = "jpg" ;
					filename0 += "." + fileEx ;
					iS = photo0.indexOf("base64,") ;
					fileSuc0 = PhotoUpload.savePhoto(request, photo0.substring(iS + 7), filename0);
					if(fileSuc0) {
						fileSuc[fileCount] = filename0;
						fileCount++;
					}
				}
			}
			
			StringBuilder select = new StringBuilder("insert into em_information (title,content,url,photo,createdate,expdate,isshow,isdelete,userid) values ("
				+ "'" + title.replace("'", "''") + "'"
				+ ",'" + content.replace("'", "''") + "'"
				+ ",'" + url.replace("'", "''") + "'"
				+ ",'" + (fileSuc[0].length()>0?fileSuc[0]:"") + "'"
				+ ",'" + createdate.replace("'", "''") + "'"
				+ ",'" + expdate.replace("'", "''") + "'"
				+ "," + isshow + ""
				+ ",0"
				+ "," + userid + ""
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInformation.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String informationid = Putil.getString(request.getParameter("informationid")) ;
		if(informationid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_information p where p.informationid="+informationid);
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/InformationEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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
			String informationid = Putil.getString(request.getParameter("informationid")) ;
			String title = Putil.getString(request.getParameter("title"));
			String content = Putil.getString(request.getParameter("content"));
			String url = Putil.getString(request.getParameter("url")) ;
			String photo0 = Putil.getString(request.getParameter("photo0")) ;
			String createdate = Putil.getString(request.getParameter("createdate"));
			String expdate = Putil.getString(request.getParameter("expdate"));
			int isshow = Putil.getInt(request.getParameter("isshow"));
			int userid = Putil.getInt(request.getParameter("userid"));

			Date date = new Date();

			String filename0 = "p0" + date.getTime()+"";

			boolean fileSuc0 = false ;

			if(photo0.length()>10) {
				int iS = 0, iE = 0 ;
				iS = photo0.indexOf("image/") ;
				iE = photo0.indexOf(";", iS) ;
				String fileEx = "" ;
				if(iS>0 && iE>iS) {
					fileEx = photo0.substring(iS+6, iE) ;
					if(fileEx.equals("jpeg")) fileEx = "jpg" ;
					filename0 += "." + fileEx ;
					iS = photo0.indexOf("base64,") ;
					fileSuc0 = PhotoUpload.savePhoto(request, photo0.substring(iS + 7), filename0);
				}
			}
			
			StringBuilder select = new StringBuilder("update em_information set "
					+ "title='" + title.replace("'", "''") + "'"
					+ ",content='" + content.replace("'", "''") + "'"
					+ ",url='" + url.replace("'", "''") + "'"
					+ (fileSuc0?",photo = '" + filename0 + "'":"")
					+ ",createdate='" + createdate.replace("'", "''") + "'"
					+ ",expdate='" + expdate.replace("'", "''") + "'"
					+ ",isshow=" + isshow + ""
					+ ",userid=" + userid + ""
				+ " where informationid=" + informationid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emInformation.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.informationid) as total from em_information p where p.informationid>=0"
					+ (keyword.length()>0?" and p.informationname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_information p where p.informationid>=0"
					+ (keyword.length()>0?" and p.informationname like '%" + keyword + "%'":"")
					+ " order by p.informationid desc limit " + (s-1)*m + "," + m + ""
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

		forward(request, response, "/admin/InformationList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth60.equals("")) {
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
			String informationid = Putil.getString(request.getParameter("informationid"));

			StringBuilder select = new StringBuilder("delete from em_information "
					+ " where informationid=" + informationid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInformation.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth59 = checkAuthority(59, request)!=null?"1":"";
    	auth60 = checkAuthority(60, request)!=null?"1":"";
		request.setAttribute("auth59", auth59);
		request.setAttribute("auth60", auth60);
    	if(auth59.equals("") && auth60.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

