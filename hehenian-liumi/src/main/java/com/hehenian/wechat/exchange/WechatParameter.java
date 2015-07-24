package com.hehenian.wechat.exchange;

public class WechatParameter {

	private final static String APPKET="wxeae7b1b55b3a43b0";
	private final static String APPSECRETKEY="68753837e1095566193d526bf0cf867f";
	
	private String token;
	private String grant_type;
	private String appid=APPKET;
	private String secret=APPSECRETKEY;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
