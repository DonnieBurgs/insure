package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.Constraints;
import com.web.util.StringUtils;

@SuppressWarnings("serial")
public class BaseHttpServlet extends HttpServlet {

	public boolean code(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String validatorCode = (String) request.getSession().getAttribute("validator_code");
		if (StringUtils.hasLength(code) && StringUtils.hasLength(validatorCode) && code.equals(validatorCode)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void redirect(HttpServletRequest request, HttpServletResponse response, String defaultRedirect) throws IOException {
		HttpSession session = request.getSession();
		String redirect = (String) session.getAttribute(Constraints.REDIRECT);
		String referer = (String) session.getAttribute(Constraints.REFERER);
		session.removeAttribute(Constraints.REDIRECT);
		session.removeAttribute(Constraints.PARAMS);
		session.removeAttribute(Constraints.REFERER);
		if (redirect != null && !redirect.equals("")) {
			Map<String, Object> params = (Map<String, Object>) session.getAttribute(Constraints.PARAMS);
			if (params != null && !params.isEmpty()) {
				redirect += "?";
				Object[] value = null;
				for (String key : params.keySet()) {
					value = (Object[]) params.get(key);
					if (value != null && value.length > 0) {
						redirect += key + "=" + value[0];
					}
				}
			}
			response.sendRedirect(request.getContextPath() + redirect);
		} else if (referer != null && !referer.equals("")) {
			response.sendRedirect(request.getContextPath() + referer);
		} else {
			response.sendRedirect(request.getContextPath() + (defaultRedirect == null ? "/" : defaultRedirect));
		}
	}

	@SuppressWarnings("unchecked")
	public String redirect(HttpServletRequest request, String defaultRedirect) {
		HttpSession session = request.getSession();
		String redirect = (String) session.getAttribute(Constraints.REDIRECT);
		if (redirect != null && !redirect.equals("")) {
			Map<String, Object> params = (Map<String, Object>) session.getAttribute(Constraints.PARAMS);
			if (params != null && !params.isEmpty()) {
				redirect += "?";
				Object[] value = null;
				for (String key : params.keySet()) {
					value = (Object[]) params.get(key);
					if (value != null && value.length > 0) {
						redirect += key + "=" + value[0];
					}
				}
			}
			session.removeAttribute(Constraints.REDIRECT);
			session.removeAttribute(Constraints.PARAMS);
			return redirect;
		}
		String referer = (String) session.getAttribute(Constraints.REFERER);
		if (referer != null && !referer.equals("")) {
			session.removeAttribute(Constraints.REFERER);
			return referer;
		}

		return (defaultRedirect == null ? "/" : defaultRedirect);
	}

	public void redirect(HttpServletResponse response, String uri) {
		try {
			response.sendRedirect(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLastPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String referer = (String) session.getAttribute(Constraints.REFERER);
		if (referer != null && !referer.equals("")) {
			session.removeAttribute(Constraints.REFERER);
			return request.getContextPath() + referer;
		}
		return "/";
	}

	public String getLastPage(HttpServletRequest request, String defaultRedirect) {
		HttpSession session = request.getSession();
		String referer = (String) session.getAttribute(Constraints.REFERER);
		if (referer != null && !referer.equals("")) {
			session.removeAttribute(Constraints.REFERER);
			return request.getContextPath() + referer;
		}
		return defaultRedirect;
	}

	public void flash(HttpServletRequest request, Object o) {
		request.getSession().setAttribute(Constraints.FLASH, o);
	}

	/**
	 * 只弹出提示
	 * 
	 * @param response
	 * @param result
	 */
	public void alert(HttpServletResponse response, String result) {
		String content = "<script type=\"text/javascript\">alert(\"" + result + "\");</script>";
		write(response, content);
	}

	public void prompt(HttpServletResponse response, String result) {
		String content = "<script type=\"text/javascript\">alert(\"" + result + "\"); history.back();</script>";
		write(response, content);
	}

	public void write(HttpServletResponse response, String result) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Charset", "UTF-8");
			PrintWriter out = response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getInt(String value) {
		int i = 0;
		if (value != null) {
			try {
				i = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	protected final static String INFO = "info";
	protected final static String SUCCESS = "success";
	protected final static String CODE = "code";
}
