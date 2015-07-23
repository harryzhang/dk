package com.hehenian.biz.common.account;

import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.dao.BaseDao;

/**
 * User: liuwtmf
 * Date: 2014/11/26
 * Time: 9:58
 */
public interface IPersonService extends BaseDao<PersonDo>{
    PersonDo getByUserId(Long userId);

    /**
     * @Description: 根据用户身份证号获取 t_person(基本信息的个人信息)
     * @param idNo 身份证号
     * @return
     * @author: zhanbmf
     * @date 2015-3-17 下午6:29:51
     */
    PersonDo getByUserIdNo(String idNo);
    
    /**
     * @Description: 根据手机号获取 t_person(基本信息的个人信息)
     * @param cellPhone
     * @return
     * @author: zhanbmf
     * @date 2015-3-17 下午6:29:54
     */
    PersonDo getByUserCellPhone(String cellPhone);
    /**
     * 修改person
     * @param personDo
     * @return
     * @author: chenzhpmf
     * @date 2015-4-4 下午12:59:52
     */
    int updatePerson(PersonDo personDo);
}
