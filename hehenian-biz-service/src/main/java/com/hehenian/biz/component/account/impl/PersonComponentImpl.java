package com.hehenian.biz.component.account.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.dal.account.IPersonDao;

@Service("personComponent")
public class PersonComponentImpl implements IPersonComponent {
    @Autowired
    private IPersonDao personDao;

    @Override
    public PersonDo getByUserId(Long userId) {
        return personDao.getByUserId(userId);
    }


    @Override
    public List<PersonDo> queryByUserIds(List<Long> userIdList) {
        return personDao.queryByUserIds(userIdList);
    }


    @Override public int savePerson(PersonDo personDo) {
        return personDao.savePerson(personDo);
    }

    /**
     * 根据用户姓名和身份证查询用户ID
     * @param paramMap
     * @return
     */
    @Override
    public Long getIdByRealnameAndIdNum(Map<String,Object> paramMap){
    	return personDao.getIdByRealnameAndIdNum(paramMap);
    }
	@Override
	public int updateMobileByUserId(Long userId, String mobilePhone) {
		return this.personDao.updatePhoneByUserId(userId, mobilePhone);
	}


	@Override
	public int updateEmailByUserId(Long userId, String email) {
		return this.personDao.updateEmailByUserId(userId, email);
	}

	@Override
	public int updatePersonByUserId(PersonDo person) {
		return personDao.updatePersonByUserId(person);
	}

}
