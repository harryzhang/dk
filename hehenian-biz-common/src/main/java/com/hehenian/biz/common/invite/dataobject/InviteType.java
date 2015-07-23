/**
 * 
 */
package com.hehenian.biz.common.invite.dataobject;

/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.invite.dataobject
 * @Title: InviteType
 * @Description: 邀请类型枚举
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public enum InviteType {

	WECHAT(1), SMS(2);

	private int typeCode;

	private InviteType(int typeCode) {
		this.typeCode = typeCode;
	}
	
	public Integer getCode() {
        return typeCode;
    }

	@Override
	public String toString() {
		return String.valueOf(this.typeCode);
	}
}
