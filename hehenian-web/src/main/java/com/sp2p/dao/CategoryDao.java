package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 客服中心帮助问题类型处理
 * @author li.hou
 *
 */
public class CategoryDao {

	/**
	 * 添加问题类型。类型id为自动增长。
	 * @param conn
	 * @param name 类型描述
	 * @return
	 * @throws SQLException
	 */
	public Long addCategory(Connection conn,String name) throws SQLException{
		Dao.Tables.t_help_type t_category = new Dao().new Tables().new t_help_type();
		t_category.helpDescribe.setValue(name);
		return t_category.insert(conn);
	}
	
	/**
	 * 查找对应id的帮助类型信息，用于信息显示后进行信息的修改
	 * @param conn
	 * @param id 帮助类型id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryCategoryById(Connection conn,Long id) throws SQLException, DataException{
		Dao.Tables.t_help_type t_category = new Dao().new Tables().new t_help_type();
		DataSet dataSet = t_category.open(conn, "", " id="+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 帮助类型修改，根据对应id修改帮助类型信息
	 * @param conn
	 * @param typeId 帮助类型id
	 * @param helpDescribe
	 * @return
	 * @throws SQLException
	 */
	public Long updateCategory(Connection conn,Long typeId,String helpDescribe) throws SQLException{
		Dao.Tables.t_help_type t_category = new Dao().new Tables().new t_help_type();
		if(helpDescribe!=null){
			t_category.helpDescribe.setValue(helpDescribe);
		}
		return t_category.update(conn, " id="+typeId);
	}
	
	/**
	 * 修改分类的下标
	 * @param conn
	 * @param ids{"undifined","2","3","1"}
	 * @return
	 * @throws SQLException 
	 */
	public Long updateCategoryIndex(Connection conn,String ids) throws SQLException{
		String[] transIds = ids.split(",");
		Long result = 0L;
		Long re = -1L;
		long tempId = 0;
		if (transIds.length > 0) {
			for(int i=0;i<transIds.length;i++){
				tempId = 0;re = -1L;//重新赋值
				//看是否是正规的int值
				tempId = Convert.strToLong(transIds[i], -1);
				if(tempId != -1){
					re = MySQL.executeNonQuery(conn,
							"update t_help_type set sortId="+i+" where id="+tempId);
					if(re > 0)
						result += 1;
				}
			}
		}
		return 	result==(transIds.length-1)?1L:-1L;	
	}
	
	/**
	 * 删除对应id的问题类型信息
	 * @param conn
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public long deleteCategory(Connection conn,long typeId) throws SQLException{
		Dao.Tables.t_help_type t_category = new Dao().new Tables().new t_help_type();
		return t_category.delete(conn, " id="+typeId );
	}
	
}
