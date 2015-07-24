package com.shove.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class DataBaseConfig {
	private static DataBaseConfig instance;
	public static String owner = "";
	public static boolean outputStatementToLogger = false;
	public static String jdbcUrl = "";
	private ComboPooledDataSource ds;

	private DataBaseConfig() throws Exception {
		ds = new ComboPooledDataSource("bbs-config");
	}

	public static final DataBaseConfig getInstance() {
		if (instance == null)
			try {
				instance = new DataBaseConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return instance;
	}

	public final synchronized Connection getConnection() throws SQLException
	{
		return ds.getConnection();
	}

	protected void finalize() throws Throwable {
		DataSources.destroy(ds);
		super.finalize();
	}
}
