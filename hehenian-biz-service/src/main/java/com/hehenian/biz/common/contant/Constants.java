package com.hehenian.biz.common.contant;

public class Constants {

    /**
     * 用Mybatis做翻页时，需要传一个KEY="page"的翻页对象
     */
	public static final String  MYBATIS_PAGE = "page";
    
    /** 应答返回码 000-代表交易成功 */
	public static final String RESP_CODE_SUCCESS = "000";
	
	

	/** 资金托管银行或者第三方 */
	public static final String CHINAPNR = "chinapnr";// 汇付
	public static final String PINAAN = "pingan";// 平安
	
	
    public static final int BORROW_STATUS_FULL = 3;
    public static final int BORROW_STATUS_NOT_FULL = 2;
    public static final int BORROW_STATUS_DISCARD = 6;
    
}
