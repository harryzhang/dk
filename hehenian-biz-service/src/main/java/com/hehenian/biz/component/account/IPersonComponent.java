package com.hehenian.biz.component.account;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.PersonDo;

public interface IPersonComponent {

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
     * @date: 2014年11月21日上午10:43:35
     */
    List<PersonDo> queryByUserIds(List<Long> userIdList);


    int savePerson(PersonDo personDo);
    
    /**
     * 根据用户姓名和身份证查询用户ID
     * @param paramMap
     * @return
     */
    Long getIdByRealnameAndIdNum(Map<String,Object> paramMap);

    /**
     * 根据用户ID修改person手机号码
     * @param userId
     * @param mobilePhone
     * @return
     */
    int updateMobileByUserId(Long userId, String mobilePhone);
    
    /**
     * 根据用户ID修改person邮箱
     * @param userId
     * @param email
     * @return
     */
    int updateEmailByUserId(Long userId, String email);
    
    int updatePersonByUserId(PersonDo person);
}
