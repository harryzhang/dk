package com.hehenian.common.memcache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

public class MemcachedHttpSession implements HttpSession, Serializable {

	private static final long serialVersionUID = 6085773901132571541L;

	private final MemcachedClient memcachedClient;

	private final String sessionId;

	private final Map<String, Object> sessionValueMap = new HashMap<String, Object>();

	private int maxInactiveInterval;

	private ServletContext servletContext;

	private boolean updated = false;

	private boolean invalidated = false;

	public MemcachedHttpSession(String sessionId, MemcachedClient memcachedClient) {
		this.sessionId = sessionId;
		this.memcachedClient = memcachedClient;
		memcachedClient.get(sessionId);
	}

	protected void update() {
		if (invalidated) {
			return;
		}
		if (sessionValueMap.size() == 0) {
			// �ջỰ����Ҫ���кţ��������
			memcachedClient.set(sessionId, getMaxInactiveInterval(), 0);
		} else {
			memcachedClient.set(sessionId, getMaxInactiveInterval(), sessionValueMap);
		}
		updated = true;
	}

	protected Map<String, Object> getValueMap() {
		return this.sessionValueMap;
	}

	public Object getAttribute(String key) {
		return this.sessionValueMap.get(key);
	}

	public Enumeration<String> getAttributeNames() {
		return (new Enumerator<String>(this.sessionValueMap.keySet(), true));
	}

	public void invalidate() {
		memcachedClient.delete(sessionId);
		updated = true;
		invalidated = true;
	}

	public void removeAttribute(String key) {
		sessionValueMap.remove(key);
		update();
	}

	public void setAttribute(String key, Object value) {
		sessionValueMap.put(key, value);
		update();
	}

	
	public long getCreationTime() {
		return 0;
	}

	
	public String getId() {
		return sessionId;
	}

	
	public long getLastAccessedTime() {
		return 0;
	}

	
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Deprecated
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return null;
	}

	
	@Deprecated
	public Object getValue(String key) {
		return getAttribute(key);
	}

	
	@Deprecated
	public String[] getValueNames() {
		Set<String> set = sessionValueMap.keySet();
		return set.toArray(new String[set.size()]);
	}

	
	public boolean isNew() {
		return false;
	}

	
	@Deprecated
	public void putValue(String key, Object value) {
		setAttribute(key, value);
	}

	
	@Deprecated
	public void removeValue(String key) {
		removeAttribute(key);
	}

	
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}

class Enumerator<E> implements Enumeration<E> {

	public Enumerator(Collection<E> collection) {
		this(collection.iterator());
	}

	public Enumerator(Collection<E> collection, boolean clone) {
		this(collection.iterator(), clone);
	}

	public Enumerator(Iterator<E> iterator) {
		super();
		this.iterator = iterator;
	}

	public Enumerator(Iterator<E> iterator, boolean clone) {
		super();
		if (!clone) {
			this.iterator = iterator;
		} else {
			List<E> list = new ArrayList<E>();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			this.iterator = list.iterator();
		}
	}

	public Enumerator(Map<?, E> map) {
		this(map.values().iterator());

	}

	public Enumerator(Map<?, E> map, boolean clone) {
		this(map.values().iterator(), clone);
	}

	private Iterator<E> iterator = null;

	public boolean hasMoreElements() {
		return (iterator.hasNext());
	}

	public E nextElement() throws NoSuchElementException {
		return (iterator.next());
	}
}