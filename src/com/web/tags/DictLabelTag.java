package com.web.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.web.util.QueryUtils;

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
		Map<String, Object> result = QueryUtils.queryObject(table, path, value);
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

}
