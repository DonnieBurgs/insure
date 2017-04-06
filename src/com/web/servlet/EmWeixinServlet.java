package com.web.servlet;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.http.HttpResponse; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.entity.StringEntity; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.util.EntityUtils; 

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.web.db.DbUtils;
import com.web.util.JsonUtils;
import com.web.util.Putil;






import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




public class EmWeixinServlet extends BaseHttpServlet {
	private static final long serialVersionUID = 6799124304534718262L;
	public static boolean isFirstRun = true ;
	public static String path = "/WEB_APP/webdocs/sc/data/" ;
	static FileWriter fo = null ;
    
    public static String appid = "wx7c30dc7735cbcbea";
    public static String appSecret = "71c792bf1efd8975e2c5b4df62c68d13";
    public static String weixinUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&response_type=code&scope=snsapi_base&state=123&redirect_uri=";
    
	
	public static DefaultHttpClient httpclient; 
    
    static { 
        httpclient = new DefaultHttpClient(); 
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端 
    } 
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response) ;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        if(isFirstRun) {
        	isFirstRun = false ;
    		initData(path) ;
        	
        }
		String method = Putil.getString(request.getParameter("method"));
		if (method.equals("token")) {
			token(request, response);
		} else if (method.equals("init")) {
			initData(path) ;
		}
	}

	private void token(HttpServletRequest request, HttpServletResponse response) {
		String echostr = Putil.getString(request.getParameter("echostr"));
		if(echostr.length()>0) {
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				response.setHeader("Charset", "UTF-8");
				response.getWriter().write(echostr) ;
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		} else {
			StringBuffer sb = new StringBuffer();
			byte buf[] = new byte[8000000];
			int buf_pos = 0, ei = 0, si = 0 ;
			String recvStr = "", sendStr = "" ;
			
			String toUserName = "", fromUserName = "", msgType = "", content = "", latitude = "", longitude = "", label = "" ;
			
			try {
				ServletInputStream ins = request.getInputStream() ;
				try {
					// FileInputStream fin = new FileInputStream(url) ;
					byte b[] = new byte[800000];
					int rleng = ins.read(b);
					while (rleng != -1) {
						buf = Putil.b_append(buf, buf_pos, b, rleng) ;
						buf_pos += rleng ;
						rleng = ins.read(b);
					}
					sb.append(new String(buf, 0, buf_pos, "utf-8"));
					buf = null ;
				} catch (Exception e) {
					System.out.println("downloadAfile:" + e.toString());
				}
				
				recvStr = sb.toString() ;
				si = recvStr.indexOf("ToUserName") ;
				if(si>=0) {
					si = recvStr.indexOf("CDATA[", si) ;
					ei = recvStr.indexOf("]", si+1) ;
					if(si>0 && ei>si) {
						toUserName = recvStr.substring(si+6, ei) ;
					}
				}
				si = recvStr.indexOf("FromUserName") ;
				if(si>=0) {
					si = recvStr.indexOf("CDATA[", si) ;
					ei = recvStr.indexOf("]", si+1) ;
					if(si>0 && ei>si) {
						fromUserName = recvStr.substring(si+6, ei) ;
					}
				}
				si = recvStr.indexOf("MsgType") ;
				if(si>=0) {
					si = recvStr.indexOf("CDATA[", si) ;
					ei = recvStr.indexOf("]", si+1) ;
					if(si>0 && ei>si) {
						msgType = recvStr.substring(si+6, ei) ;
					}
				}
				si = recvStr.indexOf("Content") ;
				if(si>=0) {
					si = recvStr.indexOf("CDATA[", si) ;
					ei = recvStr.indexOf("]", si+1) ;
					if(si>0 && ei>si) {
						content = recvStr.substring(si+6, ei) ;
					}
				}
//				System.out.println("fromUserName:" + fromUserName) ;
//				System.out.println(content) ;

				if(msgType.equals("text")) {
					sendStr = "";
					if(content.length()==11) {
						Map<String, Object> user = DbUtils.queryOne("select p.* from sc_user p where p.companyid=10004 and p.usertype=1 and p.mobile="+content + "");
						if(user!=null) {
							String userid = Putil.getString(user.get("userid"));
							int companyid = Putil.getInt(user.get("companyid")) ;
							int positionid = Putil.getInt(user.get("positionid")) ;
							int projectid = 0;
							try {
								List<Map<String, Object>> resultRows = Putil.getProjectList(companyid, positionid, 0) ;
								if(resultRows!=null) {
									if(resultRows.size()==1) {
										projectid = Putil.getInt(resultRows.get(0).get("positionid"));
									}
								}
								
							} catch (Exception e) {
								e.printStackTrace(System.out);
								JsonUtils.write(null, 0, 2, "", response);
							}
							String urltmp = "http://secure.emtsos.com/scWeixinSh.do?method=scheduleProject&userid=" + userid + "";
							if(projectid>0) {
								urltmp = "http://secure.emtsos.com/scWeixinSh.do?method=schedule&userid=" + userid + "&projectid=" + projectid + "";
							}
							
							sendStr = "<xml>" 
									+ "<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>"
									+ "<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>"
									+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
									+ "<MsgType><![CDATA[text]]></MsgType>"
									+ "<Content><![CDATA[" + urltmp + "]]></Content>"
									+ "</xml>" ;

						}
						
							

					}
					if(sendStr.length()==0) {
						sendStr = "<xml>" 
								+ "<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>"
								+ "<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>"
								+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
								+ "<MsgType><![CDATA[text]]></MsgType>"
								+ "<Content><![CDATA[请发手机号码确认身份]]></Content>"
								+ "</xml>" ;
					}
				} else if(msgType.equals("image")) {
				} else if(msgType.equals("voice")) {
				} else if(msgType.equals("video")) {
				} else if(msgType.equals("location")) {
					si = recvStr.indexOf("Location_X", si) ;
					ei = recvStr.indexOf("<", si+1) ;
					if(si>0 && ei>si) {
						latitude = recvStr.substring(si+11, ei) ;
					}
					si = recvStr.indexOf("Location_Y", si) ;
					ei = recvStr.indexOf("<", si+1) ;
					if(si>0 && ei>si) {
						longitude = recvStr.substring(si+11, ei) ;
					}
					si = recvStr.indexOf("Label") ;
					if(si>=0) {
						si = recvStr.indexOf("CDATA[", si) ;
						ei = recvStr.indexOf("]", si+1) ;
						if(si>0 && ei>si) {
							label = recvStr.substring(si+6, ei) ;
						}
					}
//					System.out.println("LOCATION....."+recvStr + latitude + ":" + longitude + ":" + label) ;
				} else if(msgType.equals("link")) {
				} else if(msgType.equals("event")) {
//					System.out.println("event.....") ;
//					System.out.println(recvStr) ;
					
					si = recvStr.indexOf("Event") ;
					if(si>=0) {
						si = recvStr.indexOf("CDATA[", si) ;
						ei = recvStr.indexOf("]", si+1) ;
						if(si>0 && ei>si) {
							content = recvStr.substring(si+6, ei) ;
						}
					}
//					System.out.println("event....." + content) ;
					if(content.equals("subscribe")) {
//						System.out.println("subscribe.....") ;
						//sendStr = procSubscribe("欢迎关注  服务号") ;
						//if(sendStr.length()>0) {
							sendStr = "<xml>" 
								+ "<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>"
								+ "<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>"
								+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
								+ "<MsgType><![CDATA[text]]></MsgType>"
								+ "<Content><![CDATA[请发手机号码确认身份]]></Content>"
								+ "</xml>" ;
							
						//}
					} else if(content.toLowerCase().equals("click")) {
						si = recvStr.indexOf("EventKey") ;
						String eventKey = "" ;
						
						if(si>=0) {
							si = recvStr.indexOf("CDATA[", si) ;
							ei = recvStr.indexOf("]", si+1) ;
							if(si>0 && ei>si) {
								eventKey = recvStr.substring(si+6, ei) ;
							}
						}
						if(eventKey.equals("payMenu")) {
							sendStr = processAn(fromUserName);
							if(sendStr.length()>0) {
								sendStr = "<xml>" 
									+ "<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>"
									+ "<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>"
									+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
									+ "<MsgType><![CDATA[news]]></MsgType>"
									+ sendStr
									+ "</xml>" ;
								
							}

						}
						
					} else if(content.toLowerCase().equals("scan")) {
							try{
						} catch(Exception e) {
							
						}

						System.out.println("scan.....") ;
					} else if(content.toUpperCase().equals("LOCATION")) {
						System.out.println("LOCATION.....") ;
					}
				} else {
					
				}
//				sendStr = "<xml>" ;
//				sendStr += "<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>" ;
//				sendStr += "<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>" ;
//				sendStr += "<CreateTime>" + new Date().getTime() + "</CreateTime>" ;
//				sendStr += "<MsgType><![CDATA[text]]></MsgType>" ;
//				sendStr += "<Content><![CDATA[你好坎坎坷ilksdfikj就]]></Content>" ;
//				sendStr += "</xml>" ;
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				response.setHeader("Charset", "UTF-8");
				response.getWriter().write(sendStr) ;
//				System.out.println(sendStr) ;
			} catch(Exception e) {
				Putil.printLog("token:") ;
				e.printStackTrace(System.out) ;
			}
			
		}
	}

	private String procText(String content, String openid) {
		String sendStr = "", content_bak = "" ;
		int count = 0 ;
		Vector<Vector<String>> resultTravel = new Vector<Vector<String>>() ;
		Vector<Vector<String>> resultCourse = new Vector<Vector<String>>() ;


		
		content = content.trim() ;
		content_bak = content ;
		
		String accessToken = "", nickname = "", userid = "" ;
		if(content.toLowerCase().equals("pay111")) {
//            try {
//				accessToken = getAccessToken(AnWeixinServlet.appid, AnWeixinServlet.appSecret);
//	            String menu = getMenuInfo(accessToken) ;
//	            FileWriter fw = new FileWriter("/usr/local/menu.txt") ;
//	            fw.write(menu);
//	            fw.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
		} else if(content.toLowerCase().equals("pay123456")) {
			
			return sendStr ;

		} else {
			sendStr = procSubscribe("搜索不到所需资料！") ;
		}
		return sendStr ;
	}
	
	public static String processAn(String openid) {
		boolean r = false ;
		String accessToken = "", nickname = "", userid = "", ctype = "", authorityno="", sendStr = "" ;
        try {  
			if(openid.length()>0) {
				Map<String, Object> user = LoginManager.loadActiveUserByOpenid(openid) ;
				if(user!=null) {
					userid = Putil.getString(user.get("userid")) ;
					ctype = Putil.getString(user.get("ctype")) ;
					authorityno = Putil.getString(user.get("authorityno")) ;
					r = true ;
				} else {
					r = false ;
					
				}
			} else {
				r = false ;
			}
			if(r==false) {
				accessToken = getAccessToken(EmWeixinServlet.appid, EmWeixinServlet.appSecret);
				if(accessToken.length()>0) {
					nickname = Putil.getNickname(openid, accessToken) ;
				}
			}

        } catch (Exception e) {
            e.printStackTrace();  
        }  

        if(r) {
        } else {
        	
        }

		return sendStr ;
	}

	private String procSubscribe(String title) {
		String sendStr = "" ;
		sendStr += "<ArticleCount>1</ArticleCount>";
		sendStr += "<Articles>";
		sendStr += "<item>";
		sendStr += "<Title><![CDATA[欢迎关注]]></Title>";
		sendStr += "<Description><![CDATA[欢迎关注！]]></Description>";
		sendStr += "<PicUrl><![CDATA[header3.png]]></PicUrl>";
		sendStr += "<Url><![CDATA[http://mp.weixin.qq.com/]]></Url>";
		sendStr += "</item>";
		sendStr += "</Articles>";
		
		return sendStr ;
	}
	
	public static void sendMessageAlertMsg(String msg, String openid) {
        String params = "{"
            	+ "\"touser\":\"" + openid + "\"," 
            	+ "\"msgtype\":\"news\","
            	+ "\"news\":{"
            	+ "\"articles\":[" ;
        params += "{"
                + "\"title\":\"新消息提醒\","
        	    + "\"description\":\"" + msg + "\","
        	    + "\"url\":\"" + weixinUrl + Putil.urlEncode("http://www.emtsos.com/emt/v-v1-zh_CN-/emt/index.w?f=4") + "\","
        	    +  "\"picurl\":\"http://www.emtsos.com/image/logo800.png\""
        	    + "}" ;
        	
        
        
        params += "]"
        	    + "}"
        	    + "}" ;
        EmWeixinServlet.sendMsg(params) ;
		
	}

    public static String sendTextMsg(String msg, String openid) { 
    	String accessToken = "" ;
    	try{
        	accessToken = getAccessToken(EmWeixinServlet.appid, EmWeixinServlet.appSecret);  
            HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken); 
            String params = "{"
            	+ "\"touser\":\"" + openid + "\"," 
            	+ "\"msgtype\":\"text\","
            	+ "\"text\":{"
                + "\"content\":\"" + msg + "\""
        	    + "}"
        	    + "}" ;
            httpost.setEntity(new StringEntity(params, "UTF-8")); 
            HttpResponse response = httpclient.execute(httpost); 
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8"); 
            System.out.println(jsonStr); 
    	} catch(Exception e) {
    		
    	}
        return ""; 

    }
	
    public static String sendMsg(String params) { 
    	String accessToken = "" ;
    	try{
        	accessToken = getAccessToken(EmWeixinServlet.appid, EmWeixinServlet.appSecret);  
            HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken); 
            httpost.setEntity(new StringEntity(params, "UTF-8")); 
            HttpResponse response = httpclient.execute(httpost); 
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8"); 
//            System.out.println(jsonStr); 
    	} catch(Exception e) {
    		
    	}
        return ""; 

    }
	
    public static String createMenu(String params, String accessToken) throws Exception { 
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken); 
        httpost.setEntity(new StringEntity(params, "UTF-8")); 
        HttpResponse response = httpclient.execute(httpost); 
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8"); 
//        System.out.println(jsonStr); 
//        JSONObject object = JSON.parseObject(jsonStr); 
        return ""; 

    }
    
    //{"access_token":"ACCESS_TOKEN","expires_in":7200}
    public static String getAccessToken(String appid, String secret) throws Exception {  
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);  
        HttpResponse response = httpclient.execute(get);  
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
        int iS=0, iE=0;
        String access_token = "" ;
        iS = jsonStr.indexOf("access_token") ;
        iE = jsonStr.indexOf("\"", iS+15) ;
        if(iS>0 && iE>iS) {
        	access_token = jsonStr.substring(iS+15, iE) ;
        }
        return access_token;  
    }  
    /** 
     * 查询菜单 
     */  
    public static String getMenuInfo(String accessToken) throws Exception {  
//        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken);  
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=" + accessToken);  
        HttpResponse response = httpclient.execute(get);  
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
        return jsonStr;  
    }  
      
    /** 
     * 删除自定义菜单 
     */  
    public static String deleteMenu(String accessToken) throws Exception {  
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);  
        HttpResponse response = httpclient.execute(get);  
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
//        System.out.println(jsonStr) ;
        return "";  
    }  

	public static void initData(String path) {
	}

	public static String removeHtml(String content) {
		if(content==null) return "" ;
	    content = content.replaceAll("</li>", "brbr").replaceAll("</p>", "brbr").replaceAll("</td>", "brbr").replaceAll("</table>", "brbr").replaceAll("</ul>", "brbr").replaceAll("\r\n", "brbr").replaceAll("\r", "brbr").replaceAll("\n", "brbr").replaceAll("<br>", "brbr").trim() ;
	    content = removeHtmlTab(content, "<", ">", 2000) ;
	    content = removeHtmlTab(content, "&", ";", 10) ;
	    content = content.replaceAll("brbrbrbrbrbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbr", "\n") ;
	    return content ;
	}
	
	public static String removeHtmlbutImg(String content) {
		if(content==null) return "" ;
	    content = renameHtmlTab(content,"<img", ">") ;
	    content = content.replaceAll("</li>", "brbr").replaceAll("</p>", "brbr").replaceAll("</td>", "brbr").replaceAll("</table>", "brbr").replaceAll("</ul>", "brbr").replaceAll("\r\n", "brbr").replaceAll("\r", "brbr").replaceAll("\n", "brbr").replaceAll("<br>", "brbr").trim() ;
	    content = removeHtmlTab(content, "<", ">", 2000) ;
	    content = removeHtmlTab(content, "&", ";", 10) ;
	    content = content.replaceAll("imgstart", "<img").replaceAll("imgend", ">").replaceAll("brbrbrbrbrbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbr", "\n") ;
	    return content ;
	}
	
	public static String removeHtmlTab(String content, String s, String e, int maxlen) {
		if(content==null) return "" ;
	    int iS=0, iE=0;
	    while(true) {
	    	iS = content.indexOf(s) ;
	    	iE = content.indexOf(e, iS) ;
	    	if(iS>=0 && iE>0 && iE>iS && iE<(iS+maxlen)) {
	    		content = content.substring(0, iS) + content.substring(iE+1) ;
	    	} else
	    		break ;
	    }
	    return content ;
	}
	
	public static String renameHtmlTab(String content, String s, String e) {
		if(content==null) return "" ;
	    int iS=0, iE=0;
	    while(true) {
	    	iS = content.indexOf(s) ;
	    	iE = content.indexOf(e, iS) ;
	    	if(iS>=0 && iE>0 && iE>iS) {
	    		content = content.substring(0, iS) + "imgstart" + content.substring(iS+4, iE) + "imgend" + content.substring(iE+1) ;
	    	} else
	    		break ;
	    }
	    return content ;
	}
	
	public static void createMenu() {
        try {  
            // 获取accessToken -参数appid，secret  
            String accessToken = getAccessToken(EmWeixinServlet.appid, EmWeixinServlet.appSecret);  
//            String accessToken = "CkMjkBMcDWPx8uLaeldMXxKYMJzqw-BuexesvR07fMgChMQXcwd76yPJBLd2o43_Cv3WD-6nYHfJQ8FlSYb_on9ziMQHZ_Y0bIFAd8SUZ4z3fllJ7YukbLpqYN5yurj80KIwaZG6QYxEI8adRLu7ZA";

            System.out.println(accessToken);  
            // 创建菜单  
            String s = "{\"button\":[{\"name\":\"预约诊疗\",\"sub_button\":[{\"type\":\"view\",\"name\":\"预约挂号\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=hospitalList") + "\"},{\"type\":\"view\",\"name\":\"诊单管理\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=registerList") + "\"}]},{\"name\":\"安加介绍\",\"sub_button\":[{\"type\":\"view\",\"name\":\"安加介绍\",\"url\":\"http://mp.weixin.qq.com/s?__biz=MzA4NTY2MTE1NA==&mid=401507388&idx=1&sn=a07a27b13d95ac44c68362c2a1c7cc1e#rd\"},{\"type\":\"view\",\"name\":\"院点介绍\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=hospitalList") + "\"},{\"type\":\"view\",\"name\":\"医生介绍\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=doctorList&hospitalid=10002") + "\"}]},{\"name\":\"会员中心\",\"sub_button\":[{\"type\":\"view\",\"name\":\"个人档案\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=userView") + "\"},{\"type\":\"view\",\"name\":\"个人病历\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=medicalrecordList") + "\"},{\"type\":\"view\",\"name\":\"体检报告\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=physicalList") + "\"},{\"type\":\"view\",\"name\":\"在线答疑\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=messagesessionList") + "\"},{\"type\":\"view\",\"name\":\"消息中心\",\"url\":\"" + weixinUrl + Putil.urlEncode("http://m.andcare.me/scWeixinSh.do?method=informationList") + "\"}]}]}" ;
            String res = createMenu(s, accessToken);  
//            String res = deleteMenu(accessToken) ;
//            System.out.println(res);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
	}
	public static void deleteMenu() {
        try {  
            // 获取accessToken -参数appid，secret  
            String accessToken = getAccessToken(EmWeixinServlet.appid, EmWeixinServlet.appSecret);  

//            System.out.println(accessToken);  
            String res = deleteMenu(accessToken) ;
//            System.out.println(res);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
	}

	public static void createQR() {
        try {  
            // 获取accessToken -参数appid，secret  
            String accessToken = getAccessToken(EmWeixinServlet.appid, EmWeixinServlet.appSecret);  
//            System.out.println(accessToken);  
            // 创建菜单  
            String s = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";  

            HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken); 
            httpost.setEntity(new StringEntity(s, "UTF-8")); 
            HttpResponse response = httpclient.execute(httpost); 
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8"); 
//            System.out.println(jsonStr); 
//            JSONObject object = JSON.parseObject(jsonStr); 
            //https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
	}

	    //图片转化成base64字符串
	    public static String GetImageStr()
	    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        String imgFile = "/Users/sam/Downloads/1B58A486-CF73-41F5-9F55-2D00D1096538.png";//待处理的图片
	        InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
	        try 
	        {
	            in = new FileInputStream(imgFile);        
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(data);//返回Base64编码过的字节数组字符串
	    }
	    
	    //base64字符串转化成图片
	    public static boolean GenerateImage(String imgStr)
	    {   //对字节数组字符串进行Base64解码并生成图片
	        if (imgStr == null) //图像数据为空
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try 
	        {
	            //Base64解码
	            byte[] b = decoder.decodeBuffer(imgStr);
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {//调整异常数据
	                    b[i]+=256;
	                }
	            }
	            //生成jpeg图片
	            String imgFilePath = "/Users/sam/Downloads/222.png";//新生成的图片
	            OutputStream out = new FileOutputStream(imgFilePath);    
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        } 
	        catch (Exception e) 
	        {
	            return false;
	        }
	    }


    public static void main(String[] args) { 
    	deleteMenu() ;
    	createMenu() ;
//    	new DecimalFormat("0.00").format(12.33666999);
//    	Putil.printLog(new DecimalFormat("0.00").format(13453453532.33666999)) ;
    }  
	
}
