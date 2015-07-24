package com.hehenian.manager.modules.sys.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 扩展UserDetails
 */
public class UserInfos extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3690162590096755072L;
	
	private int userId;
	
	private String salt;
	
	private String nickName;
	
	public UserInfos(int userId,String username,String nickName, String password,String salt, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.userId=userId;
		this.salt=salt;
		this.nickName=nickName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	

}
