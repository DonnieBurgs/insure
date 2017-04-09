package com.web.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.web.db.DbUtils;
import com.web.util.StringUtils;

public class DictSelectTag extends SimpleTagSupport {

	private String table;
	private String path;
	private boolean defaultValue;
	private String label;
	private String name;
	private String value;
	private String id;
	private String cssClass;
	private String styleClass;
	private String multiple;
	private String onChange;

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

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return StringUtils.hasLength(id) ? id : path;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	@Override
	public void doTag() throws JspException, IOException {
		List<Map<String, Object>> resultRows = new ArrayList<Map<String, Object>>();
		resultRows = DbUtils.query("select p.* from " + table + " p");
		JspWriter out = getJspContext().getOut();
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='" + this.getPath() + "' id='" + this.getId() + "'");
		if (StringUtils.hasLength(this.getCssClass())) {
			sb.append("class=\"" + this.getCssClass() + "\" ");
		}
		if (StringUtils.hasLength(this.getStyleClass())) {
			sb.append("style=\"" + this.getStyleClass() + "\" ");
		}
		if (StringUtils.hasLength(this.getMultiple())) {
			sb.append("multiple=\"" + this.getMultiple() + "\" ");
		}
		if (StringUtils.hasLength(this.getOnChange())) {
			sb.append("onchange=\"" + this.getOnChange() + "\" ");
		}
		sb.append(">");
		if (this.isDefaultValue()) {
			sb.append("<option value=''>--请选择--</option>");
		}
		for (Map<String, Object> map : resultRows) {
//			for (String k : map.keySet()) {
//				System.out.println(k + "    " + map.get(k));
//			}
			String n = String.valueOf(map.get(name));
			String l = String.valueOf(map.get(label));
			if (n.equals(this.getValue())) {
				sb.append("<option value='" + n + "' selected='selected'>");
			} else {
				sb.append("<option value='" + n + "'>");
			}
			sb.append(l + "</option>");
		}
		sb.append("</select>");
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.doTag();
	}

}
