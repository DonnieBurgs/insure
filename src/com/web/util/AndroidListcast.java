package com.web.util;

import push.AndroidNotification;

public class AndroidListcast extends AndroidNotification {
	public AndroidListcast() {
		try {
			this.setPredefinedKeyValue("type", "listcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
