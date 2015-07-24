package com.hehenian.manager.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public  abstract class PaginationCallback<T> {

	public abstract T toCustomizedBean(Map<String,Object> data);
	
	public Pagination<T> getPage(List<Map<String,Object>> datas,Pagination<T> page){
		int size=datas.size();
		List<T> results=new ArrayList<T>(size);
		for(int i=0;i<size;i++){
			Map<String,Object> data=datas.get(i);
			results.add(toCustomizedBean(data));
		}
		page.setRows(results);
		return page;
	}
}
