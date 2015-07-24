package com.hehenian.common.session.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.DisposableBean;

public class MemcachedSpyCache implements SessionCache,
		DisposableBean {
	private MemcachedClient client;

	@SuppressWarnings("unchecked")
	public HashMap<String, Serializable> getSession(String root) {
		return (HashMap<String, Serializable>) client.get(root);
	}

	public void setSession(String root, Map<String, Serializable> session,
			int exp) {
		client.set(root, exp * 60, session);
	}

	public Serializable getAttribute(String root, String name) {
		HashMap<String, Serializable> session = getSession(root);
		return session != null ? session.get(name) : null;
	}

	public void setAttribute(String root, String name, Serializable value,
			int exp) {
		HashMap<String, Serializable> session = getSession(root);
		if (session == null) {
			session = new HashMap<String, Serializable>();
		}
		session.put(name, value);
		client.set(root, exp * 60, session);
	}

	public void clear(String root) {
		client.delete(root);
	}

	public boolean exist(String root) {
		return client.get(root) != null;
	}


	public void destroy() throws Exception {
		client.shutdown();
	}

	public void setClient(MemcachedClient client) {
		this.client = client;
	}
	
}
