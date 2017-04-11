package com.web.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.web.db.DbUtils;

public class DictLabelTag extends SimpleTagSupport {

	private String table;
	private String path;
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		String key = String.format("%s_%s_%s", table, path, value);
		Map<String, Object> result = null;

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
				if (l.equals(this.getValue())) {
					cache.put(key, map);
					result = map;
					break;
				}
			}
		}
		if (result != null) {
			JspWriter out = getJspContext().getOut();
			StringBuffer sb = new StringBuffer();
			try {
				sb.append(String.valueOf(result.get(name)));
				out.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new JspException(e);
			}
		}
		super.doTag();
	}

	static final LinkedHashMap<String, Map<String, Object>> cache = new LinkedHashMap<>();
	static long queryTime = 0;
}
