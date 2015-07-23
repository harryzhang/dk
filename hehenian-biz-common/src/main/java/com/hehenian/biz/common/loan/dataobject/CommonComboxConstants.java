package com.hehenian.biz.common.loan.dataobject;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共定义combox类
 * @author liminglmf
 *
 */
public class CommonComboxConstants {

	private String key ;
	
	private String value ;
	
	public CommonComboxConstants(){};
	
	public CommonComboxConstants(String key,String value){
		this.key = key ;
		this.value = value ;
	}
	
    public static List<CommonComboxConstants> getStatusList(){
    	List<CommonComboxConstants> list = new ArrayList<CommonComboxConstants>();
    	list.add(new CommonComboxConstants("T", "有效"));
    	list.add(new CommonComboxConstants("F", "失效"));
    	return list;
    }
    
    public static List<CommonComboxConstants> getYesNoList(){
    	List<CommonComboxConstants> list = new ArrayList<CommonComboxConstants>();
    	list.add(new CommonComboxConstants("0", "否"));
    	list.add(new CommonComboxConstants("1", "是"));
    	return list;
    }
	
    public enum Status {
	       T,F
	    }
    
	public static String getCommonStatus(String status){
    	if("T".equals(status)){
    		return "有效";
    	}
    	if("F".equals(status)){
    		return "失效";
    	}
    	return null;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
