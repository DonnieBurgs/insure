package com.web.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {

	final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	final static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.getSerializationConfig().setDateFormat(formatter);
	}

	public static String toJson(Object o) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getSerializationConfig().setDateFormat(formatter);
		try {
			StringWriter writer = new StringWriter();
			objectMapper.writeValue(writer, o);
			return writer.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void write(Object data, int ret, int code, String msg, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ret", ret);
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", data);
		JsonUtils.writeJson(result, response);

	}
	
	public static void writeJson(Object o, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Charset", "UTF-8");
		try {
			objectMapper.writeValue(response.getWriter(), o);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
