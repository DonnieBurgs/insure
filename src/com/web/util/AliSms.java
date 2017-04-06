package com.web.util;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliSms {
	public static String URL = "http://gw.api.taobao.com/router/rest";
	public static String APPKEY = "23669518";
	public static String SECRET = "e97f1c60f86369884b27e85468cd1235";
	public static int TYPE_LOGIN = 0;
	public static int TYPE_REGIST = 1;
	public static int TYPE_FORGETPW = 2;
	public static int TYPE_IDENTITY = 3;
	public static String signName[] = {"登录验证","注册验证","找回密码","身份验证"};
	public static String templateCode[] = {"SMS_51065021","SMS_51065019","SMS_53150125","SMS_51065023"};
	public static String PRODUCT = "易医通";
	public static boolean sendSms(String mobile, String code, int type) {
		TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		
		req.setExtend( "" );
		req.setSmsType( "normal" );
		req.setSmsFreeSignName( signName[type] );
		req.setSmsParamString( "{code:'"+code+"',product:'"+PRODUCT+"'}" );
		req.setRecNum(mobile );
		req.setSmsTemplateCode( templateCode[type] );
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			if(rsp.getBody().indexOf("err_code\":\"0")>0) {
				return true;
			}
			System.out.println(rsp.getBody());
			
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
		return false;
	}
	public static void main(String [] args) throws Exception {
		//sendSms( "13711553301", AliSms.TYPE_LOGIN);


	}

}
//
/**
 * 
 * 活动验证 变更验证 登录验证 注册验证 身份验证 找回密码
 * SMS_51065023 身份验证验证码
验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！
SMS_51065021 登录确认验证码
验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。
SMS_51065019 用户注册验证码
验证码${code}，您正在注册成为${product}用户，感谢您的支持！
SMS_51065017 修改密码验证码

验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。
SMS_53150125 找回密码
验证码${code}，您正在进行${product}用户找回密码操作，感谢您的支持！

 */
