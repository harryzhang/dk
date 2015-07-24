package com.hehenian.common.memcache;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

public class MemcachedSessionManager implements HttpSessionManager, ServletContextAware,
		InitializingBean {

	private MemcachedClient memcachedClient;
	private String memcachedServers;

	private ServletContext servletContext;

	public String sessionKey = "sid";
	private int sessionTimeout = 60 * 10;
	public String cookieDomain = ".hehenian.com";
	public String cookiePath = "/";

	public MemcachedHttpSession newSession(String sessionId) {
		MemcachedHttpSession session = new MemcachedHttpSession(sessionId, memcachedClient);
		session.setServletContext(servletContext);
		return session;
	}

	public MemcachedHttpSession getSession(String sessionId) {
		if (StringUtils.isEmpty(sessionId)) {
			return null;
		}
		MemcachedClient client = this.memcachedClient;

		Object object = client.get(sessionId);
		if (object == null) {
			return null;
		}
		MemcachedHttpSession session = newSession(sessionId);
		if (object instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, Object> sessionMap = (Map<String, Object>) object;
			session.getValueMap().putAll(sessionMap);
		}
		return session;
	}

	
	public boolean exists(String sessionId) {
		if (StringUtils.isEmpty(sessionId)) {
			return false;
		}
		return memcachedClient.get(sessionId) != null;
	}

	
	public void updateSession(HttpSession session) {
		MemcachedHttpSession s = (MemcachedHttpSession) session;
		if (s.isUpdated() == false) {
			s.update();
		}
	}

	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	
	public void afterPropertiesSet() throws Exception {
		BinaryConnectionFactory cf = new BinaryConnectionFactory();
		memcachedClient = new MemcachedClient(cf, AddrUtil.getAddresses(memcachedServers));
	}

	public void setMemcachedServers(String memcachedServers) {
		this.memcachedServers = memcachedServers;
	}

	
	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {

        this.sessionKey = sessionKey;
	}

	
	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	
	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	
	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}
}
