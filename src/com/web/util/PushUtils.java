package com.web.util;

import java.util.Map;

import com.web.db.DbUtils;

import push.android.AndroidUnicast;

public class PushUtils {
	public final static String UMENG_APPKEY = "578f2aaee0f55ac3aa002bc9";
	public final static String UMENG_APPMASTERSECRET = "bp3jtnyfhcs4iwcammcaaiwvayqsss6j";
	public final static String UMENG_production_mode = "true";

	public static boolean sendMessage(Map<String, Object> message, int type) {
		boolean result = false;
		String deviceToken = Putil.getString(message.get("devicetoken"));
		if(deviceToken.length()==0) {
			int userid = Putil.getInt(message.get("userid"));
			
			if (userid == 0)
				return false;
			Map<String, Object> user = DbUtils.queryOne("select p.* from sc_user p where p.userid="+userid);
			if(user==null) {
				return false;
			}

			deviceToken = Putil.getString(user.get("devicetoken"));
		}
		if(deviceToken.length()==0) {
			return false;
		}


		try {
			AndroidListcast unicast = new AndroidListcast();
			unicast.setAppMasterSecret(UMENG_APPMASTERSECRET);
			unicast.setPredefinedKeyValue("appkey", UMENG_APPKEY);
			unicast.setPredefinedKeyValue("timestamp", System.currentTimeMillis() + "");
			unicast.setPredefinedKeyValue("device_tokens", deviceToken);
			unicast.setPredefinedKeyValue("ticker", Putil.getString(message.get("content")));
			unicast.setPredefinedKeyValue("title", Putil.getString(message.get("title")));
			unicast.setPredefinedKeyValue("text", Putil.getString(message.get("content")));
			unicast.setPredefinedKeyValue("after_open", "go_app");
			unicast.setPredefinedKeyValue("display_type", "notification");
//			unicast.setPredefinedKeyValue("after_open", "go_custom");
//			unicast.setPredefinedKeyValue("display_type", "message");
//			unicast.setPredefinedKeyValue("custom", "{\"type\":1, \"json\": " + JsonUtils.toJson(message) + "}");
			unicast.setPredefinedKeyValue("play_vibrate", "true");
			unicast.setPredefinedKeyValue("play_lights", "true");
			unicast.setPredefinedKeyValue("play_sound", "true");
			unicast.setPredefinedKeyValue("production_mode", UMENG_production_mode);
			result = unicast.send();
		} catch(Exception e) {
			Putil.printLog(e.toString());
		}
		return result;
	}
}
