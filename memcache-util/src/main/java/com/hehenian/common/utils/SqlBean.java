package com.hehenian.common.utils;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class SqlBean {

	private String sql;
	private SqlParameterSource param;
	private SqlParameterSource[] paramArray;

	public SqlBean(String sql, SqlParameterSource param) {
		super();
		this.sql = sql;
		this.param = param;
	}

	public SqlBean(String sql, SqlParameterSource[] paramArray) {
		super();
		this.sql = sql;
		this.paramArray = paramArray;
	}

	public String getSql() {
		return sql;
	}

	public SqlParameterSource getParam() {
		return param;
	}

	public SqlParameterSource[] getParamArray() {
		return paramArray;
	}

}
