package com.hehenian.common.memcache;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

public class HttpSessionResponseWrapper extends HttpServletResponseWrapper {

	private HttpSession session;
	private HttpSessionManager sessionManager;
	private final boolean sessionFromCookie;

	public HttpSessionResponseWrapper(HttpServletResponse response, HttpSession session,
			HttpSessionManager sessionManager, boolean sessionFromCookie) {
		super(response);
		this.session = session;
		this.sessionManager = sessionManager;
		this.sessionFromCookie = sessionFromCookie;
	}

	@Override
	public String encodeRedirectUrl(String url) {
		return encodeRedirectURL(url);
	}

	@Override
	public String encodeRedirectURL(String url) {
		return encodeURL(url);
	}

	@Override
	public String encodeUrl(String url) {
		return encodeURL(url);
	}

	@Override
	public String encodeURL(String url) {
		if (sessionFromCookie) {
			return url;
		}
		StringBuilder sb = new StringBuilder();
		// standard url rewriting
		int p = url.indexOf('?');
		if (p == 0) {
			sb.append(url);
		} else if (p > 0) {
			sb.append(url, 0, p);
			sb.append(';');
			sb.append(sessionManager.getSessionKey());
			sb.append('=');
			sb.append(session.getId());
			sb.append(url, p, url.length());
		} else if ((p = url.indexOf('#')) >= 0) {
			sb.append(url, 0, p);
			sb.append(';');
			sb.append(sessionManager.getSessionKey());
			sb.append('=');
			sb.append(session.getId());
			sb.append(url, p, url.length());
		} else {
			sb.append(url);
			sb.append(';');
			sb.append(sessionManager.getSessionKey());
			sb.append('=');
			sb.append(session.getId());
		}
		return sb.toString();
	}
}
