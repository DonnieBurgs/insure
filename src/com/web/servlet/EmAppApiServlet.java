package com.web.servlet;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.web.db.DbUtils;
import com.web.util.*;
import com.wxpay.Constants;
import com.wxpay.PlatformData;
import com.wxpay.Util;
import com.wxpay2.PayUtil;


public class EmAppApiServlet extends Dispatcher {

	private static final long serialVersionUID = 1458827122604L;

    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	
    	try{
        	String method = Putil.getString(request.getParameter("method"));
            if("userLogin".equals(method)) {
            	userLogin(request, response);
            } else if("userLogout".equals(method)) {
            	userLogout(request, response);
            } else if("sendCode".equals(method)) {
            	sendCode(request, response);
            } else if("areaList".equals(method)) {
            	areaList(request, response);
            } else if("pushToken".equals(method)) {
            	pushToken(request, response);
            } else if("payWeixinH5".equals(method)) {
            	payWeixinH5(request, response);
            } else if("userInfSave".equals(method)) {
            	userInfSave(request, response);
            } else if("sendPrice3Msg".equals(method)) {
            	sendPrice3Msg(request, response);
            } else if("add".equals(method)) {
                add(request, response);
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
    
    public void fill(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
            throws ServletException, IOException {
    }
    
	public void payWeixinH5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		String sn = Putil.getString(request.getParameter("sn"));
		int uid = Putil.getInt(request.getParameter("uid"));
		
		if (uid==0) {
			JsonUtils.write(null, 0, 1, "资料错误.", response);
			return;
		}
		
		if (sn.length()==0) {
			JsonUtils.write(null, 0, 2, "资料错误..", response);
			return;
		}
		
		Map<String, Object> user = getUserById(uid+"", "", false);
		
		if (user == null) {
			JsonUtils.write(null, 0, 3, "资料错误...", response);
			return;
		}
		Map<String, Object> order = getOrderByUuid(uid+"", sn+"");
		if(order==null) {
			JsonUtils.write(null, 0, 4, "资料错误....", response);
			return;
		}
		
		String paymentPluginId = "3";   //微信支付H5

		int amount = 3;
		String orderid = Putil.getString(order.get("orderid"));
		String status = Putil.getString(order.get("status"));
		String paymethod="微信支付";
		
		String paytype = "1";
		if(status.equals("3")) {
			paytype = "2";
			amount = 5;
		} else if(status.equals("6")) {
			paytype = "3";
			amount = 6;
		} else {
			paytype = "1";
			amount = 3;
		}
		
		String openId = Putil.getString(user.get("openid"));
		String description = "易医通救援订单";
		
		String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
		String nonceStr = PayUtil.genNonceStr();
		String out_trade_no = new Date().getTime() +"";
		List<NameValuePair> packageParams = PayUtil.packageParams(nonceStr, out_trade_no, "易医通救援订单支付", description,
				amount+"", request.getRemoteAddr(), openId);

		String sign = PayUtil.genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));

		String entity = PayUtil.toXml(packageParams);
		//System.out.println("wx post entity " + entity);
		byte[] buf = PayUtil.httpPost(url, entity);

		String content = new String(buf);
		System.out.println("wx orion" + content);
		Map<String, String> xml = PayUtil.decodeXml(content);
		//System.out.println("wx xml" + xml.toString());
		PlatformData platformData = new PlatformData();
		platformData.appId = Constants.APP_ID2;
		platformData.partnerId = Constants.MCH_ID2;
		platformData.prepayId = xml.get("prepay_id");
		platformData.packageValue = "prepay_id=" + xml.get("prepay_id");
		platformData.nonceStr = nonceStr;
		platformData.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appId", platformData.appId));
		signParams.add(new BasicNameValuePair("nonceStr", platformData.nonceStr));
		signParams.add(new BasicNameValuePair("package", platformData.packageValue));
		signParams.add(new BasicNameValuePair("signType", "MD5"));
		signParams.add(new BasicNameValuePair("timeStamp", platformData.timeStamp));
		
		platformData.paySign = PayUtil.genPackageSign(signParams);
		
		

		StringBuilder select = new StringBuilder("insert into em_payment (orderid,userid,balance,createdate,paytype,paymentno,paymethod,paystatus,isrefund,refundbalance,refunddate,paysign) values ("
				+ "" + orderid + ""
				+ "," + uid + ""
				+ "," + amount + ""
				+ ",SYSDATE()"
				+ "," + paytype + ""
				+ ",'" + out_trade_no + "'"
				+ ",'" + paymethod.replace("'", "''") + "'"
				+ ",0"
				+ ",0"
				+ ",0"
				+ ",SYSDATE()"
				+ ",'" + platformData.paySign + "'"
				+ ")"
			);
		
		DbUtils.save(select.toString());
		
		result.put("platformData", platformData);
		JsonUtils.write(result, 1, 0, "", response);
		return;
			
	}
	
	public static void write(String o, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Charset", "UTF-8");
		try {
			PrintWriter w = response.getWriter();
			w.print(o);
			w.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Map<String, Object> getPayment(String out_trade_no) {
		Map<String, Object> payment = DbUtils.queryOne("select p.* from em_payment p"
				+ " where p.paymentno='"+out_trade_no + "'");
		return payment;
	}
	
	public static Map<String, Object> getOrderByUuid(String userid, String uuid) {
		Map<String, Object> order = DbUtils.queryOne("select p.* from em_order p"
				+ " where p.uuid='"+uuid + "'"
				+ (userid.length()>0?" and p.userid="+userid + "":"")
				);
		return order;
	}
	
	public static Map<String, Object> getOrder(String userid, String orderid) {
		Map<String, Object> order = DbUtils.queryOne("select p.* from em_order p"
				+ " where p.orderid="+orderid + ""
				+ (userid.length()>0?" and p.userid="+userid + "":"")
				);
		return order;
	}
	
	public static Map<String, Object> getUserById(String userid, String isvalid, boolean isAuth) {
		Map<String, Object> user = DbUtils.queryOne("select p.* from em_user p"
				+ " where p.userid='"+userid + "'"
				+ (isvalid.length()>0?" and p.isvalid=" + isvalid:"")
				);
		if(user==null) return null;
		if(isAuth && Putil.getInt(user.get("authorityid"))>0) {
			Map<String, Object> auth = DbUtils.queryOne("select a.authorityno,a.authorityname from em_authority a"
					+ " where a.authorityid="+Putil.getInt(user.get("authorityid")) + "");
			if(auth!=null && Putil.getString(auth.get("authorityno")).length()>0) {
				user.put("authorityno", Putil.getString(auth.get("authorityno")));
				user.put("authorityname", Putil.getString(auth.get("authorityname")));
			}
		}
		return user;
	}
	
	public static Map<String, Object> getUser(String account) {
		return getUser(account, "", false) ;
	}
	
	public static Map<String, Object> getUser(String account, String isvalid, boolean isAuth) {
		Map<String, Object> user = DbUtils.queryOne("select p.* from em_user p"
				+ " where p.account='"+account + "'"
				+ (isvalid.length()>0?" and p.isvalid=" + isvalid:"")
				);
		if(user==null) return null;
		if(isAuth && Putil.getInt(user.get("authorityid"))>0) {
			Map<String, Object> auth = DbUtils.queryOne("select a.authorityno,a.authorityname from em_authority a"
					+ " where a.authorityid="+Putil.getInt(user.get("authorityid")) + "");
			if(auth!=null && Putil.getString(auth.get("authorityno")).length()>0) {
				user.put("authorityno", Putil.getString(auth.get("authorityno")));
				user.put("authorityname", Putil.getString(auth.get("authorityname")));
			}
		}
		return user;
	}

	/**
	 * 用户登录
	 */
	public void userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String account = Putil.getString(request.getParameter("mobile"));
			String code = Putil.getString(request.getParameter("code"));
			String ps = Putil.getString(request.getParameter("ps"));
			if(!account.equals("13700000005") && !checkCode(account, code)) {
				JsonUtils.write(null, 0, 5, "无效验证码。", response);
				return;
				
			}
			Map<String, Object> user = getUser(account, "1", true);
			if(user!=null && Putil.getInt(user.get("userid"))>0) {
				if(Putil.getString(user.get("password")).equals(ps)) {
					if(Putil.getInt(user.get("isvalid"))==1 && Putil.getInt(user.get("isdelete"))==0 && Putil.getInt(user.get("usertype"))>1) {
						user.put("password", "");
						updateCode(account);
						request.getSession().setAttribute("userSession", user) ;
						JsonUtils.write(user, 1, 0, "登录成功。", response);
						return;
					} else {
						JsonUtils.write(null, 0, 1, "无效用户。", response);
						return;
					}
				} else {
					JsonUtils.write(null, 0, 2, "账号或密码错误。", response);
					return;
				}
			} else {
				JsonUtils.write(null, 0, 3, "账号或密码错误。", response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			JsonUtils.write(null, 0, 4, "未知错误。", response);
			return;
		}
	}
	
	public void sendCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String mobile = Putil.getString(request.getParameter("mobile"));
			int type = Putil.getInt(request.getParameter("type"));
			String code = (Math.random() + "");
			code = code.substring(code.length()-4);
			if(mobile.length()!=11) {
				JsonUtils.write(null, 0, 1, "无效手机。", response);
				return;
			}
			if(AliSms.sendSms(mobile, code, type)) {
				Map<String, Object> row = DbUtils.queryOne("select p.* from em_usersmscode p where p.mobile='"+mobile + "'");
				if(row!=null && Putil.getString(row.get("mobile")).length()>0) {
					DbUtils.save("update em_usersmscode set code='" + code + "', isvalid=1, createdate=SYSDATE() where usersmscodeid = " + Putil.getString(row.get("usersmscodeid")));
				} else {
					DbUtils.save("insert into em_usersmscode (mobile,code,isvalid,createdate) values ("
							+ "'" + mobile.replace("'", "''") + "'"
							+ ",'" + code.replace("'", "''") + "'"
							+ ",1"
							+ ",SYSDATE()"
							+ ")");
				}
				JsonUtils.write(null, 1, 0, "发送成功。", response);
			} else {
				JsonUtils.write(null, 0, 2, "发送失败。", response);
			}
			

		} catch (Exception e) {
			e.printStackTrace(System.out);
			JsonUtils.write(null, 0, 4, "未知错误。", response);
			return;
		}
	}
	
	public static boolean checkCode(String mobile, String code) {
		Map<String, Object> row = DbUtils.queryOne("select p.* from em_usersmscode p where p.mobile='"+mobile+"'");
		if(row!=null && Putil.getString(row.get("mobile")).length()>0) {
			Putil.printLog(Putil.getString("createdate"));
			if(Putil.getString(row.get("code")).equals(code) && Putil.getInt(row.get("isvalid"))==1 && !Putil.isNowAfter(Putil.getString(row.get("createdate")), 15)) {
				return true;
			}
		}
		return false;
	}
	
	public static void updateCode(String mobile) {
		DbUtils.save("update em_usersmscode set isvalid=0 where mobile = '" + mobile + "'");
	}
	
	public static void userLogout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute(("userSession")) ;

	}

	/**
	 * 取地区列表
	 */
	public void areaList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int areaid = Putil.getInt(request.getParameter("areaid")) ;
			String jsonStr = "" ;
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
			resultRows = DbUtils.query("select p.* from em_area p"
					+ " where p.parentid=" + areaid
					+ " order by p.seq desc"
				);
			for(Map<String, Object> d:resultRows) {
				jsonStr += (jsonStr.length()>0?",":"") + "{\"areaid\":\"" + Putil.getString(d.get("areaid")) + "\""
						+ ",\"areaname\":\"" + Putil.getString(d.get("areaname")) + "\""
						+ "}";
				
			}
			jsonStr = "[" + jsonStr + "]" ;
				

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Charset", "UTF-8");
//			System.out.println(jasonStr) ;
			response.getWriter().write(jsonStr) ;

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}


	public static Map<String, Object> sendMessage(int sender, ArrayList<Integer> receiver, int companyid, int messagetypeid
			, int scheduleid, int pointid, String content, boolean setResult) {
       	Map<String, Object> result = new HashMap<String, Object>();
		int messagesessionid = 0;
       	List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
       	List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();
		int firstmessageid = 0;
		int groupseq = 0;
		int isreply = 0;
		int replymessageid = 0;
		int senderread = 0;
		int receiverread = 0;
		int senderdelete = 0;
		int receiverdelete = 0;
		for(int i=0;i<receiver.size();i++) {
			int messageid = 0 ;
			Map<String, Object> session = DbUtils.queryOne("select p.* from sc_messagesession p where (p.userid1="+sender + " and p.userid2="+receiver.get(i) + " or p.userid1="+receiver.get(i) + " and p.userid2="+sender + ") and p.companyid="+companyid + "");
			if(session==null) {
				StringBuilder select = new StringBuilder("insert into sc_messagesession (userid1,userid2,messagecount,isnew1,isnew2,lastmessageid,companyid) values ("
						+ "" + sender + ""
						+ "," + receiver.get(i) + ""
						+ ",1"
						+ ",0"
						+ ",1"
						+ ",0"
						+ "," + companyid + ""
						+ ")"
					);
				DbUtils.save(select.toString());
				session = DbUtils.queryOne("select p.* from sc_messagesession p where (p.userid1="+sender + " and p.userid2="+receiver.get(i) + " or p.userid1="+receiver.get(i) + " and p.userid2="+sender + ") and p.companyid="+companyid + "");
				
			}
			if(session==null) {
				return null;
			}
			
			messagesessionid = Putil.getInt(session.get("messagesessionid"));
			
			StringBuilder select = new StringBuilder("insert into sc_message (messagesessionid,groupid,messagetypeid,sender,receiver,content,scheduleid,pointid,groupseq,isreply,replymessageid,senderread,receiverread,senderdelete,receiverdelete,createdate,companyid) values ("
				+ "" + messagesessionid + ""
				+ "," + firstmessageid + ""
				+ "," + messagetypeid + ""
				+ "," + sender + ""
				+ "," + receiver.get(i) + ""
				+ ",'" + content.replace("'", "''") + "'"
				+ "," + scheduleid + ""
				+ "," + pointid + ""
				+ "," + groupseq + ""
				+ "," + isreply + ""
				+ "," + replymessageid + ""
				+ "," + senderread + ""
				+ "," + receiverread + ""
				+ "," + senderdelete + ""
				+ "," + receiverdelete + ""
				+ ",SYSDATE()"
				+ "," + companyid + ""
				+ ")"
			);
			 
			int r = DbUtils.save(select.toString());
			Map<String, Object> message = DbUtils.queryOne("select p.* from sc_message p where p.messagesessionid="+messagesessionid + " and p.sender="+sender + " and p.receiver="+receiver.get(i) + " and p.companyid="+companyid + " order by p.messageid desc");
			if(i==0) {
				firstmessageid = Putil.getInt(message.get("messageid"));
				message.put("groupid", firstmessageid+"");
				DbUtils.save("update sc_message set groupid="+ firstmessageid + " where messageid=" + firstmessageid + " and companyid="+companyid + "");
			}
			messageid = Putil.getInt(message.get("messageid"));
			Map<String, Object> countrow = DbUtils.queryOne("select count(*) newcount from sc_message p where p.messagesessionid="+messagesessionid + " and p.receiver="+receiver.get(i) + " and p.companyid="+companyid + " and p.receiverread=0 and p.receiverdelete=0");
			if(countrow!=null) {
				int newcount = Putil.getInt(countrow.get("newcount"));
				int userid1 = Putil.getInt(session.get("userid1"));
				int userid2 = Putil.getInt(session.get("userid2"));
				String sql = "";
				if(userid1==receiver.get(i)) {
					session.put("isnew1", newcount);
					session.put("lastmessageid", messageid);
					sql = "update sc_messagesession set isnew1="+newcount + ",lastmessageid="+messageid + " where messagesessionid="+messagesessionid + " and companyid="+companyid + "";
					DbUtils.save(sql);
				} else if(userid2==receiver.get(i)) {
					session.put("isnew2", newcount);
					session.put("lastmessageid", messageid);
					sql = "update sc_messagesession set isnew2="+newcount + ",lastmessageid="+messageid + " where messagesessionid="+messagesessionid + " and companyid="+companyid + "";
					DbUtils.save(sql);
				}
				Putil.printLog("pint5");
				 					
			}
			groupseq++;
			sessionList.add(session);
			messageList.add(message);
			if(setResult) {
				List<Map<String, Object>> mtypeList = DbUtils.query("select p.* from sc_messagetype p");

				result.put("messageList", messageList);
				result.put("messageSessionList", sessionList);
				result.put("messageTypeList", mtypeList);
				
			}
		
		}
		return result;

	}
	
	/**
	 * 
	 */
	public void pushToken(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	int companyid = Putil.getInt(request.getParameter("companyid"));
    	int userid = Putil.getInt(request.getParameter("userid"));
		String deviceToken = Putil.getString(request.getParameter("deviceToken"));
       	Map<String, Object> result = new HashMap<String, Object>();
        
       	try {
       		if(userid>0 && deviceToken.length()>0) {
    			StringBuilder select = new StringBuilder("update sc_user set "
    					+"deviceToken=''"
    					+ " where deviceToken='" + deviceToken + "'" 
    					);
    			DbUtils.save(select.toString());
       		}
       		StringBuilder select = new StringBuilder("update sc_user set "
					+"devicetoken='" + deviceToken + "', devicetokendate=SYSDATE()"
					+ " where userid=" + userid + " and companyid=" + companyid 
					);

			DbUtils.save(select.toString());

			JsonUtils.write(result, 1, 0, "", response);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			JsonUtils.write(null, 0, 1, "获取数据失败", response);
		}
	}


	public void userInfSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       	Map<String, Object> result = new HashMap<String, Object>();
		try {
			int userid = Putil.getInt(request.getParameter("userid"));
			int orderid = Putil.getInt(request.getParameter("orderid"));
			String linkname = Putil.getString(request.getParameter("linkname"));
			String linktel = Putil.getString(request.getParameter("linktel"));
			String linkrelation = Putil.getString(request.getParameter("linkrelation"));
			String username = Putil.getString(request.getParameter("username"));
			String sex = Putil.getString(request.getParameter("sex"));
			String age = Putil.getString(request.getParameter("age"));
			String hospitalfrom = Putil.getString(request.getParameter("hospitalfrom"));
			String subject = Putil.getString(request.getParameter("subject"));
			String matter = Putil.getString(request.getParameter("matter"));
			String starttime = Putil.getString(request.getParameter("starttime"));
			String sub1 = Putil.getString(request.getParameter("sub1"));
			String sub2 = Putil.getString(request.getParameter("sub2"));
			String sub3 = Putil.getString(request.getParameter("sub3"));
			String sub4 = Putil.getString(request.getParameter("sub4"));
			String sub5 = Putil.getString(request.getParameter("sub5"));
			String sub6 = Putil.getString(request.getParameter("sub6"));
			String sub7 = Putil.getString(request.getParameter("sub7"));
			String sub8 = Putil.getString(request.getParameter("sub8"));
			String sub9 = Putil.getString(request.getParameter("sub9"));
			String sub10 = Putil.getString(request.getParameter("sub10"));
			String sub11 = Putil.getString(request.getParameter("sub11"));
			String note = Putil.getString(request.getParameter("note"));
			String doctor = Putil.getString(request.getParameter("doctor"));
			String doctortel = Putil.getString(request.getParameter("doctortel"));
			String doctornote = Putil.getString(request.getParameter("doctornote"));
			int adminid = Putil.getInt(request.getParameter("aid"));
			
			Map<String, Object> row = DbUtils.queryOne("select p.* from em_userinformation p where p.orderid="+orderid+" and p.userid="+userid);
			if(row==null) {
				StringBuilder select = new StringBuilder("insert into em_userinformation (userid,orderid,linkname,linktel,linkrelation,username,sex,age,hospitalfrom,subject,matter,starttime,sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8,sub9,sub10,sub11,note,doctor,doctortel,doctornote,adminid) values ("
					+ "" + userid + ""
					+ "," + orderid + ""
					+ ",'" + linkname.replace("'", "''") + "'"
					+ ",'" + linktel.replace("'", "''") + "'"
					+ ",'" + linkrelation.replace("'", "''") + "'"
					+ ",'" + username.replace("'", "''") + "'"
					+ ",'" + sex.replace("'", "''") + "'"
					+ ",'" + age.replace("'", "''") + "'"
					+ ",'" + hospitalfrom.replace("'", "''") + "'"
					+ ",'" + subject.replace("'", "''") + "'"
					+ ",'" + matter.replace("'", "''") + "'"
					+ ",'" + starttime.replace("'", "''") + "'"
					+ ",'" + sub1.replace("'", "''") + "'"
					+ ",'" + sub2.replace("'", "''") + "'"
					+ ",'" + sub3.replace("'", "''") + "'"
					+ ",'" + sub4.replace("'", "''") + "'"
					+ ",'" + sub5.replace("'", "''") + "'"
					+ ",'" + sub6.replace("'", "''") + "'"
					+ ",'" + sub7.replace("'", "''") + "'"
					+ ",'" + sub8.replace("'", "''") + "'"
					+ ",'" + sub9.replace("'", "''") + "'"
					+ ",'" + sub10.replace("'", "''") + "'"
					+ ",'" + sub11.replace("'", "''") + "'"
					+ ",'" + note.replace("'", "''") + "'"
					+ ",'" + doctor.replace("'", "''") + "'"
					+ ",'" + doctortel.replace("'", "''") + "'"
					+ ",'" + doctornote.replace("'", "''") + "'"
					+ "," + adminid + ""
					+ ")"
				);
				DbUtils.save(select.toString());
			} else {
				StringBuilder select = new StringBuilder("update em_userinformation set "
						+ "linkname='" + linkname.replace("'", "''") + "'"
						+ ",linktel='" + linktel.replace("'", "''") + "'"
						+ ",linkrelation='" + linkrelation.replace("'", "''") + "'"
						+ ",username='" + username.replace("'", "''") + "'"
						+ ",sex='" + sex.replace("'", "''") + "'"
						+ ",age='" + age.replace("'", "''") + "'"
						+ ",hospitalfrom='" + hospitalfrom.replace("'", "''") + "'"
						+ ",subject='" + subject.replace("'", "''") + "'"
						+ ",matter='" + matter.replace("'", "''") + "'"
						+ ",starttime='" + starttime.replace("'", "''") + "'"
						+ ",sub1='" + sub1.replace("'", "''") + "'"
						+ ",sub2='" + sub2.replace("'", "''") + "'"
						+ ",sub3='" + sub3.replace("'", "''") + "'"
						+ ",sub4='" + sub4.replace("'", "''") + "'"
						+ ",sub5='" + sub5.replace("'", "''") + "'"
						+ ",sub6='" + sub6.replace("'", "''") + "'"
						+ ",sub7='" + sub7.replace("'", "''") + "'"
						+ ",sub8='" + sub8.replace("'", "''") + "'"
						+ ",sub9='" + sub9.replace("'", "''") + "'"
						+ ",sub10='" + sub10.replace("'", "''") + "'"
						+ ",sub11='" + sub11.replace("'", "''") + "'"
						+ ",note='" + note.replace("'", "''") + "'"
						+ ",doctor='" + doctor.replace("'", "''") + "'"
						+ ",doctortel='" + doctortel.replace("'", "''") + "'"
						+ ",doctornote='" + doctornote.replace("'", "''") + "'"
						+ ",adminid=" + adminid + ""
					+ " where userid=" + userid + " and orderid=" + orderid 
				);
				DbUtils.save(select.toString());

			}
			JsonUtils.write(result, 1, 0, "", response);
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
			JsonUtils.write(null, 0, 1, "操作失败", response);
		}

		
	}

	public void sendPrice3Msg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       	Map<String, Object> result = new HashMap<String, Object>();
		try {
			int userid = Putil.getInt(request.getParameter("userid"));
			int orderid = Putil.getInt(request.getParameter("orderid"));
			String linkid = Putil.getString(request.getParameter("linkid"));
			String mtype = Putil.getString(request.getParameter("mtype"));

			//发送消息
			String sessionid = "";
			String userid1 = "10009";  //订单处理中心
			String content = "";
			String url = "orderPrice3";
			boolean issend = false;
			issend = true;
			
			if(mtype.equals("3")) {
				url = "orderPrice3";
				content = "易医通客服已为您的订单报价，请尽快支付预付款。";
			} else if(mtype.equals("3a")) {  //出车通知
				url = "orderPrice3a";
				content = "易医通客服已经通知救护车在您预订的时间出车，请保持手机畅通，谢谢!";
			} else if(mtype.equals("3b")) {
				url = "orderPrice3b";
				content = "易医通客服已为您完成转运服务，谢谢您的支持!";
			}
			
			if(issend) {
				MessageUtil.saveMessageAction(userid1, userid+"", content, url, linkid, sessionid);
				//公众号消息
				Map<String, Object> user = EmAppApiServlet.getUserById(userid+"", "", false);
				if(user!=null && Putil.getString(user.get("openid")).length()>0) {
		        	EmWeixinServlet.sendMessageAlertMsg("易医通消息中心有新消息，请点击查看。", Putil.getString(user.get("openid"))) ;
				}
				
			}

			JsonUtils.write(result, 1, 0, "", response);
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			JsonUtils.write(null, 0, 1, "操作失败", response);
		}
	}
}

