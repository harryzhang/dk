package com.hehenian.manager.commons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class PageMapper<T> {
	
	public abstract T toCustomizedBean(ResultSet rs)throws SQLException;
	
	public Pagination<T> getPage(ResultSet rs,Pagination<T> page) throws SQLException{
		List<T> results=new ArrayList<T>();
		while(rs.next()){
			results.add(toCustomizedBean(rs));
		}
		page.setRows(results);
		return page;
	}
}
