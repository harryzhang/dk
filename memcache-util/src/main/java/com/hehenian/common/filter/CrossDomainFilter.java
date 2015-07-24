package com.hehenian.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = -193710108167841968L;

	public CrossDomainFilter() {
		super();
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("P3P", "CP=CAO PSA OUR");
		
		if (chain != null) {
			chain.doFilter(request, res);
		}
	}

	public void destroy() {

	}

}
