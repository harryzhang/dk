package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;

public class UserBankManagerDao {

	public Map<String,String> queryBankCardInfos(Connection conn,Long bankId,int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Views.t_bankcard_lists t_info = new Dao().new Views().new t_bankcard_lists();
		DataSet dataSet = t_info.open(conn, "", " id=" + bankId, "",limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> queryBankCardUpdate(Connection conn,Long bankId,int limitStart,int limitCount) 
    throws SQLException, DataException{
	Dao.Views.v_t_bankcard_update_list t_infou = new Dao().new Views().new v_t_bankcard_update_list();
	DataSet dataSet = t_infou.open(conn, " * ", " id=" + bankId, "",limitStart, limitCount);
	return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/** 
	 * 新增银行卡--审核查询
	 * @param conn
	 * @param bankId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String,String> queryBankCard(Connection conn,Long bankId,int limitStart,int limitCount) 
    throws SQLException, DataException{
    Dao.Views.v_t_bankcard_query t_infou = new Dao().new Views().new v_t_bankcard_query();
    DataSet dataSet = t_infou.open(conn, " * ", " id=" + bankId, "",limitStart, limitCount);
    return BeanMapUtils.dataSetToMap(dataSet);
    }
	
	/**
	 * 更新银行卡审核数据信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateBankInfo(Connection conn,Long checkUserId,Long bankId,String remark,Integer status) throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.remark.setValue(remark);
		t_info.checkUser.setValue(checkUserId);
		t_info.cardStatus.setValue(status);
		t_info.checkTime.setValue(new Date());
		return t_info.update(conn, " id="+bankId);
    }
	
	/**
	 * 更新银行卡变更信息
	 * @param conn
	 * @param checkUserId
	 * @param bankId
	 * @param remark
	 * @param result
	 * @return
	 * @throws SQLException 
	 */
	public Long updateModifyBankInfo(Connection conn,Long checkUserId,Long bankId,
			String remark,Integer result,String bankName
			,String branchBankName,String bankCardNo,String date,boolean success) throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.remark.setValue(remark);
		t_info.checkUser.setValue(checkUserId);
		t_info.cardStatus.setValue(result);
		t_info.checkTime.setValue(new Date());
		
		if(success){//审核成功
			//将修改后的银行卡信息变为现在的银行卡信息
			t_info.bankName.setValue(bankName);
			t_info.branchBankName.setValue(branchBankName);
			t_info.cardNo.setValue(bankCardNo);
			t_info.commitTime.setValue(date);
			
			//将现在的银行卡信息进行删除
//			t_info.modifiedBankName.setValue("");
//			t_info.modifiedBranchBankName.setValue("");
//			t_info.modifiedCardNo.setValue("");
			t_info.modifiedCardStatus.setValue(result);
//			t_info.modifiedTime.setValue(null);
		}else{//审核失败
			t_info.modifiedCardStatus.setValue(IConstants.BANK_FAIL);//修改的状态失败
			t_info.cardStatus.setValue(IConstants.BANK_SUCCESS);//以前绑定的银行卡的状态为成功
		}
		
		return t_info.update(conn, " id="+bankId);
	}
	
	public Map<String,String> queryOneBankInfo(Connection conn,Long bankId,int limitStart,int limitCount) 
	   throws SQLException, DataException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		DataSet dataSet = t_info.open(conn, "", " id=" + bankId, "",limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
	
	public Long updateChangeBankInfo(Connection conn,Long bankId,String bankName,String modifiedOpenBankId,String mSubBankName,String province,String city,String bankCard,
			int status,Date date,boolean modify, String provinceId, String cityId)
	    throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.modifiedBankName.setValue(bankName);
		t_info.modifiedOpenBankId.setValue(modifiedOpenBankId);
		t_info.modifiedBranchBankName.setValue(mSubBankName);
		t_info.province.setValue(province);
		t_info.city.setValue(city);
		t_info.modifiedCardNo.setValue(bankCard);
		t_info.modifiedTime.setValue(date);
		t_info.provinceId.setValue(provinceId);
		t_info.cityId.setValue(cityId);
		if(modify){//银行卡变更
			t_info.modifiedCardStatus.setValue(status);
		}else{//取消银行卡变更
			t_info.cardStatus.setValue(status);
			t_info.modifiedCardStatus.setValue(null);
		}
		return t_info.update(conn, " id="+bankId);
    }
}
