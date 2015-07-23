/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.util
 * @Title: RedisUtil.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年12月15日 上午11:07:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 *//*
package com.hehenian.biz.common.util;


import com.hehenian.biz.common.account.dataobject.AccountUserDo;

*//**
 * 
 * @author: zhangyunhmf
 * @date 2014年12月15日 上午11:07:53
 *//*
public class RedisUtil {

    private static final Jedis jedis = new Jedis("10.111.0.202");

    public static void main(String[] args) throws Throwable {
        //setObject();
        //getObject();

        Object o = getObject("0CC4FE522C4DD81C57627EFFFB73FC4D_user");

        System.out.println(((AccountUserDo) o).getUsername());
    }

    public static void setObject(String key, Object o) throws Throwable {
        byte[] objBytes = SerializableUtils.toByteArray(o);
        jedis.set(key.getBytes(), objBytes);
    }

    public static Object getObject(String key) throws Throwable {
        byte[] objBytes = jedis.get(key.getBytes());
        return SerializableUtils.fromByteArray(objBytes);
    }
}
*/