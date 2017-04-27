package com.insure.servlet.admin;
/*
	<servlet>
		<servlet-name>emClaim</servlet-name>
		<servlet-class>com.insure.servlet.admin.EmClaimServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>emClaim</servlet-name>
		<url-pattern>/emClaim.do</url-pattern>
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

public class EmClaimServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1491580695501L;
	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(checkAuthority("auth44", request, response)) {
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
		
		request.setAttribute("claimtypeMap", EmClaimInfoServlet.claimtypeMap);
		request.setAttribute("claimreasonMap", EmClaimInfoServlet.claimreasonMap);
		request.setAttribute("paytypeMap", EmClaimInfoServlet.paytypeMap);
		request.setAttribute("insuretypeMap", EmClaimInfoServlet.insuretypeMap);

		forward(request, response, "/admin/ClaimAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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
			String insuredid = Putil.getString(request.getParameter("insured.id"));
			String insuredname = Putil.getString(request.getParameter("insured.insuredname"));
			String gender = Putil.getString(request.getParameter("insured.gender"));
			String idtype = Putil.getString(request.getParameter("insured.idtype"));
			String idnumber = Putil.getString(request.getParameter("insured.idnumber"));
			String passport = Putil.getString(request.getParameter("insured.passport"));
			String birthdate = Putil.getString(request.getParameter("insured.birthdate"));
			String insuredtype = Putil.getString(request.getParameter("insured.insuredtype"));
			String age = Putil.getString(request.getParameter("insured.age"));
			String workage = Putil.getString(request.getParameter("insured.workage"));
			String employer = Putil.getString(request.getParameter("insured.employer"));
			String jobnumber = Putil.getString(request.getParameter("insured.jobnumber"));
			String bankname = Putil.getString(request.getParameter("insured.bankname"));
			String bankprovince = Putil.getString(request.getParameter("insured.bankprovince"));
			String bankcity = Putil.getString(request.getParameter("insured.bankcity"));
			String subbranch = Putil.getString(request.getParameter("insured.subbranch"));
			String accountname = Putil.getString(request.getParameter("insured.accountname"));
			String accountnumber = Putil.getString(request.getParameter("insured.accountnumber"));
			String mobile = Putil.getString(request.getParameter("insured.mobile"));
			String email = Putil.getString(request.getParameter("insured.email"));
			String department = Putil.getString(request.getParameter("insured.department"));
			String title = Putil.getString(request.getParameter("insured.title"));
			
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_insured p where p.insuredname='"+insuredname + "' and p.idnumber = '" + idnumber +"'");
			if(StringUtils.hasLength(insuredid) || (row != null && !row.isEmpty())){ // 更新
				insuredID = Integer.parseInt(insuredid);
				StringBuilder select = new StringBuilder("update em_insured set "
						+ "insuredname='" + insuredname.replace("'", "''") + "'"
						+ ",gender='" + gender.replace("'", "''") + "'"
						+ ",idtype='" + idtype.replace("'", "''") + "'"
						+ ",idnumber='" + idnumber.replace("'", "''") + "'"
						+ ",passport='" + passport.replace("'", "''") + "'"
						+ ",birthdate='" + birthdate.replace("'", "''") + "'"
						+ ",insuredtype='" + insuredtype.replace("'", "''") + "'"
						+ ",age='" + age.replace("'", "''") + "'"
						+ ",workage='" + workage.replace("'", "''") + "'"
						+ ",employer='" + employer.replace("'", "''") + "'"
						+ ",jobnumber='" + jobnumber.replace("'", "''") + "'"
						+ ",bankname='" + bankname.replace("'", "''") + "'"
						+ ",bankprovince='" + bankprovince.replace("'", "''") + "'"
						+ ",bankcity='" + bankcity.replace("'", "''") + "'"
						+ ",subbranch='" + subbranch.replace("'", "''") + "'"
						+ ",accountname='" + accountname.replace("'", "''") + "'"
						+ ",accountnumber='" + accountnumber.replace("'", "''") + "'"
						+ ",mobile='" + mobile.replace("'", "''") + "'"
						+ ",email='" + email.replace("'", "''") + "'"
						+ ",department='" + department.replace("'", "''") + "'"
						+ ",title='" + title.replace("'", "''") + "'"
					+ " where id=" + insuredid + "" 
				);
				int result = DbUtils.save(select.toString());
			}else{
				StringBuilder select = new StringBuilder("insert into em_insured (insuredname,gender,idtype,idnumber,passport,birthdate,insuredtype,age,workage,employer,jobnumber,bankname,bankprovince,bankcity,subbranch,accountname,accountnumber,mobile,email,department,title) values ("
					+ "'" + insuredname.replace("'", "''") + "'"
					+ ",'" + gender.replace("'", "''") + "'"
					+ ",'" + idtype.replace("'", "''") + "'"
					+ ",'" + idnumber.replace("'", "''") + "'"
					+ ",'" + passport.replace("'", "''") + "'"
					+ ",'" + birthdate.replace("'", "''") + "'"
					+ ",'" + insuredtype.replace("'", "''") + "'"
					+ ",'" + age.replace("'", "''") + "'"
					+ ",'" + workage.replace("'", "''") + "'"
					+ ",'" + employer.replace("'", "''") + "'"
					+ ",'" + jobnumber.replace("'", "''") + "'"
					+ ",'" + bankname.replace("'", "''") + "'"
					+ ",'" + bankprovince.replace("'", "''") + "'"
					+ ",'" + bankcity.replace("'", "''") + "'"
					+ ",'" + subbranch.replace("'", "''") + "'"
					+ ",'" + accountname.replace("'", "''") + "'"
					+ ",'" + accountnumber.replace("'", "''") + "'"
					+ ",'" + mobile.replace("'", "''") + "'"
					+ ",'" + email.replace("'", "''") + "'"
					+ ",'" + department.replace("'", "''") + "'"
					+ ",'" + title.replace("'", "''") + "'"
					+ ")"
				);

			insuredID= DbUtils.save1(select.toString());
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		if(insuredID == 0){
			prompt(response, "被保险人保存失败");
			return;
		}
		

		// 保存后的ID
		int claimID=0;
		int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
		try {
			
			String serialnumber = Putil.getString(request.getParameter("serialnumber"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			String bardh = Putil.getString(request.getParameter("bardh"));




			
			StringBuilder select = new StringBuilder("insert into em_claim (claimarchiveid,serialnumber,insuredid,bardh) values ("
				+ "" + claimarchiveid + ""
				+ ",'" + serialnumber.replace("'", "''") + "'"
				+ "," + insuredid + ""
				+ ",'" + bardh.replace("'", "''") + "'"
				+ ")"
			);

			claimID = DbUtils.save1(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
		try {
			int claimtype = Putil.getInt(request.getParameter("info.claimtype"));
			int claimreason = Putil.getInt(request.getParameter("info.claimreason"));
			String claimdate = Putil.getString(request.getParameter("info.claimdate"));
			int paytype = Putil.getInt(request.getParameter("info.paytype"));
			String insuretype1 = Putil.getString(request.getParameter("info.insuretype1"));
			String insuretype2 = Putil.getString(request.getParameter("info.insuretype2"));
			String insuretype3 = Putil.getString(request.getParameter("info.insuretype3"));
			float spamount = Putil.getFloat(request.getParameter("info.spamount"));
			int diseaseid = Putil.getInt(request.getParameter("info.diseaseid"));
			int determinecount = Putil.getInt(request.getParameter("info.determinecount"));
			int receiptcount = Putil.getInt(request.getParameter("info.receiptcount"));
			String mark = Putil.getString(request.getParameter("info.mark"));

			
			StringBuilder select = new StringBuilder("insert into em_claiminfo (claimid,claimtype,claimreason,claimdate,paytype,insuretype1,insuretype2,insuretype3,spamount,diseaseid,determinecount,receiptcount,mark) values ("
				+ "" + claimID + ""
				+ "," + claimtype + ""
				+ "," + claimreason + ""
				+ ",'" + claimdate.replace("'", "''") + "'"
				+ "," + paytype + ""
				+ ",'" + insuretype1.replace("'", "''") + "'"
				+ ",'" + insuretype2.replace("'", "''") + "'"
				+ ",'" + insuretype3.replace("'", "''") + "'"
				+ "," + spamount + ""
				+ "," + diseaseid + ""
				+ "," + determinecount + ""
				+ "," + receiptcount + ""
				+ ",'" + mark.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaim2.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s+"&keyword="+claimarchiveid);
		
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
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_claim p where p.id="+id);
			request.setAttribute("item", row);
			
			Map<String, Object> rowClaiminfo = DbUtils.queryOne("select p.* from em_claiminfo p where p.claimid="+id);
			request.setAttribute("emClaimInfo", rowClaiminfo);
			
			Map<String, Object> rowInsured = DbUtils.queryOne("select p.* from em_insured p where p.id="+String.valueOf(row.get("insuredid")));
			request.setAttribute("insured", rowInsured);
		}
		
		request.setAttribute("claimtypeMap", EmClaimInfoServlet.claimtypeMap);
		request.setAttribute("claimreasonMap", EmClaimInfoServlet.claimreasonMap);
		request.setAttribute("paytypeMap", EmClaimInfoServlet.paytypeMap);
		request.setAttribute("insuretypeMap", EmClaimInfoServlet.insuretypeMap);

		forward(request, response, "/admin/ClaimEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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

		String id = Putil.getString(request.getParameter("id")) ;
		try {
			int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
			String serialnumber = Putil.getString(request.getParameter("serialnumber"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			String bardh = Putil.getString(request.getParameter("bardh"));




			
			StringBuilder select = new StringBuilder("update em_claim set "
					+ "claimarchiveid=" + claimarchiveid + ""
					+ ",serialnumber='" + serialnumber.replace("'", "''") + "'"
					+ ",insuredid=" + insuredid + ""
					+ ",bardh='" + bardh.replace("'", "''") + "'"
				+ " where id=" + id + "" 
			);
			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}
		
				try {
					String insuredid = Putil.getString(request.getParameter("insured.id"));
					String insuredname = Putil.getString(request.getParameter("insured.insuredname"));
					String gender = Putil.getString(request.getParameter("insured.gender"));
					String idtype = Putil.getString(request.getParameter("insured.idtype"));
					String idnumber = Putil.getString(request.getParameter("insured.idnumber"));
					String passport = Putil.getString(request.getParameter("insured.passport"));
					String birthdate = Putil.getString(request.getParameter("insured.birthdate"));
					String insuredtype = Putil.getString(request.getParameter("insured.insuredtype"));
					String age = Putil.getString(request.getParameter("insured.age"));
					String workage = Putil.getString(request.getParameter("insured.workage"));
					String employer = Putil.getString(request.getParameter("insured.employer"));
					String jobnumber = Putil.getString(request.getParameter("insured.jobnumber"));
					String bankname = Putil.getString(request.getParameter("insured.bankname"));
					String bankprovince = Putil.getString(request.getParameter("insured.bankprovince"));
					String bankcity = Putil.getString(request.getParameter("insured.bankcity"));
					String subbranch = Putil.getString(request.getParameter("insured.subbranch"));
					String accountname = Putil.getString(request.getParameter("insured.accountname"));
					String accountnumber = Putil.getString(request.getParameter("insured.accountnumber"));
					String mobile = Putil.getString(request.getParameter("insured.mobile"));
					String email = Putil.getString(request.getParameter("insured.email"));
					String department = Putil.getString(request.getParameter("insured.department"));
					String title = Putil.getString(request.getParameter("insured.title"));
					
						StringBuilder select = new StringBuilder("update em_insured set "
								+ "insuredname='" + insuredname.replace("'", "''") + "'"
								+ ",gender='" + gender.replace("'", "''") + "'"
								+ ",idtype='" + idtype.replace("'", "''") + "'"
								+ ",idnumber='" + idnumber.replace("'", "''") + "'"
								+ ",passport='" + passport.replace("'", "''") + "'"
								+ ",birthdate='" + birthdate.replace("'", "''") + "'"
								+ ",insuredtype='" + insuredtype.replace("'", "''") + "'"
								+ ",age='" + age.replace("'", "''") + "'"
								+ ",workage='" + workage.replace("'", "''") + "'"
								+ ",employer='" + employer.replace("'", "''") + "'"
								+ ",jobnumber='" + jobnumber.replace("'", "''") + "'"
								+ ",bankname='" + bankname.replace("'", "''") + "'"
								+ ",bankprovince='" + bankprovince.replace("'", "''") + "'"
								+ ",bankcity='" + bankcity.replace("'", "''") + "'"
								+ ",subbranch='" + subbranch.replace("'", "''") + "'"
								+ ",accountname='" + accountname.replace("'", "''") + "'"
								+ ",accountnumber='" + accountnumber.replace("'", "''") + "'"
								+ ",mobile='" + mobile.replace("'", "''") + "'"
								+ ",email='" + email.replace("'", "''") + "'"
								+ ",department='" + department.replace("'", "''") + "'"
								+ ",title='" + title.replace("'", "''") + "'"
							+ " where id=" + insuredid + "" 
						);
						int result = DbUtils.save(select.toString());
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
				
				try {
					String claiminfoid = Putil.getString(request.getParameter("claiminfoid"));
					int claimtype = Putil.getInt(request.getParameter("info.claimtype"));
					int claimreason = Putil.getInt(request.getParameter("info.claimreason"));
					String claimdate = Putil.getString(request.getParameter("info.claimdate"));
					int paytype = Putil.getInt(request.getParameter("info.paytype"));
					String insuretype1 = Putil.getString(request.getParameter("info.insuretype1"));
					String insuretype2 = Putil.getString(request.getParameter("info.insuretype2"));
					String insuretype3 = Putil.getString(request.getParameter("info.insuretype3"));
					float spamount = Putil.getFloat(request.getParameter("info.spamount"));
					int diseaseid = Putil.getInt(request.getParameter("info.diseaseid"));
					int determinecount = Putil.getInt(request.getParameter("info.determinecount"));
					int receiptcount = Putil.getInt(request.getParameter("info.receiptcount"));
					String mark = Putil.getString(request.getParameter("info.mark"));

					
					StringBuilder select = new StringBuilder("update em_claiminfo set "
							+ "claimid=" + id + ""
							+ ",claimtype=" + claimtype + ""
							+ ",claimreason=" + claimreason + ""
							+ ",claimdate='" + claimdate.replace("'", "''") + "'"
							+ ",paytype=" + paytype + ""
							+ ",insuretype1='" + insuretype1.replace("'", "''") + "'"
							+ ",insuretype2='" + insuretype2.replace("'", "''") + "'"
							+ ",insuretype3='" + insuretype3.replace("'", "''") + "'"
							+ ",spamount=" + spamount + ""
							+ ",diseaseid=" + diseaseid + ""
							+ ",determinecount=" + determinecount + ""
							+ ",receiptcount=" + receiptcount + ""
							+ ",mark='" + mark.replace("'", "''") + "'"
						+ " where id=" + claiminfoid + "" 
					);
					int result = DbUtils.save(select.toString());
			
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}

		redirect(request, response, "/emClaim2.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
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

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claim p where p.id>=0"
					+ (keyword.length()>0?" and p.claimname like '%" + keyword + "%'":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claim p where p.id>=0"
					+ (keyword.length()>0?" and p.claimname like '%" + keyword + "%'":"")
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

		forward(request, response, "/admin/ClaimList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(checkAuthority("auth44", request, response)) {
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

			StringBuilder select = new StringBuilder("delete from em_claim "
					+ " where id=" + id + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emClaim.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	String auth43 = checkAuthority(43, request)!=null?"1":"";
    	String auth44 = checkAuthority(44, request)!=null?"1":"";
		request.setAttribute("auth43", auth43);
		request.setAttribute("auth44", auth44);
    	if(auth43.equals("") && auth44.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
    
    @Override
    public void def(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	  String method = request.getParameter("method");
          if("autocomplete".equals(method)) {
              autocomplate(request, response);
          } else if("maxNo".equals(method)) {
        	  maxNo(request, response);
          }else {
        	  listImage(request, response);
          }
          
    	
    }
    
    private void maxNo(HttpServletRequest request, HttpServletResponse response) {
    	String claimarchiveid = Putil.getString(request.getParameter("claimarchiveid")) ;
    	StringBuilder sql = new StringBuilder("select max(p.serialnumber) m from em_claim p where p.id>=0");
		if(claimarchiveid.length() > 0)
			sql.append(" and p.claimarchiveid = '" + claimarchiveid + "'");
		Map<String, Object> resultRow = DbUtils.queryOne(sql.toString());
		long maxNo = 0;
		if(resultRow != null && !resultRow.isEmpty()){
			Object r = resultRow.get("m");
			try {
				maxNo = Long.parseLong(String.valueOf(r)) + 1;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		toJson(maxNo, response);
	}

    
    private void autocomplate(HttpServletRequest request, HttpServletResponse response) {
    	String keyword = Putil.getString(request.getParameter("term")) ;
    	String claimarchiveid = Putil.getString(request.getParameter("claimarchiveid")) ;
		String inc = Putil.getString(request.getParameter("inc")) ;
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder("select p.* from em_claim p where p.id>=0");
		if(claimarchiveid.length() > 0)
			sql.append(" and p.claimarchiveid = '" + claimarchiveid + "'");
		if(keyword.length() > 0){
			sql.append(" and p.serialnumber like '%" + keyword + "%'");

			if(StringUtils.hasLength(inc)){
				String[] arr = inc.split(",");
				for (String string : arr) {
					sql.append(" or p." + string + " like '%" + keyword + "%'");
				}
			}
		}
		sql.append(" order by p.id desc limit 50");
    	resultRows = DbUtils.query(sql.toString());
		
    	if(resultRows != null && !resultRows.isEmpty()){
    		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    		for (Map<String, Object> map : resultRows) {
    			Map<String, Object> row = new HashMap<>();
    			row.put("id", map.get("id"));
    			row.put("label", map.get("serialnumber"));
    			row.put("value", map.get("serialnumber"));
    			rows.add(row);
			}
    		toJson(rows, response);
    	}else {
			toJson(new ArrayList<>(), response);
		}
	}

	private void listImage(HttpServletRequest request, HttpServletResponse response){
    	String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		try {
			long totalCount = 0 ;
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			
			
			int claimarchiveid = Putil.getInt(request.getParameter("claimarchiveid"));
			int insuredid = Putil.getInt(request.getParameter("insuredid"));
			

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.id) as total from em_claim p where p.id>=0"
					+ (claimarchiveid >0?" and p.claimarchiveid = " + claimarchiveid : "")
					+ (insuredid >0?" and p.insuredid = " + insuredid : "")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.* from em_claim p where p.id>=0"
					+ (claimarchiveid >0?" and p.claimarchiveid = " + claimarchiveid : "")
					+ (insuredid >0?" and p.insuredid = " + insuredid : "")
					+ " order by p.id desc limit " + (s-1)*m + "," + m + ""
				);
			
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("keyword", keyword);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");
			request.setAttribute("claimarchiveid", claimarchiveid);
			request.setAttribute("insuredid", insuredid);
			request.setAttribute("emClaimArchive", request.getParameter("emClaimArchive"));
			request.setAttribute("emInsured", request.getParameter("emInsured"));

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		forward(request, response, "/admin/ClaimListImage.jsp");
    }
}

