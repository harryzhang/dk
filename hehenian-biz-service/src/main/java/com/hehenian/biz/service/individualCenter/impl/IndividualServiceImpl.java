package com.hehenian.biz.service.individualCenter.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.individualCenter.IIndividualService;
import com.hehenian.biz.dal.individualCenter.impl.UserDao;

/**
 * 
 * @author Administrator
 * 
 */
@Service("individualService")
public class IndividualServiceImpl extends BaseService implements IIndividualService{

    /*@Autowired
    public UserDao userDao;*/
    
    public static Log log = LogFactory.getLog(IndividualServiceImpl.class);
    
    


	/**
	 * 用户基本信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonById(long id) throws Exception {
		/*Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPersonById(conn, id);
			Map<String, String> map1 = userDao.queryUserById(conn, id);
			if (map1 != null) {
				if (map == null)
					map = new HashMap<String, String>();
				map.put("username", map1.get("username"));
				map.put("refferee", map1.get("refferee"));
				map.put("source", map1.get("source"));
				map.put("email", map1.get("email"));
				map.put("usrCustId", map1.get("usrCustId"));
				map.put("mobilePhone", map1.get("mobilePhone"));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}*/
		return null;
	}

}



