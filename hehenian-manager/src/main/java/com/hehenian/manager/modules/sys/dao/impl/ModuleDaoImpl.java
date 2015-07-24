package com.hehenian.manager.modules.sys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.dao.ModuleDao;
import com.hehenian.manager.modules.sys.model.Module;
import com.hehenian.manager.modules.sys.model.Resources;

@Repository("moduleDao")
public class ModuleDaoImpl implements ModuleDao, InitializingBean {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	private Map<String,Module> moduleMap;
	
	@Override
	public int insertModule(Module module) {
		String sql = "insert into Module(module,name,moduleDesc,createTime) values(?,?,?,now())";
		int ret=userNameJdbcTemplate.getJdbcOperations().update(
				sql,
				new Object[] { module.getModule(), module.getName(),
						module.getModuleDesc() });
		//重新加载所有模块
		reload();
		return ret;
	}

	@Override
	public int updateModule(Module module) {
		StringBuilder sql=new StringBuilder("update Module set ");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotBlank(module.getModule())){
			sql.append(" module=? ,");
			args.add(module.getModule());
		}
		if(StringUtils.isNotBlank(module.getName())){
			sql.append(" name=? ,");
			args.add(module.getName());
		}
		if(StringUtils.isNotBlank(module.getModuleDesc())){
			sql.append(" moduleDesc=?,");
			args.add(module.getModuleDesc());
		}
		sql.setLength(sql.length()-1);
		sql.append(" where id=").append(module.getId());
		int ret=userNameJdbcTemplate.getJdbcOperations().update(sql.toString(), args.toArray());
		//重新加载所有模块
		reload();
		return ret;
	}

	@Override
	public int deleteModule(int id) {
		String sql = "delete from Module where id = ?";
		int ret=userNameJdbcTemplate.getJdbcOperations().update(sql,
				new Object[] { id });
		//重新加载
		reload();
		return ret;
	}

	@Override
	public List<Module> getUserModules(int userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.id RID,r.name NAME,r.resourceStr RESOURCESTR,r.module MODULE,r.icon ICON FROM Resources r ")
				.append("INNER JOIN AuthorityResources ar ")
				.append("ON r.id=ar.resourceId and r.resoucetype='menu' ")
				.append("INNER JOIN RolesAuthority ra ")
				.append("ON ra.authorityId=ar.authorityId ")
				.append("INNER JOIN UsersRoles ur ")
				.append("ON ra.roleId=ur.roleId WHERE userId=?");
		List<Map<String,Object>> datas=userNameJdbcTemplate.getJdbcOperations().queryForList(sql.toString(), userId);
		Map<String,Module> userModules=new HashMap<String,Module>();
		Map<Integer,Boolean> resourcesMap=new HashMap<Integer,Boolean>();
		for(Map<String,Object> data:datas){
			String module=data.get("MODULE").toString();
			Module m=userModules.get(module);
			if(m==null){
				m=moduleMap.get(module);
				if(m==null){
					return new ArrayList<Module>();
				}
				try {
					m=m.clone();
					userModules.put(module, m);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			if(m==null){
				continue;
			}
			if("SYS".equals(module)){
				m.setOrder(-1);
			}
			Integer resourceId=Integer.parseInt(data.get("RID").toString());
			if(resourcesMap.get(resourceId)!=null){
				continue;
			}
			resourcesMap.put(resourceId, true);
			Resources r=new Resources();
			r.setId(resourceId);
			r.setName(data.get("NAME").toString());
			String url=data.get("RESOURCESTR").toString();
			r.setResourceStr(url);
			r.setModule(module);
			r.setIcon(data.get("ICON").toString());
			if("/menuAdmin/userAdmin.html".equals(url)){
				r.setOrder(1);
			}else if("/menuAdmin/rolesIndex.html".equals(url)){
				r.setOrder(2);
			}else if("/authority/authorityIndex.html".equals(url)){
				r.setOrder(3);
			}else{
				r.setOrder(999);
			}
			m.getResources().add(r);
		}
		return new ArrayList<Module>(userModules.values());
	}

//	
//	@Resource
//	public void setVerifyR0NameJdbcTemplate(
//			NamedParameterJdbcTemplate verifyR0NameJdbcTemplate) {
//		this.userNameJdbcTemplate = verifyR0NameJdbcTemplate;
//		reload();
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
	
	@Override
	public Pagination<Module> getAllModules(Pagination<Module> page) {
		page.setRows(new ArrayList<Module>(moduleMap.values()));
		return page;
	}

	private void reload(){
		if(moduleMap!=null){
			moduleMap.clear();
		}else{
			moduleMap=new HashMap<String,Module>();
		}
		String sql="select id,module,name,moduleDesc from Module ";
		List<Map<String,Object>> datas=userNameJdbcTemplate.getJdbcOperations().queryForList(sql);
		for(Map<String,Object> data:datas){
			Module module=new Module();
			int id=Integer.parseInt(data.get("id").toString());
			String moduleStr=data.get("module").toString();
			module.setId(id);
			module.setName(data.get("name").toString());
			module.setModule(moduleStr);
			Object desc=data.get("moduleDesc");
			module.setModuleDesc(desc==null?"":desc.toString());
			moduleMap.put(moduleStr, module);
		}
	}

	@Override
	public List<Module> getAllModules() {
		return new ArrayList<Module>(moduleMap.values());
	}

	@Override
	public Module getModuleById(int moduleId) {
		String sql = "select id,module,name,moduleDesc,createTime from Module where id=?";
		return userNameJdbcTemplate.getJdbcOperations().queryForObject(sql,new Object[]{moduleId},new RowMapper<Module>(){

			@Override
			public Module mapRow(ResultSet rs, int arg1) throws SQLException {
				Module module=new Module();
				module.setId(rs.getInt("id"));
				module.setModule(rs.getString("module"));
				module.setName(rs.getString("name"));
				module.setModuleDesc(rs.getString("moduleDesc"));
				module.setCreateTime(rs.getDate("createTime"));
				return module;
			}
			
		});
	}
	
	
}
