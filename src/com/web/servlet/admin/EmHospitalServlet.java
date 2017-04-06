package com.web.servlet.admin;
/*
	<servlet>
		<servlet-name>emHospital</servlet-name>
		<servlet-class>com.web.servlet.admin.EmHospitalServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emHospital</servlet-name>
		<url-pattern>/emHospital.do</url-pattern>
	</servlet-mapping>

 */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.util.*;

public class EmHospitalServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1489736574632L;
	private String auth41 = "";
	private String auth42 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_areaid1 = Putil.getString(request.getParameter("key_areaid1")) ;
		String key_areaid = Putil.getString(request.getParameter("key_areaid")) ;
		request.setAttribute("key_areaid", key_areaid);
		request.setAttribute("key_areaid1", key_areaid1);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		//地区列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from em_area p where parentid=0 order by seq desc");
		request.setAttribute("areaList1", resultRows);

		forward(request, response, "/admin/HospitalAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_areaid1 = Putil.getString(request.getParameter("key_areaid1")) ;
		String key_areaid = Putil.getString(request.getParameter("key_areaid")) ;
		request.setAttribute("key_areaid", key_areaid);
		request.setAttribute("key_areaid1", key_areaid1);
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
			String hospitalname = Putil.getString(request.getParameter("hospitalname"));
			String grade = Putil.getString(request.getParameter("grade"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			String url = Putil.getString(request.getParameter("url"));
			float lat = Putil.getFloat(request.getParameter("lat"));
			float lng = Putil.getFloat(request.getParameter("lng"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			String note = Putil.getString(request.getParameter("note"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;

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
			
			StringBuilder select = new StringBuilder("insert into em_hospital (hospitalname,grade,mobile,areaid,addr,url,lat,lng,isvalid,companyid,note,photo) values ("
				+ "'" + hospitalname.replace("'", "''") + "'"
				+ ",'" + grade.replace("'", "''") + "'"
				+ ",'" + mobile.replace("'", "''") + "'"
				+ "," + areaid + ""
				+ ",'" + addr.replace("'", "''") + "'"
				+ ",'" + url.replace("'", "''") + "'"
				+ "," + lat + ""
				+ "," + lng + ""
				+ "," + isvalid + ""
				+ "," + companyid + ""
				+ ",'" + note.replace("'", "''") + "'"
				+ ",'" + (fileSuc[0].length()>0?fileSuc[0]:"") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emHospital.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_areaid1="+key_areaid1+"&key_areaid="+key_areaid+"&m="+m+"&s="+s);
		
	}

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		//地区列表
		List<Map<String, Object>> resultRows = DbUtils.query("select * from em_area where parentid=0 order by seq desc");
		request.setAttribute("areaList1", resultRows);

		String key_areaid1 = Putil.getString(request.getParameter("key_areaid1")) ;
		String key_areaid = Putil.getString(request.getParameter("key_areaid")) ;
		request.setAttribute("key_areaid", key_areaid);
		request.setAttribute("key_areaid1", key_areaid1);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		String hospitalid = Putil.getString(request.getParameter("hospitalid")) ;
		if(hospitalid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_hospital p where p.hospitalid="+hospitalid);
			if(row!=null) {
				String areaid = Putil.getString(row.get("areaid"));
				Map<String, Object> area = DbUtils.queryOne("select a2.parentid,a2.areaid from em_area a2 where a2.areaid="+areaid);
				if(area!=null) {
					String areaid1 = Putil.getString(area.get("parentid"));
					//地区列表
					List<Map<String, Object>> resultRows2 = DbUtils.query("select * from em_area p where parentid="+areaid1 + " order by seq desc");
					request.setAttribute("areaList2", resultRows2);
					request.setAttribute("areaid1", areaid1);
					request.setAttribute("areaid", areaid);
					
				}
				
			}
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/HospitalEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_areaid1 = Putil.getString(request.getParameter("key_areaid1")) ;
		String key_areaid = Putil.getString(request.getParameter("key_areaid")) ;
		request.setAttribute("key_areaid", key_areaid);
		request.setAttribute("key_areaid1", key_areaid1);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String hospitalid = Putil.getString(request.getParameter("hospitalid")) ;
			String hospitalname = Putil.getString(request.getParameter("hospitalname"));
			String grade = Putil.getString(request.getParameter("grade"));
			String mobile = Putil.getString(request.getParameter("mobile"));
			int areaid = Putil.getInt(request.getParameter("areaid"));
			String addr = Putil.getString(request.getParameter("addr"));
			String url = Putil.getString(request.getParameter("url"));
			float lat = Putil.getFloat(request.getParameter("lat"));
			float lng = Putil.getFloat(request.getParameter("lng"));
			int isvalid = Putil.getInt(request.getParameter("isvalid"));
			int companyid = Putil.getInt(request.getParameter("companyid"));
			String note = Putil.getString(request.getParameter("note"));
			String photo0 = Putil.getString(request.getParameter("photo0")) ;

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
			
			StringBuilder select = new StringBuilder("update em_hospital set "
					+ "hospitalname='" + hospitalname.replace("'", "''") + "'"
					+ ",grade='" + grade.replace("'", "''") + "'"
					+ ",mobile='" + mobile.replace("'", "''") + "'"
					+ ",areaid=" + areaid + ""
					+ ",addr='" + addr.replace("'", "''") + "'"
					+ ",url='" + url.replace("'", "''") + "'"
					+ ",lat=" + lat + ""
					+ ",lng=" + lng + ""
					+ ",isvalid=" + isvalid + ""
					+ ",companyid=" + companyid + ""
					+ ",note='" + note.replace("'", "''") + "'"
					+ (fileSuc0?",photo = '" + filename0 + "'":"")
				+ " where hospitalid=" + hospitalid + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}
		redirect(request, response, "/emHospital.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_areaid1="+key_areaid1+"&key_areaid="+key_areaid+"&m="+m+"&s="+s);
		
	}

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		try {
			long totalCount = 0 ;
			String key_areaid1 = Putil.getString(request.getParameter("key_areaid1")) ;
			String key_areaid = Putil.getString(request.getParameter("key_areaid")) ;
			request.setAttribute("key_areaid", key_areaid);
			request.setAttribute("key_areaid1", key_areaid1);
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序

			//地区列表
			List<Map<String, Object>> areaList1 = DbUtils.query("select * from em_area p where parentid=0 order by seq desc");
			request.setAttribute("areaList1", areaList1);
			if(key_areaid1.length()>0) {
				//地区列表
				List<Map<String, Object>> resultRows2 = DbUtils.query("select * from em_area p where parentid="+key_areaid1 + " order by seq desc");
				request.setAttribute("areaList2", resultRows2);
			}
			
			String strArea = "";
			if(key_areaid.length()>0) {
				strArea = " and p.areaid=" + key_areaid;
			} else if(key_areaid.length()==0 && key_areaid1.length()>0) {
				strArea = " and (p.areaid=" + key_areaid1 + " or a.parentid=" + key_areaid1 +")";
			} else {
				strArea = "";
			}

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.hospitalid) as total from em_hospital p,em_area a where p.areaid=a.areaid"
					+ strArea
					+ (keyword.length()>0?" and p.hospitalname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			Putil.printLog("select p.*,a.areaname,a.fullname from em_hospital p,em_area a where p.areaid=a.areaid"
					+ strArea
					+ (keyword.length()>0?" and p.hospitalname like '%" + keyword + "%'":"")
					+ " order by p.hospitalid desc limit " + (s-1)*m + "," + m + "");
			// 列表分页语句
			resultRows = DbUtils.query("select p.*,a.areaname,a.fullname from em_hospital p,em_area a where p.areaid=a.areaid"
					+ strArea
					+ (keyword.length()>0?" and p.hospitalname like '%" + keyword + "%'":"")
					+ " order by p.hospitalid desc limit " + (s-1)*m + "," + m + ""
				);
			
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("key_areaid", key_areaid);
			request.setAttribute("key_areaid1", key_areaid1);
			request.setAttribute("keyword", keyword);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		forward(request, response, "/admin/HospitalList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String key_areaid1 = Putil.getString(request.getParameter("key_areaid1")) ;
		String key_areaid = Putil.getString(request.getParameter("key_areaid")) ;
		request.setAttribute("key_areaid", key_areaid);
		request.setAttribute("key_areaid1", key_areaid1);
		String keyword = Putil.getString(request.getParameter("keyword")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String hospitalid = Putil.getString(request.getParameter("hospitalid"));

			StringBuilder select = new StringBuilder("delete from em_hospital "
					+ " where hospitalid=" + hospitalid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emHospital.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_areaid1="+key_areaid1+"&key_areaid="+key_areaid+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth41 = checkAuthority(41, request)!=null?"1":"";
    	auth42 = checkAuthority(42, request)!=null?"1":"";
		request.setAttribute("auth41", auth41);
		request.setAttribute("auth42", auth42);
    	if(auth41.equals("") && auth42.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

