package com.hehenian.biz.dal.account;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.PersonDo;

import org.apache.ibatis.annotations.Param;

public interface IPersonDao {

    /**
     * 根据用户ID查询用户个人信息
     * 
     * @param userId
     * @return
     */
    PersonDo getByUserId(Long userId);


    /**
     * 根据用户ID查询用户明细信息
     * 
     * @param userIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日上午10:44:44
     */
    List<PersonDo> queryByUserIds(List<Long> userIdList);


    int savePerson(PersonDo personDo);

    int saveWorkauth(@Param("userId") long userId);
    
    /**
     * 修改个人信息
     * @param personDo
     * @return
     */
    int updatePerson(PersonDo personDo);

    /**
     * 根据用户ID修改个人信息
     * @param personDo
     * @return
     */
    int updatePersonByUserId(PersonDo personDo);

    /**
     * 根据用户ID修改person手机号码
     * @param userId
     * @param mobilePhone
     * @return
     */
    int updatePhoneByUserId(@Param("userId") Long userId, @Param("mobilePhone") String mobilePhone);
    
    /**
     * 根据用户ID修改person邮箱
     * @param userId
     * @param mobilePhone
     * @return
     */
	int updateEmailByUserId(@Param("userId") Long userId, @Param("email") String email);
    
    /**
     * 根据用户姓名和身份证查询用户ID
     * @param paramMap
     * @return
     */
    Long getIdByRealnameAndIdNum(Map<String,Object> paramMap);
}
