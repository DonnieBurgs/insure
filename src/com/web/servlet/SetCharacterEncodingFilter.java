package com.web.servlet;

import java.io.IOException;
import javax.servlet.*;

public class SetCharacterEncodingFilter
    implements Filter
{

    public SetCharacterEncodingFilter()
    {
        encoding = null;
        filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        if(encoding != null)
            request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        this.filterConfig = filterConfig;
        encoding = filterConfig.getInitParameter("encoding");
    }

    public void destroy()
    {
        encoding = null;
        filterConfig = null;
    }

    protected String encoding;
    protected FilterConfig filterConfig;
}
