/**
 * 
 */
package com.hehenian.biz.dal.invite;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.hehenian.biz.common.invite.dataobject.InviteType;

/**
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.invite
 * @Title: EnumInviteTypeHandler
 * @Description: 邀请类型枚举转换器
 * @author: chenzhpmf
 * @date 2015年5月14日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public class EnumInviteTypeHandler extends BaseTypeHandler {

	private Class<InviteType> type;

	private InviteType[] enums;

	public EnumInviteTypeHandler(Class<InviteType> type) {
		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null)
			throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
	}
	
	public EnumInviteTypeHandler() {

	}

	@Override
	public void setNonNullParameter(PreparedStatement paramPreparedStatement, int paramInt, Object paramObject, JdbcType paramJdbcType)
			throws SQLException {
		// baseTypeHandler已经帮我们做了parameter的null判断
		paramPreparedStatement.setInt(paramInt, ((InviteType)paramObject).getCode());
	}

	@Override
	public Object getNullableResult(ResultSet paramResultSet, String columnName) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = paramResultSet.getInt(columnName);

		if (paramResultSet.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位EnumStatus子类
			return locateEnumStatus(i);
		}
	}

	@Override
	public Object getNullableResult(CallableStatement paramCallableStatement, int columnIndex) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        int i = paramCallableStatement.getInt(columnIndex);
        if (paramCallableStatement.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位EnumStatus子类
            return locateEnumStatus(i);
        }
	}

	/**
	 * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
	 * 
	 * @param code 数据库中存储的自定义code属性
	 * @return code对应的枚举类
	 */
	private InviteType locateEnumStatus(int code) {
		for (InviteType status : InviteType.values()) {
			if (status.getCode().equals(Integer.valueOf(code))) {
				return status;
			}
		}
		throw new IllegalArgumentException("未知的枚举类型：" + code + ",请核对" + type.getSimpleName());
	}
}
