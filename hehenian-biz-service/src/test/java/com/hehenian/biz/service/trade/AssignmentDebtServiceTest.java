/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade
 * @Title: AssignmentDebtServiceTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月14日 下午8:28:20
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import chinapnr.SecureLink;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IAssignmentDebtService;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月14日 下午8:28:20
 */
public class AssignmentDebtServiceTest extends BaseTestCase {
    @Autowired
    private IAssignmentDebtService assignmentDebtService;

    @Test
    public void testUpdatePurchaseDebt() {
        assignmentDebtService.updatePurchaseDebt(316l, 666.68);
    }

    // 查询用户账户余额
    public static void main(String[] args) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", "10");// 版本
            params.put("CmdId", "QueryBalanceBg");// 消息类型
            params.put("MerCustId", "6000060000477719");// 商户号
            params.put("UsrCustId", "6000060031135692");// ------------
            StringBuffer plain = new StringBuffer();
            for (String key : params.keySet()) {
                plain.append(params.get(key));
            }

            SecureLink sl = new SecureLink();
            int ret = sl.SignMsg("830036",
                    "D:/work/hehenian-system-master/hehenian-web/src/main/webapp/hhnchinapnr/MerPrK830036.key",
                    plain.toString());
            if (ret != 0) {
                throw new BusinessException("发送请求签名错误!");
            }
            params.put("ChkValue", sl.getChkValue());// 请求签名

            String jsonString = HttpClientUtils.post("https://lab.chinapnr.com/muser/publicRequests", params);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
