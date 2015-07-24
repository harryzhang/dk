package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 用户评论数据实现类
 * 
 * @author xiemin
 * @version [版本号, 2013-9-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserReviewDao
{
    /**
     * 审核
     * 
     * @param conn
     * @param id
     * @param status
     * @return
     * @throws SQLException [参数说明]
     * 
     * @return long [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public long updateUserReview(Connection conn, String id, String status,String dsc)
        throws SQLException
    {
        Dao.Tables.t_msgboard t_msgboard =
            new Dao().new Tables().new t_msgboard();
        t_msgboard.status.setValue(Integer.valueOf(status));
        t_msgboard.dsc.setValue(dsc);
        return t_msgboard.update(conn, " id in (" + id + ")");
    }
    
    /**
     * 删除评论信息
     * 
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException
     */
    public void deleteUserReview(Connection conn, String ids)
        throws SQLException, DataException
    {
        Dao.Tables.t_msgboard t_msgboard =
            new Dao().new Tables().new t_msgboard();
        t_msgboard.ifdelete.setValue(1);
        t_msgboard.update(conn, "id in(" + ids + ")");
    }
    
    /**
     * 根据ID查询评论信息
     * 
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException
     */
    public Map<String,String> findByUserReview(Connection conn, String id) throws SQLException,
            DataException {
        StringBuffer sbf = new StringBuffer();
        sbf.append("SELECT a.id,b.username as userName,c.username as reviewName,a.msgContent,a.status,a.dsc from t_msgboard a");
            sbf.append(" inner join t_user b on b.id = a.msger");
            sbf.append(" inner join t_user c on c.id = a.boardUserID");
            sbf.append(" where a.id="+id);
            DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
            return BeanMapUtils.dataSetToMap(dataSet);
    }
}
