/**  
 * @Project: memcache-util
 * @Package com.hehenian.common.annotations
 * @Title: RequireLogin.java
 * @Description: 是否要登录
 *
 * @author: zhanbmf
 * @date 2015-3-28 下午6:26:17
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ( { ElementType.METHOD, ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {
	
	/**
	 * 是否要登录(默认为true,|true需要登录|false不需要登录)
	 * @return
	 */
	boolean value() default true;
	
	/**
	 * @Description: 是否需要注入
	 * @return
	 * @author: zhanbmf
	 * @date 2015-3-28 下午6:26:45
	 */
	boolean injectPersonDo() default false;
}

