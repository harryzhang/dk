/**  
 * @Project: hehenian-lend
 * @Package com.hehenian.web.common.interceptor
 * @Title: PermissionInterceptor.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月22日 上午11:53:50
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.login.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限拦截器
 * 
 * @author: liuzgmf
 * @date 2015年1月22日 上午11:53:50
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年5月27日 下午5:34:27
 * @Project hehenian-lend-login
 * @Package com.hehenian.login.common.interceptor 
 * @File PermissionInterceptor.java
*/
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

}
