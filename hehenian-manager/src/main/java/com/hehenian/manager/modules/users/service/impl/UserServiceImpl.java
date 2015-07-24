package com.hehenian.manager.modules.users.service.impl;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hehenian.manager.commons.Constants;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.dao.AuthorityDao;
import com.hehenian.manager.modules.sys.dao.RolesAuthorityDao;
import com.hehenian.manager.modules.sys.dao.UsersRolesDao;
import com.hehenian.manager.modules.sys.model.RolesAuthority;
import com.hehenian.manager.modules.sys.model.UserInfos;
import com.hehenian.manager.modules.sys.model.UsersRoles;
import com.hehenian.manager.modules.users.dao.RolesDao;
import com.hehenian.manager.modules.users.dao.UserDao;
import com.hehenian.manager.modules.users.model.Managers;
import com.hehenian.manager.modules.users.model.Roles;
import com.hehenian.manager.modules.users.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserDetailsService,UserService {

	@Autowired
	protected UserDao userDao;

	@Autowired
	protected AuthorityDao authorityDao;
	
	@Autowired
	protected RolesDao rolesDao;
	
	@Autowired
	protected UsersRolesDao usersRolesDao;
	
	@Autowired
	protected RolesAuthorityDao rolesAuthorityDao;

	Md5PasswordEncoder encoder=new Md5PasswordEncoder();
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		Managers data = userDao.getUserDetailsByUserName(userName);
		List<GrantedAuthority> authorities = authorityDao
				.getGrantedAuthority(data.getId());
		UserInfos details=new UserInfos(data.getId(),data.getUsername(),data.getNickname(),data.getPassword(),data.getSalt(), true,
				true, true, true, authorities);
		return details;
	}

	/**
	 * 新增用户
	 */
	@Override
	public int addUsers(Managers user) {
		String salt = RandomStringUtils.randomAlphanumeric(6);
		user.setSalt(salt);
		user.setPassword(encoder.encodePassword(user.getPassword()
				.trim(), salt));
		user.setEnabled(true);
		int ret=-1;
		if(user.getId()!=null){
			ret=userDao.updateUser(user);
		}else{
			ret=userDao.addUser(user);
		}
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}
	
	public Managers getUserById(int userId){
		return userDao.getUserById(userId);
	}
	
	/**
	 * 删除用户
	 */
	public int deleteOneUser(int userId){
		int ret=userDao.deleteUser(userId);
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}
	
	@Override
	public Pagination<Managers> getManagersByPage(
			Pagination<Managers> pagination,String username) {
		return userDao.getUsersByPage(pagination, username);
	}

	@Override
	public Pagination<Roles> getRolesByPage(Pagination<Roles> pagination,
			String roleName) {
		return rolesDao.getRolesByPage(pagination, roleName);
	}

	@Override
	public Roles getOneRoleById(int roleId) {
		return rolesDao.getOneRoleById(roleId);
	}

	@Override
	public int deleteOneRole(int roleId) {
		int ret=rolesDao.deleteRole(roleId);
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public int updateOneRole(Roles role) {
		int ret=Constants.FAIL;
		role.setEnabled(true);
		if(role.getId()!=null){
			ret=rolesDao.updateRole(role);
		}else{
			ret=rolesDao.addRole(role);
		}
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public Pagination<Managers> getManagersInOrNotInRoles(
			Pagination<Managers> page, int roleId,boolean flag) {
		return flag?userDao.getUsersInRoles(page, roleId):userDao.getUsersNotInRoles(page, roleId);
	}

	@Override
	public int addUsersRoles(UsersRoles ur) {
		if(usersRolesDao.hasExists(ur)){
			return Constants.FAIL;
		}
		int ret=usersRolesDao.addUsersRoles(ur);
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public int deleteUsersRoles(UsersRoles ur) {
		int ret=usersRolesDao.deleteUsersRoles(ur);
		return ret>0?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public Pagination<Roles> getRolesInOrNotInAuthorities(
			Pagination<Roles> page, int authId,boolean inOrNot) {
		return inOrNot?rolesDao.getRolesInAuthority(page, authId):rolesDao.getRolesNotInAuthority(page, authId);
	}

	@Override
	public int addRolesAuthority(RolesAuthority ra) {
		int ret=rolesAuthorityDao.addRolesAuthority(ra);
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public int deleteRolesAuthority(RolesAuthority ra) {
		int ret=rolesAuthorityDao.deleteRolesAuthority(ra);
		return ret>0?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public int resetCurrentUserPwd(int userId,String oldPassword, String newPassword) {
		if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
			return Constants.FAIL;
		}
		Managers user=userDao.getUserById(userId);
		if(user==null){
			return Constants.FAIL;
		}
		
		String passwordEncode=encoder.encodePassword(oldPassword, user.getSalt());
		if(user.getPassword().equals(passwordEncode)){
			user.setPassword(encoder.encodePassword(newPassword
				.trim(), user.getSalt()));
			int count=userDao.updateUser(user);
			return count>0?Constants.SUCCESS:Constants.FAIL;
		}
		return Constants.INVALID;
	}

}
