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

import com.web.servlet.*;


public class EmWxPayNotifyServlet extends Dispatcher {

	private static final long serialVersionUID = 1458827122604L;

    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	
    	try{
        	payNotify(request, response);
            
    		
    	} catch(Exception e) {
    		Putil.printLog("dispatcher error:" + request.getQueryString() + ":" + request.getRequestURI()) ;
    		e.printStackTrace(System.out) ;
    	}
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
	
	public void payNotify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<NameValuePair> result = new LinkedList<NameValuePair>();
		
		String requestBody = "";
		byte buf[] = new byte[8000000];
		int buf_pos = 0, ei = 0, si = 0 ;
		ServletInputStream ins = request.getInputStream() ;
		try {
			byte b[] = new byte[800000];
			int rleng = ins.read(b);
			while (rleng != -1) {
				buf = Putil.b_append(buf, buf_pos, b, rleng) ;
				buf_pos += rleng ;
				rleng = ins.read(b);
			}
			requestBody = new String(buf, 0, buf_pos, "utf-8");
			buf = null ;
		} catch (Exception e) {
			System.out.println("downloadAfile:" + e.toString());
		}

		System.out.println(String.format("wx notify: %s", request.getRequestURI() + ", body: " + requestBody));
		Map<String, String> xml = Util.decodeXml(requestBody);
		String return_code = Putil.getString(xml.get("return_code"));
		String return_msg= Putil.getString(xml.get("return_msg")); 
		String nonce_str= Putil.getString(xml.get("nonce_str"));
		String sign= Putil.getString(xml.get("sign"));
		String out_trade_no= Putil.getString(xml.get("out_trade_no"));
		String transaction_id= Putil.getString(xml.get("transaction_id")); 
		String trade_status= Putil.getString(xml.get("trade_status"));
		Integer total_fee= Integer.parseInt(xml.get("total_fee"));
		Map<String, Object> payment = EmAppApiServlet.getPayment(out_trade_no+"");
		Putil.printLog(return_code + ":" + return_msg + ":" + out_trade_no + ":" + trade_status + ":");
		if(payment==null) {
			Putil.printLog("payment==null");
			result.add(new BasicNameValuePair("return_code", "FAIL"));
			result.add(new BasicNameValuePair("return_msg", ""));
			write(Util.toXml(result), response);
			return;
		}
		if (return_code.equals("SUCCESS")) {
			String paytype = Putil.getString(payment.get("paytype"));
			if(!paytype.equals("4")) { //订单
				String orderid = Putil.getString(payment.get("orderid"));
				Map<String, Object> order = EmAppApiServlet.getOrder("", orderid+"");
				if(order!=null) {
					String status = Putil.getString(order.get("status"));
					String statuschg = "0";
					if(paytype.equals("1"))
						statuschg = "2";
					else if(paytype.equals("2"))
						statuschg = "4";
					else if(paytype.equals("3"))
						statuschg = "7";
					else
						statuschg = status;
						
					DbUtils.save("update em_order set status="+statuschg + " where orderid=" + orderid);
					DbUtils.save("update em_payment set paystatus=1 where paymentno='" + out_trade_no + "'");
					
					//发送消息
					String sessionid = "";
					String userid1 = "10009";  //订单处理中心
					String userid = Putil.getString(order.get("userid")); //接收者
					String content = "您已支付订金，客服人员会尽快处理您的订单。";
					String url = "orderDetail";
					String linkid = Putil.getString(order.get("uuid"));
					if(paytype.equals("1")) {
						content = "您已支付订金，客服人员会尽快处理您的订单。";
					} else if(paytype.equals("2")) {
						content = "您已支付预付款，客服人员会尽快处理您的订单。";
					} else if(paytype.equals("3")) {
						content = "您已支付尾款，谢谢您对我们工作的支持。";
					} else if(paytype.equals("4")) {
						content = "您在易医通平台充值成功，金额200元。";
					}
					MessageUtil.saveMessageAction(userid1, userid, content, url, linkid, sessionid);
					//公众号消息
					//Map<String, Object> user = EmAppApiServlet.getUserById(userid, "", false);
					//if(user!=null && Putil.getString(user.get("openid")).length()>0) {
			        //	EmWeixinServlet.sendMessageAlertMsg("易医通消息中心有新消息，请点击查看。", Putil.getString(user.get("openid"))) ;
					//}
					
				} else {
				}
				
			} else { //充值
				
			}
			result.add(new BasicNameValuePair("return_code", "SUCCESS"));
			result.add(new BasicNameValuePair("return_msg", ""));
		} else { //支付失败
			Putil.printLog("return_code:"+return_code);
			DbUtils.save("update em_payment set paystatus=2 where paymentno='" + out_trade_no + "'");
			result.add(new BasicNameValuePair("return_code", "FAIL"));
			result.add(new BasicNameValuePair("return_msg", ""));
		}
		System.out.println(Util.toXml(result));
		write(Util.toXml(result), response);
		/*
		String return_code = xml.get("return_code");
		String return_msg= xml.get("return_msg"); 
		String nonce_str= xml.get("nonce_str");
		String sign= xml.get("sign");
		String out_trade_no= xml.get("out_trade_no");
		String transaction_id= xml.get("transaction_id"); 
		String trade_status= xml.get("trade_status");
		Integer total_fee= Integer.parseInt(xml.get("total_fee"));
		
		log.warn(String.format("wx return_code: %s, return_msg: %s", return_code, return_msg));
		List<NameValuePair> result = new LinkedList<NameValuePair>();
		if (StringUtils.equals(return_code, "SUCCESS")) {
			log.warn(String.format("wx out_trade_no: %s, transaction_id: %s, sign: %s", out_trade_no, transaction_id, sign));

			String localSign = null;
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("return_code", return_code);
			signMap.put("return_msg", return_msg);
			signMap.put("appid", xml.get("appid"));
			signMap.put("mch_id",  xml.get("mch_id"));
			signMap.put("device_info", xml.get("device_info"));
			signMap.put("nonce_str",  xml.get("nonce_str"));
			signMap.put("result_code", xml.get("result_code"));
			signMap.put("err_code", xml.get("err_code"));
			signMap.put("err_code_des", xml.get("err_code_des"));
			signMap.put("openid", xml.get("openid"));
			signMap.put("is_subscribe", xml.get("is_subscribe"));
			signMap.put("trade_type", xml.get("trade_type"));
			signMap.put("bank_type", xml.get("bank_type"));
			signMap.put("total_fee", xml.get("total_fee"));
			signMap.put("fee_type", xml.get("fee_type"));
			signMap.put("cash_fee", xml.get("cash_fee"));
			signMap.put("cash_fee_type", xml.get("cash_fee_type"));
			signMap.put("coupon_fee",xml.get("coupon_fee"));
			signMap.put("coupon_count", xml.get("coupon_count"));
			signMap.put("coupon_batch_id_1", xml.get("coupon_batch_id_1"));
			signMap.put("coupon_batch_id_2", xml.get("coupon_batch_id_2"));
			signMap.put("coupon_batch_id_3", xml.get("coupon_batch_id_3"));
			signMap.put("coupon_id_1", xml.get("coupon_id_1"));
			signMap.put("coupon_id_2",xml.get("coupon_id_2"));
			signMap.put("coupon_id_3", xml.get("coupon_id_3"));
			signMap.put("coupon_fee_1", xml.get("coupon_fee_1"));
			signMap.put("coupon_fee_2", xml.get("coupon_fee_2"));
			signMap.put("coupon_fee_3",xml.get("coupon_fee_3"));
			signMap.put("transaction_id", xml.get("transaction_id"));
			signMap.put("out_trade_no", xml.get("out_trade_no"));
			signMap.put("attach",xml.get("attach"));
			signMap.put("time_end",xml.get("time_end"));
			
//			localSign = MD5.getMessageDigest((getSortParamsStringWithoutEmpty(signMap, false) + "&key=" + Constants.API_KEY).getBytes()).toUpperCase();
//			log.warn(String.format("签名  sign: %s",  localSign));
//			log.warn(String.format("签名 检验结果: %s, %s",  localSign, StringUtils.equals(sign, localSign) + ""));
//			if (!StringUtils.equals(sign, localSign)) {
//				result.add(new BasicNameValuePair("return_code", "FAIL"));
//				result.add(new BasicNameValuePair("return_msg", "签名失败"));
//				return Util.toXml(result);
//			}

			Payment payment = paymentDao.findBySn(out_trade_no);
			if(payment.getType()==Type.payment) {
				if (payment != null && payment.getStatus() == Status.wait) {
					log.warn(String.format("处理支付单...: %s", payment.getSn()));
					payment.setTotalFee(new BigDecimal(total_fee).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					log.warn("已支付金额：" + payment.getTotalFee() + ", 需支付的金额：" + payment.getPayAmount());
					if (payment.getTotalFee().compareTo(payment.getPayAmount()) >= 0) {
						Order order = payment.getOrder();
						if (order != null) {
							log.warn(String.format("处理订单...: %s ...", order.getSn()));
							try {
								orderService.payment(order, payment, null);
								log.warn(String.format("处理订单: %s 成功！", order.getSn()));
							} catch (Exception e) {
								log.warn(String.format("处理订单: %s 异常！", order.getSn()) ,e );
								e.printStackTrace();
							}
						} else {
							if (StringUtils.isNotEmpty(payment.getMemo())) {
								String[] sn = payment.getMemo().split(",");
								for (String s : sn) {
									order = orderDao.findBySn(s);
									if (order != null) {
										log.warn(String.format("处理订单..: %s ...", order.getSn()));
										try {
											orderService.payment(order, payment, null);
											log.warn(String.format("处理订单: %s 成功！", order.getSn()));
										} catch (Exception e) {
											log.warn(String.format("处理订单: %s 异常！", order.getSn()), e);
											e.printStackTrace();
										}

									}
								}
							}
						}

						if (payment.getPoint() != null && payment.getPoint().longValue() > 0) {
							Member member = payment.getMember();
							if (member != null) {
								MemberPoint memberPoint = new MemberPoint();
								memberPoint.setMember(member);
								memberPoint.setEvent("支付抵扣");
								memberPoint.setPoint(-payment.getPoint());
								memberPoint.setOrderid(order.getId());
								memberPointDao.save(memberPoint);
								long point = member.getPoint() - payment.getPoint();
//								member.setPoint(point);
								member.setPoint(memberPointDao.sumPointByMember(member.getId()));
								memberDao.save(member);
							}
						}

					}

					payment.setAccount(transaction_id);
					payment.setStatus(Status.success);
					payment.setPaymentDate(new Date());
					paymentDao.save(payment);
				}
			} else if(payment.getType()==Type.recharge) {  //充值
				payment.setTotalFee(new BigDecimal(total_fee).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
				payment.setAccount(transaction_id);
				payment.setStatus(Status.success);
				payment.setPaymentDate(new Date());
				paymentDao.save(payment);
				Member member = payment.getMember();
				if (member != null) {
					long point = (long)(payment.getAmount().floatValue() * 100);
//					member.setPoint(point);
//					memberDao.save(member);
//					MemberPoint memberPoint = new MemberPoint();
//					memberPoint.setMember(member);
//					memberPoint.setEvent("充值");
//					memberPoint.setPoint((long)(payment.getAmount().floatValue() * 100));
//					memberPointDao.save(memberPoint);
					memberPointService.add(member, "充值成功", point, true, null);
				}
			}			

			result.add(new BasicNameValuePair("return_code", "SUCCESS"));
			result.add(new BasicNameValuePair("return_msg", ""));
		} else {
			result.add(new BasicNameValuePair("return_code", "FAIL"));
			result.add(new BasicNameValuePair("return_msg", ""));
		}
		return Util.toXml(result);
		*/
	}
	

}

