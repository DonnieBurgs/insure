package com.web.tags;

import java.io.IOException;
import java.util.ArrayList;
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
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		resultRows = DbUtils.query("select p.* from " + table + " p");
		JspWriter out = getJspContext().getOut();
		StringBuffer sb = new StringBuffer();
//		System.out.println(name);
//		System.out.println(path);
		for (Map<String, Object> map : resultRows) {
			String n = String.valueOf(map.get(name));
			String l = String.valueOf(map.get(path));
//			System.out.println("aaaaaaa" + name);
//			System.out.println("bbbb" + path);
//			System.out.println("ccccccc" + n);
//			System.out.println("dddd" + l);
//			System.out.println("fffffff" + this.getValue());
			if (l.equals(this.getValue())) {
				sb.append(n);
				break;
			}
		}
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new JspException(e);
		}
		super.doTag();
	}
}
