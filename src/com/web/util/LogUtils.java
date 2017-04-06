package com.web.util;

import com.web.db.DbUtils;

public class LogUtils {


	private LogUtils() {
		// 私有实例防止生成实例
	}

	public static void addLog(String type, String note, String linkid) {
		DbUtils.save("insert into an_syslog (type,linkid,note,createdate) values ("
				+ " " + type 
				+ "," + linkid +""
				+ ",'" + note + "'"
				+ ",SYSDATE()"
				+ ")"
				);

	}


}