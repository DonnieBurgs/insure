package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emInsurerPolicy</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmInsurerPolicyServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emInsurerPolicy</servlet-name>
		<url-pattern>/emInsurerPolicy.do</url-pattern>
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

public class EmInsurerPolicyServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580694640L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth22", request, response)) {
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

		fillGroupInsurancePolicy(request);
		
		forward(request, response, "/admin/InsurerPolicyAdd.jsp");
	
	}

	private void fillGroupInsurancePolicy(HttpServletRequest request) {
		List<Map<String, Object>> resultRows = DbUtils.query("select p.* from em_groupinsurancepolicy p where p.id>=0"
				+ " order by p.groupinsurancepolicyname"
			);
		request.setAttribute("groupinsurancepolicyItems", resultRows);
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth22", request, response)) {
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
		
		
		//先保存被保险人
		int insuredID=0;
		try {
			String insuredname = Putil.getString(request.getParameter("insured.insuredname"));
			String gender = Putil.getString(request.getParameter("insured.gender"));
			String idnumber = Putil.getString(request.getParameter("insured.idnumber"));
			String passport = Putil.getString(request.getParameter("insured.passport"));
			String birthdate = Putil.getString(request.getParameter("insured.birthdate"));
			String employer = Putil.getString(request.getParameter("insured.employer"));
			String jobnumber = Putil.getString(request.getParameter("insured.jobnumber"));
			String bankname = Putil.getString(request.getParameter("insured.bankname"));
			String accountname = Putil.getString(request.getParameter("insured.accountname"));
			String accountnumber = Putil.getString(request.getParameter("insured.accountnumber"));
			String email = Putil.getString(request.getParameter("insured.email"));
			String department = Putil.getString(request.getParameter("insured.department"));
			
			StringBuilder select = new StringBuilder("insert into em_insured (insuredname,gender,idnumber,passport,birthdate,employer,jobnumber,bankname,accountname,accountnumber,email,department) values ("
				+ "'" + insuredname.replace("'", "''") + "'"
				+ ",'" + gender.replace("'", "''") + "'"
				+ ",'" + idnumber.replace("'", "''") + "'"
				+ ",'" + passport.replace("'", "''") + "'"
				+ ",'" + birthdate.replace("'", "''") + "'"
				+ ",'" + employer.replace("'", "''") + "'"
				+ ",'" + jobnumber.replace("'", "''") + "'"
				+ ",'" + bankname.replace("'", "''") + "'"
				+ ",'" + accountname.replace("'", "''") + "'"
				+ ",'" + accountnumber.replace("'", "''") + "'"
				+ ",'" + email.replace("'", "''") + "'"
				+ ",'" + department.replace("'", "''") + "'"
				+ ")"
			);

			insuredID= DbUtils.save1(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		if(insuredID == 0){
			prompt(response, "被保险人保存失败");
			return;
		}
		
		try {
			int groupinsurancepolicyid = Putil.getInt(request.getParameter("groupinsurancepolicyid"));
			int insurancegroupid = Putil.getInt(request.getParameter("insurancegroupid"));
			int insuranceid = Putil.getInt(request.getParameter("insuranceid"));
			int attachedtoid = Putil.getInt(request.getParameter("attachedtoid"));
			String periodbegin = Putil.getString(request.getParameter("periodbegin"));
			String periodend = Putil.getString(request.getParameter("periodend"));
			String policystate = Putil.getString(request.getParameter("policystate"));
			String shistate = Putil.getString(request.getParameter("shistate"));
			String shiarea = Putil.getString(request.getParameter("shiarea"));
			String joblocal = Putil.getString(request.getParameter("joblocal"));
			String relation = Putil.getString(request.getParameter("relation"));
			String jobnumber = Putil.getString(request.getParameter("jobnumber"));
			String bankname = Putil.getString(request.getParameter("bankname"));
			String accountname = Putil.getString(request.getParameter("accountname"));
			String accountnumber = Putil.getString(request.getParameter("accountnumber"));
			String clientid = Putil.getString(request.getParameter("clientid"));
			
			StringBuilder select = new StringBuilder("insert into em_insurerpolicy (groupinsurancepolicyid,insuredid,insurancegroupid, insuranceid, attachedtoid,periodbegin,periodend,policystate,shistate,shiarea,joblocal,relation,jobnumber,bankname,accountname,accountnumber,clientid) values ("
				+ "" + groupinsurancepolicyid + ""
				+ "," + insuredID + ""
						+ "," + insurancegroupid + ""
								+ "," + insuranceid + ""
				+ "," + attachedtoid + ""
				+ ",'" + periodbegin.replace("'", "''") + "'"
				+ ",'" + periodend.replace("'", "''") + "'"
				+ ",'" + policystate.replace("'", "''") + "'"
				+ ",'" + shistate.replace("'", "''") + "'"
				+ ",'" + shiarea.replace("'", "''") + "'"
				+ ",'" + joblocal.replace("'", "''") + "'"
				+ ",'" + relation.replace("'", "''") + "'"
				+ ",'" + jobnumber.replace("'", "''") + "'"
				+ ",'" + bankname.replace("'", "''") + "'"
				+ ",'" + accountname.replace("'", "''") + "'"
				+ ",'" + accountnumber.replace("'", "''") + "'"
				+ ",'" + clientid.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsurerPolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

		String id = Putil.getString(request.getParameter("id")) ;
		if(id.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_insurerpolicy p where p.id="+id);
			request.setAttribute("item", row);
		}
		fillGroupInsurancePolicy(request);

		forward(request, response, "/admin/InsurerPolicyEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth22", request, response)) {
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
			String id = Putil.getString(request.getParameter("insuredid")) ;
			String insuredname = Putil.getString(request.getParameter("insured.insuredname"));
			String gender = Putil.getString(request.getParameter("insured.gender"));
			String idnumber = Putil.getString(request.getParameter("insured.idnumber"));
			String passport = Putil.getString(request.getParameter("insured.passport"));
			String birthdate = Putil.getString(request.getParameter("insured.birthdate"));
			String employer = Putil.getString(request.getParameter("insured.employer"));
			String jobnumber = Putil.getString(request.getParameter("insured.jobnumber"));
			String bankname = Putil.getString(request.getParameter("insured.bankname"));
			String accountname = Putil.getString(request.getParameter("insured.accountname"));
			String accountnumber = Putil.getString(request.getParameter("insured.accountnumber"));
			String email = Putil.getString(request.getParameter("insured.email"));
			String department = Putil.getString(request.getParameter("insured.department"));


			
			StringBuilder select = new StringBuilder("update em_insured set "
					+ "insuredname='" + insuredname.replace("'", "''") + "'"
					+ ",gender='" + gender.replace("'", "''") + "'"
					+ ",idnumber='" + idnumber.replace("'", "''") + "'"
					+ ",passport='" + passport.replace("'", "''") + "'"
					+ ",birthdate='" + birthdate.replace("'", "''") + "'"
					+ ",employer='" + employer.replace("'", "''") + "'"
					+ ",jobnumber='" + jobnumber.replace("'", "''") + "'"
					+ ",bankname='" + bankname.replace("'", "''") + "'"
					+ ",accountname='" + accountname.replace("'", "''") + "'"
					+ ",accountnumber='" + accountnumber.replace("'", "''") + "'"
					+ ",email='" + email.replace("'", "''") + "'"
					+ ",department='" + department.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}
		
		try {
			String id = Putil.getString(request.getParameter("id")) ;
			int groupinsurancepolicyid = Putil.getInt(request.getParameter("groupinsurancepolicyid"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			int insurancegroupid = Putil.getInt(request.getParameter("insurancegroupid"));
			int insuranceid = Putil.getInt(request.getParameter("insuranceid"));
			int attachedtoid = Putil.getInt(request.getParameter("attachedtoid"));
			String periodbegin = Putil.getString(request.getParameter("periodbegin"));
			String periodend = Putil.getString(request.getParameter("periodend"));
			String policystate = Putil.getString(request.getParameter("policystate"));
			String shistate = Putil.getString(request.getParameter("shistate"));
			String shiarea = Putil.getString(request.getParameter("shiarea"));
			String joblocal = Putil.getString(request.getParameter("joblocal"));
			String relation = Putil.getString(request.getParameter("relation"));
			String jobnumber = Putil.getString(request.getParameter("jobnumber"));
			String bankname = Putil.getString(request.getParameter("bankname"));
			String accountname = Putil.getString(request.getParameter("accountname"));
			String accountnumber = Putil.getString(request.getParameter("accountnumber"));
			String clientid = Putil.getString(request.getParameter("clientid"));




			
			StringBuilder select = new StringBuilder("update em_insurerpolicy set "
					+ "groupinsurancepolicyid=" + groupinsurancepolicyid + ""
					+ ",insuredid=" + insuredid + ""
							+ ",insurancegroupid=" + insurancegroupid + ""
									+ ",insuranceid=" + insuranceid + ""
					+ ",attachedtoid=" + attachedtoid + ""
					+ ",periodbegin='" + periodbegin.replace("'", "''") + "'"
					+ ",periodend='" + periodend.replace("'", "''") + "'"
					+ ",policystate='" + policystate.replace("'", "''") + "'"
					+ ",shistate='" + shistate.replace("'", "''") + "'"
					+ ",shiarea='" + shiarea.replace("'", "''") + "'"
					+ ",joblocal='" + joblocal.replace("'", "''") + "'"
					+ ",relation='" + relation.replace("'", "''") + "'"
					+ ",jobnumber='" + jobnumber.replace("'", "''") + "'"
					+ ",bankname='" + bankname.replace("'", "''") + "'"
					+ ",accountname='" + accountname.replace("'", "''") + "'"
					+ ",accountnumber='" + accountnumber.replace("'", "''") + "'"
					+ ",clientid='" + clientid.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emInsurerPolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_insurerpolicy p where p.id>=0"
					+ (keyword.length()>0?" and p.insurerpolicyname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_insurerpolicy p where p.id>=0"
					+ (keyword.length()>0?" and p.insurerpolicyname like '%" + keyword + "%'":"")
					+ " order by p.id desc limit " + (s-1)*m + "," + m + ""
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

		forward(request, response, "/admin/InsurerPolicyList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth22", request, response)) {
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
			String id = Putil.getString(request.getParameter("id"));

			StringBuilder select = new StringBuilder("delete from em_insurerpolicy "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emInsurerPolicy.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth21 = checkAuthority(21, request)!=null?"1":"";
    	String auth22 = checkAuthority(22, request)!=null?"1":"";
		request.setAttribute("auth21", auth21);
		request.setAttribute("auth22", auth22);
    	if(auth21.equals("") && auth22.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

