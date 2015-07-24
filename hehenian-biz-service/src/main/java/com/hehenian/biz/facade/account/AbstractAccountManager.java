/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.account
 * @Title: AccountManagerUtils.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月13日 上午9:28:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.account;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import chinapnr.SecureLink;

import com.hehenian.biz.common.contant.Constants;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.facade.account.chinapnr.ChinaPnrConfig;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * 账户管理服务抽象类
 * 
 * @author: liuzgmf
 * @date 2014年10月13日 上午9:28:14
 */
public abstract class AbstractAccountManager {
    private final Logger          logger          = Logger.getLogger(this.getClass());
    protected final static String DEFAULT_CHARSET = "UTF-8";                          // 默认字符集
    @Autowired
    protected ChinaPnrConfig      chinaPnrConfig;

    /**
     * 获取执行错误或者失败时的输出参数对象
     * 
     * @param errorMessage
     * @return
     */
    protected OutParameter getFailureOutParameter(String errorMessage) {
        OutParameter outParameter = new OutParameter();
        outParameter.setSuccess(false);
        outParameter.setRespDesc(errorMessage);
        return outParameter;
    }

    /**
     * 获取成功返回时的输出参数对象
     * 
     * @param json
     * @return
     */
    protected OutParameter getSuccessOutParameter(String jsonString) {
        OutParameter outParameter = new OutParameter();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> params = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {
            });
            String respCode = (String) params.get("RespCode");
            boolean success = Constants.RESP_CODE_SUCCESS.equals(respCode) ? true : false;
            outParameter.setSuccess(success);
            outParameter.setRespCode((String) params.get("RespCode"));
            outParameter.setRespDesc(URLDecoder.decode((String) params.get("RespDesc"), DEFAULT_CHARSET));
            outParameter.setTrxId((String) params.get("TrxId"));
            outParameter.setRetUrl((String) params.get("RetUrl"));
            outParameter.setBgRetUrl((String) params.get("BgRetUrl"));
            outParameter.setParams(params);
            return outParameter;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("返回数据格式有误!");
        }
    }

    /**
     * 获取请求签名
     * 
     * @param params
     * @return
     */
    protected String getChkValue(Map<String, String> params) {
        StringBuffer plain = new StringBuffer();
        for (String key : params.keySet()) {
            plain.append(params.get(key));
        }

        SecureLink sl = new SecureLink();
        int ret = sl.SignMsg(chinaPnrConfig.getMerId(), chinaPnrConfig.getMerKeyFile(), plain.toString());
        if (ret != 0) {
            throw new BusinessException("发送请求签名错误!");
        }
        return sl.getChkValue();
    }

    /**
     * 获取页面返回地址
     * 
     * @param retUrl
     * @return
     */
    protected String getRetUrl(String retUrl) {
        if (StringUtils.isBlank(retUrl)) {
            return chinaPnrConfig.getRetHost() + chinaPnrConfig.getRetUrl();
        } else {
            return chinaPnrConfig.getRetHost() + retUrl;
        }
    }

    /**
     * 返回后台返回地址
     * 
     * @param bgRetUrl
     * @return
     */
    protected String getBgRetUrl(String bgRetUrl) {
        if (StringUtils.isBlank(bgRetUrl)) {
            return chinaPnrConfig.getRetHost() + chinaPnrConfig.getBgRetUrl();
        } else {
            return chinaPnrConfig.getRetHost() + bgRetUrl;
        }
    }
}
