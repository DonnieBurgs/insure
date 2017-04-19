package com.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.web.db.DbUtils;

import oracle.net.aso.e;

/**
 * @author liqi
 */
public class QueryUtils {

	static final LinkedHashMap<String, Map<String, Object>> cache = new LinkedHashMap<>();
	static long queryTime = 0;

	public static Map<String, Object> queryObject(String table, String path, String value) {
		String key = String.format("%s_%s_%s", table, path, value);
		Map<String, Object> result = new HashMap<>();

		if (System.currentTimeMillis() - queryTime > 5000)
			cache.clear();

		if (cache.containsKey(key)) {
			// System.out.println("query for cache");
			result = cache.get(key);
		} else {
			queryTime = System.currentTimeMillis();
			// System.out.println("query for db");
			List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
			resultRows = DbUtils.query("select p.* from " + table + " p");
			for (Map<String, Object> map : resultRows) {
				String l = String.valueOf(map.get(path));
				if (l.equals(value)) {
					cache.put(key, map);
					result = map;
					break;
				}
			}
		}
		return result;
	}

	public static Object query(String table, String path, String value, String name) {
		Map<String, Object> result = QueryUtils.queryObject(table, path, value);
		if (result != null) {
			return result.get(name);
		}
		return null;
	}

	public static String queryTable4Array(String table, String orderBy, String sort) {
		System.out.println(table);
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		String sql = "select p.* from " + table + " p order by p." + orderBy + " " + sort;
		System.out.println(sql);
		resultRows = DbUtils.query(sql);
		if (resultRows != null && !resultRows.isEmpty()) {
			return JSON.toJSONString(resultRows);
		} else {
			return "[]";
		}
	}
}
