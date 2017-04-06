package com.web.util;

import java.io.Reader;
import java.sql.Clob;

public class ClobUtils {

	public static int buffer = 4096;
	
	public static String readClob(Object clob) throws Exception {
		if (clob == null) {
			return "";
		}
		StringBuffer clobString = new StringBuffer();
		if (clob instanceof Clob) {
			int y;
			char ac[] = new char[buffer];
			Reader reader = ((Clob) clob).getCharacterStream();
			while ((y = reader.read(ac, 0, buffer)) != -1) {
				clobString.append(new String(ac, 0, y));
			}
		} else {
			clobString.append(clob.toString());
		}
		return clobString.toString();
	}
}
