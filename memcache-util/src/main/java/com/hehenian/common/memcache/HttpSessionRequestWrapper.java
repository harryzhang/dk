package com.hehenian.common.memcache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class HttpSessionRequestWrapper extends HttpServletRequestWrapper {

	private HttpSession session;

	public HttpSessionRequestWrapper(HttpServletRequest request, HttpSession session) {
		super(request);
		this.session = session;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return session;
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}
}
