package com.web.servlet.admin;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;
import com.web.servlet.EmAppApiServlet;
import com.web.servlet.EmWeixinServlet;
import com.web.util.*;

public class EmOrderServlet extends UserSecureDispatcher {

	private static final long serialVersionUID = 1488465992922L;
	private String auth11 = "";
	private String auth12 = "";

	
	public void blank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
    	
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_otype = Putil.getString(request.getParameter("key_otype")) ;
		String key_status = Putil.getString(request.getParameter("key_status")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_otype", key_otype);
		request.setAttribute("key_status", key_status);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");


		//订单类型列表
		List<Map<String, Object>> otList = DbUtils.query("select p.* from em_ordertype p order by p.seq desc");
		request.setAttribute("otList", otList);
		//车型列表
		List<Map<String, Object>> cartypeList = DbUtils.query("select p.* from em_cartype p order by p.seq desc");
		request.setAttribute("cartypeList", cartypeList);
		//套餐列表
		List<Map<String, Object>> packtypeList = DbUtils.query("select p.* from em_packtype p order by p.seq desc");
		request.setAttribute("packtypeList", packtypeList);
		//服务项目列表
		List<Map<String, Object>> serviceoptiontypeList = DbUtils.query("select p.* from em_serviceoptiontype p order by p.seq desc");
		request.setAttribute("serviceoptiontypeList", serviceoptiontypeList);
		
		request.setAttribute("nowdate", Putil.format4());

		forward(request, response, "/admin/OrderAdd.jsp");
	
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_otype = Putil.getString(request.getParameter("key_otype")) ;
		String key_status = Putil.getString(request.getParameter("key_status")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_otype", key_otype);
		request.setAttribute("key_status", key_status);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		Map<String, Object> admin = LoginManager.getUser(request);
		if(admin==null) {
			return ;
		}
		try {
			int userid = Putil.getInt(request.getParameter("userid"));
			int otype = Putil.getInt(request.getParameter("otype"));
			String departuredate = Putil.getString(request.getParameter("departuredate"));
			String address1 = Putil.getString(request.getParameter("address1"));
			String province1 = Putil.getString(request.getParameter("province1"));
			String city1 = Putil.getString(request.getParameter("city1"));
			String district1 = Putil.getString(request.getParameter("district1"));
			float lat1 = Putil.getFloat(request.getParameter("lat1"));
			float lng1 = Putil.getFloat(request.getParameter("lng1"));
			String address2 = Putil.getString(request.getParameter("address2"));
			String province2 = Putil.getString(request.getParameter("province2"));
			String city2 = Putil.getString(request.getParameter("city2"));
			String district2 = Putil.getString(request.getParameter("district2"));
			float lat2 = Putil.getFloat(request.getParameter("lat2"));
			float lng2 = Putil.getFloat(request.getParameter("lng2"));
			float distance = Putil.getFloat(request.getParameter("distance"));
			int cartypeid = Putil.getInt(request.getParameter("cartypeid"));
			int packtypeid = Putil.getInt(request.getParameter("packtypeid"));
			String servicetype = Putil.getString(request.getParameter("servicetype"));
			int doctorid = Putil.getInt(request.getParameter("doctorid"));
			float price1 = Putil.getFloat(request.getParameter("price1"));
			float price2 = Putil.getFloat(request.getParameter("price2"));
			float price3 = Putil.getFloat(request.getParameter("price3"));
			float price4 = Putil.getFloat(request.getParameter("price4"));
			float price5 = Putil.getFloat(request.getParameter("price5"));
			int status = Putil.getInt(request.getParameter("status"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String note = Putil.getString(request.getParameter("note"));
			String servicenote = Putil.getString(request.getParameter("servicenote"));
			float score = Putil.getFloat(request.getParameter("score"));
			int score1 = Putil.getInt(request.getParameter("score1"));
			int score2 = Putil.getInt(request.getParameter("score2"));
			int score3 = Putil.getInt(request.getParameter("score3"));
			String scorenote = Putil.getString(request.getParameter("scorenote"));
			
			status = 1;
			
			StringBuilder select = new StringBuilder("insert into em_order (userid,otype,departuredate,address1,province1,city1,district1,lat1,lng1,address2,province2,city2,district2,lat2,lng2,distance,cartypeid,packtypeid,servicetype,doctorid,price1,price2,price3,price4,price5,status,createdate,note,servicenote,score,score1,score2,score3,scorenote) values ("
				+ "" + userid + ""
				+ "," + otype + ""
				+ ",'" + departuredate.replace("'", "''") + "'"
				+ ",'" + address1.replace("'", "''") + "'"
				+ ",'" + province1.replace("'", "''") + "'"
				+ ",'" + city1.replace("'", "''") + "'"
				+ ",'" + district1.replace("'", "''") + "'"
				+ "," + lat1 + ""
				+ "," + lng1 + ""
				+ ",'" + address2.replace("'", "''") + "'"
				+ ",'" + province2.replace("'", "''") + "'"
				+ ",'" + city2.replace("'", "''") + "'"
				+ ",'" + district2.replace("'", "''") + "'"
				+ "," + lat2 + ""
				+ "," + lng2 + ""
				+ "," + distance + ""
				+ "," + cartypeid + ""
				+ "," + packtypeid + ""
				+ ",'" + servicetype.replace("'", "''") + "'"
				+ "," + doctorid + ""
				+ "," + price1 + ""
				+ "," + price2 + ""
				+ "," + price3 + ""
				+ "," + price4 + ""
				+ "," + price5 + ""
				+ "," + status + ""
				+ ",SYSDATE()"
				+ ",'" + note.replace("'", "''") + "'"
				+ ",'" + servicenote.replace("'", "''") + "'"
				+ "," + score + ""
				+ "," + score1 + ""
				+ "," + score2 + ""
				+ "," + score3 + ""
				+ ",'" + scorenote.replace("'", "''") + "'"
				+ ")"
			);

			int result = DbUtils.save(select.toString());
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emOrder.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_otype="+key_otype+"&key_status="+key_status+"&m="+m+"&s="+s);
		
	}

	public void fill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_otype = Putil.getString(request.getParameter("key_otype")) ;
		String key_status = Putil.getString(request.getParameter("key_status")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_otype", key_otype);
		request.setAttribute("key_status", key_status);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		String adminid = Putil.getString(userSession.get("userid"));
		request.setAttribute("adminid", adminid);

		//订单类型列表
		List<Map<String, Object>> otList = DbUtils.query("select p.* from em_ordertype p order by p.seq desc");
		request.setAttribute("otList", otList);
		//车型列表
		List<Map<String, Object>> cartypeList = DbUtils.query("select p.* from em_cartype p order by p.seq desc");
		request.setAttribute("cartypeList", cartypeList);
		//套餐列表
		List<Map<String, Object>> packtypeList = DbUtils.query("select p.* from em_packtype p order by p.seq desc");
		request.setAttribute("packtypeList", packtypeList);
		//订单状态列表
		List<Map<String, Object>> orderstatusList = DbUtils.query("select p.* from em_orderstatus p where p.orderstatusid>0 order by p.seq desc");
		request.setAttribute("orderstatusList", orderstatusList);
		//服务项目列表
		List<Map<String, Object>> serviceoptiontypeList = DbUtils.query("select p.* from em_serviceoptiontype p order by p.seq desc");
		if(serviceoptiontypeList!=null && serviceoptiontypeList.size()>0) {
			for(Map<String, Object> item:serviceoptiontypeList) {
				item.put("stid", ","+Putil.getString(item.get("serviceoptiontypeid"))+",");
			}
		}
		request.setAttribute("serviceoptiontypeList", serviceoptiontypeList);

		String orderid = Putil.getString(request.getParameter("orderid")) ;
		if(orderid.length()>0) {
			Map<String, Object> row = DbUtils.queryOne("select p.*,u.username,u.mobile,ot.ordertypename,os.orderstatusname from em_order p,em_user u,em_ordertype ot,em_orderstatus os,em_cartype ct,em_packtype pt where p.orderid="+orderid
					+ " and p.userid=u.userid and p.otype=ot.ordertypeid and p.status=os.orderstatusid and p.cartypeid=ct.cartypeid and p.packtypeid=pt.packtypeid");
			if(row!=null && Putil.getString(row.get("servicetype")).length()>0) row.put("servicetype", ","+Putil.getString(row.get("servicetype"))+",") ;
			if(row!=null) {
				Map<String, Object> inf = DbUtils.queryOne("select p.* from em_userinformation p where p.orderid="+orderid);
				request.setAttribute("inf", inf);
			}
			request.setAttribute("item", row);
		}

		forward(request, response, "/admin/OrderEdit.jsp");
		
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_otype = Putil.getString(request.getParameter("key_otype")) ;
		String key_status = Putil.getString(request.getParameter("key_status")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("keyword", keyword);
		request.setAttribute("key_otype", key_otype);
		request.setAttribute("key_status", key_status);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String orderid = Putil.getString(request.getParameter("orderid")) ;
			int otype = Putil.getInt(request.getParameter("otype"));
			String departuredate = Putil.getString(request.getParameter("departuredate"));
			String address1 = Putil.getString(request.getParameter("address1"));
//			String province1 = Putil.getString(request.getParameter("province1"));
//			String city1 = Putil.getString(request.getParameter("city1"));
//			String district1 = Putil.getString(request.getParameter("district1"));
			float lat1 = Putil.getFloat(request.getParameter("lat1"));
			float lng1 = Putil.getFloat(request.getParameter("lng1"));
			String address2 = Putil.getString(request.getParameter("address2"));
//			String province2 = Putil.getString(request.getParameter("province2"));
//			String city2 = Putil.getString(request.getParameter("city2"));
//			String district2 = Putil.getString(request.getParameter("district2"));
			float lat2 = Putil.getFloat(request.getParameter("lat2"));
			float lng2 = Putil.getFloat(request.getParameter("lng2"));
			float distance = Putil.getFloat(request.getParameter("distance"));
			int cartypeid = Putil.getInt(request.getParameter("cartypeid"));
			int packtypeid = Putil.getInt(request.getParameter("packtypeid"));
//			String servicetype = Putil.getString(request.getParameter("servicetype"));
			int doctorid = Putil.getInt(request.getParameter("doctorid"));
			float price1 = Putil.getFloat(request.getParameter("price1"));
			float price2 = Putil.getFloat(request.getParameter("price2"));
			float price3 = Putil.getFloat(request.getParameter("price3"));
			float price4 = Putil.getFloat(request.getParameter("price4"));
			float price5 = Putil.getFloat(request.getParameter("price5"));
			int status = Putil.getInt(request.getParameter("status"));
			String createdate = Putil.getString(request.getParameter("createdate"));
			String note = Putil.getString(request.getParameter("note"));
			String servicenote = Putil.getString(request.getParameter("servicenote"));
			float score = Putil.getFloat(request.getParameter("score"));
			int score1 = Putil.getInt(request.getParameter("score1"));
			int score2 = Putil.getInt(request.getParameter("score2"));
			int score3 = Putil.getInt(request.getParameter("score3"));
			String scorenote = Putil.getString(request.getParameter("scorenote"));
			
			String servicetype = "";
			for(int i=0;i<20;i++) {
				if(Putil.getString(request.getParameter("st"+i)).length()>0) {
					servicetype += (servicetype.length()>0?",":"") + Putil.getString(request.getParameter("st"+i)) ;
				}
			}

			String openid = "";
			String userid = "";
			String linkid = "";
			String statusOld = "";
			Map<String, Object> orderOld = DbUtils.queryOne("select p.*,u.username,u.openid from em_order p,em_user u where p.orderid="+orderid
					+ " and p.userid=u.userid");
			if(orderOld!=null && Putil.getString(orderOld.get("userid")).length()>0) {
				userid = Putil.getString(orderOld.get("userid")) ;
				openid = Putil.getString(orderOld.get("openid")) ;
				linkid = Putil.getString(orderOld.get("uuid"));
				statusOld = Putil.getString(orderOld.get("status"));
			}

			StringBuilder select = new StringBuilder("update em_order set "
					+ "otype=" + otype + ""
					+ ",departuredate='" + departuredate.replace("'", "''") + "'"
					+ ",address1='" + address1.replace("'", "''") + "'"
//					+ ",province1='" + province1.replace("'", "''") + "'"
//					+ ",city1='" + city1.replace("'", "''") + "'"
//					+ ",district1='" + district1.replace("'", "''") + "'"
					+ ",lat1=" + lat1 + ""
					+ ",lng1=" + lng1 + ""
					+ ",address2='" + address2.replace("'", "''") + "'"
//					+ ",province2='" + province2.replace("'", "''") + "'"
//					+ ",city2='" + city2.replace("'", "''") + "'"
//					+ ",district2='" + district2.replace("'", "''") + "'"
					+ ",lat2=" + lat2 + ""
					+ ",lng2=" + lng2 + ""
					+ ",distance=" + distance + ""
					+ ",cartypeid=" + cartypeid + ""
					+ ",packtypeid=" + packtypeid + ""
					+ ",servicetype='" + servicetype.replace("'", "''") + "'"
					+ ",doctorid=" + doctorid + ""
					+ ",price1=" + price1 + ""
					+ ",price2=" + price2 + ""
					+ ",price3=" + price3 + ""
					+ ",price4=" + price4 + ""
					+ ",price5=" + price5 + ""
					+ ",status=" + status + ""
					+ ",note='" + note.replace("'", "''") + "'"
					+ ",servicenote='" + servicenote.replace("'", "''") + "'"
//					+ ",score=" + score + ""
//					+ ",score1=" + score1 + ""
//					+ ",score2=" + score2 + ""
//					+ ",score3=" + score3 + ""
//					+ ",scorenote='" + scorenote.replace("'", "''") + "'"
				+ " where orderid=" + orderid + "" 
			);
			int result = DbUtils.save(select.toString());

			//发送消息
			String sessionid = "";
			String userid1 = "10009";  //订单处理中心
			String content = "您已支付订金，客服人员会尽快处理您的订单。";
			String url = "orderDetail";
			boolean issend = false;
			if(statusOld.equals("2") && status==3) {
				content = "易医通客户人员已为您的订单报价，请尽快支付预付款。";
				issend = true;
			//} else if(statusOld.equals("2") && status==3) {
			//	content = "易医通客户人员已为您的订单报价，请尽快支付预付款。";
			//	issend = true;
			} else if(statusOld.equals("4") && status==5) {
				content = "易医通已经通知救护车在您预订的时间出车，请保持手机畅通，谢谢。";
				issend = true;
			} else if(statusOld.equals("5") && status==6) {
				content = "易医通已经为您服务完毕，请支付尾款，谢谢您的支持和配合。";
				issend = true;
			} else if(!statusOld.equals("7") && status==7) {
				content = "易医通已经为您完成救援服务，谢谢您的支持和配合。";
				issend = true;
			}
			//if(issend) {
			//	MessageUtil.saveMessageAction(userid1, userid, content, url, linkid, sessionid);
				//公众号消息
			//	Map<String, Object> user = EmAppApiServlet.getUserById(userid, "", false);
			//	if(user!=null && Putil.getString(user.get("openid")).length()>0) {
		     //   	EmWeixinServlet.sendMessageAlertMsg("易医通消息中心有新消息，请点击查看。", Putil.getString(user.get("openid"))) ;
			//	}
				
			//}

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		redirect(request, response, "/emOrder.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_otype="+key_otype+"&key_status="+key_status+"&m="+m+"&s="+s);
		
	}

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		try {
			long totalCount = 0 ;
			String keyword = Putil.getString(request.getParameter("keyword")) ;
			String key_otype = Putil.getString(request.getParameter("key_otype")) ;
			String key_status = Putil.getString(request.getParameter("key_status")) ;
			int s = Putil.getInt(request.getParameter("s")); 
			int m = Putil.getInt(request.getParameter("m")); 
			String o = Putil.getString(request.getParameter("o")); // 排序字
			String sort = Putil.getString(request.getParameter("sort")); // 顺序
			
			//订单类型列表
			List<Map<String, Object>> otList = DbUtils.query("select p.* from em_ordertype p order by p.seq desc");
			request.setAttribute("otList", otList);
			//订单状态列表
			List<Map<String, Object>> orderstatusList = DbUtils.query("select p.* from em_orderstatus p where p.orderstatusid>0 order by p.seq desc");
			request.setAttribute("orderstatusList", orderstatusList);

			// 列表
			if(s==0) s = 1 ;  //pagenum
			if(m==0) m = 15 ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();

			StringBuilder countSql = new StringBuilder("select count(p.orderid) as total from em_order p,em_user u,em_ordertype ot,em_orderstatus os where p.status>0"
					+ " and p.userid=u.userid and p.otype=ot.ordertypeid and p.status=os.orderstatusid"
					+ (key_otype.length()>0?" and p.otype=" + key_otype:"")
					+ (key_status.length()>0?" and p.status=" + key_status:"")
					+ (keyword.length()>0?" and (p.address1 like '%" + keyword + "%' or p.address2 like '%" + keyword + "%')":"")
					);
			Map<String, Object> countMap = DbUtils.queryOne(countSql.toString());
			if (countMap!=null) {
				totalCount = Putil.getInt(countMap.get("total"));
			}
			
			// 列表分页语句
			resultRows = DbUtils.query("select p.*,u.username,u.mobile,ot.ordertypename,os.orderstatusname from em_order p,em_user u,em_ordertype ot,em_orderstatus os where p.status>0"
					+ " and p.userid=u.userid and p.otype=ot.ordertypeid and p.status=os.orderstatusid"
					+ (key_otype.length()>0?" and p.otype=" + key_otype:"")
					+ (key_status.length()>0?" and p.status=" + key_status:"")
					+ (keyword.length()>0?" and (p.address1 like '%" + keyword + "%' or p.address2 like '%" + keyword + "%')":"")
					+ " order by p.createdate desc limit " + (s-1)*m + "," + m + ""
				);
			
			
			request.setAttribute("totalRow", totalCount);
			request.setAttribute("resultRows", resultRows);
			request.setAttribute("keyword", keyword);
			request.setAttribute("key_otype", key_otype);
			request.setAttribute("key_status", key_status);
			request.setAttribute("m", m+"");
			request.setAttribute("s", s+"");

		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}

		forward(request, response, "/admin/OrderList.jsp");
		
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	if(auth12.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return;
    	}
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid")) ;
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword")) ;
		String key_otype = Putil.getString(request.getParameter("key_otype")) ;
		String key_status = Putil.getString(request.getParameter("key_status")) ;
		int s = Putil.getInt(request.getParameter("s")); 
		int m = Putil.getInt(request.getParameter("m")); 
		request.setAttribute("key_otype", key_otype);
		request.setAttribute("key_status", key_status);
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m+"");
		request.setAttribute("s", s+"");

		try {
			String orderid = Putil.getString(request.getParameter("orderid"));

			StringBuilder select = new StringBuilder("delete from em_order "
					+ " where orderid=" + orderid + "" 
					);

			int result = DbUtils.save(select.toString());

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		redirect(request, response, "/emOrder.do?method=list&uf_parentid="+uf_parentid+"&keyword="+keyword+"&key_otype="+key_otype+"&key_status="+key_status+"&m="+m+"&s="+s);
		
	}
	
    public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response) {
    	auth11 = checkAuthority(11, request)!=null?"1":"";
    	auth12 = checkAuthority(12, request)!=null?"1":"";
		request.setAttribute("auth11", auth11);
		request.setAttribute("auth12", auth12);
    	if(auth11.equals("") && auth12.equals("")) {
    		this.prompt(response, "没有权限，请联系管理员！");
    		return false;
    	}
    	return true;
    	
    }
}

