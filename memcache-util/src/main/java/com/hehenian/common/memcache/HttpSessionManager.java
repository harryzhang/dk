package com.hehenian.common.memcache;

import javax.servlet.http.HttpSession;

public interface HttpSessionManager {

	public HttpSession getSession(String sessionId);

	public boolean exists(String sessionId);

	public HttpSession newSession(String sessionId);

	public void updateSession(HttpSession session);

	public String getSessionKey();

	public int getSessionTimeout();

	public String getCookieDomain();

	public String getCookiePath();
}
