package com.hehenian.biz.service.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

/**
 * User: liuwtmf
 * Date: 2014/12/10
 * Time: 10:31
 */
@Service("personService")
public class PersonServiceImpl extends AbstractBaseDaoImpl<PersonDo> implements IPersonService {
    @Autowired
    private IPersonComponent personComponent;
    
    @Resource
	private JdbcTemplate sp2pJdbcTemplate;
    
    private static RowMapper<PersonDo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PersonDo.class);
    
    @Override public PersonDo getByUserId(Long userId) {
        return personComponent.getByUserId(userId);
    }
    
	@Override
	public PersonDo getByUserIdNo(String idNo) {
		String sql = "select * from t_person where idNo=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new String[] {idNo}, rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public PersonDo getByUserCellPhone(String cellPhone) {
		String sql = "select * from t_person where cellPhone=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new String[] {cellPhone}, rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public RowMapper<PersonDo> getRowMapper() {
		return new RowMapper<PersonDo>() {
			@Override
			public PersonDo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PersonDo pd = new PersonDo();
				//to do...
				return null;
			}
		};
	}

	@Override
	public int updatePerson(PersonDo personDo) {
		String sql = "update t_person set realName=?,idNo=?,idNoStatus=?,auditStatus=? where id=? limit 1";
		return sp2pJdbcTemplate.update(sql, personDo.getRealName(),personDo.getIdNo(),personDo.getIdNoStatus(),personDo.getAuditStatus(),personDo.getId());
	}


}
