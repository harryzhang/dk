package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.UserReviewDao;

/**
 * 用户评论业务处理类
 * 
 * @author xiemin
 * @version [版本号, 2013-9-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserReviewService extends BaseService
{
    /**
     * 引入日志
     */
    public static Log log = LogFactory.getLog(UserManageServic.class);
    
    /**
     * 注入dao
     */
    private UserReviewDao userReviewDao;
    
    /** 
     * 用户评论查询列表，分页
     * @param pageBean
     * @param userName
     * @param content
     * @param status
     * @throws SQLException
     * @throws DataException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void findUserReview(PageBean<Map<String, Object>> pageBean,
        String userName, String content, String status)
        throws SQLException, DataException
    {
        Connection conn = connectionManager.getConnection();
        StringBuffer condition = new StringBuffer();
        try
        {
            if (StringUtils.isNotBlank(userName))
            {
                condition.append(" and username  like '%"
                    + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
            }
            if (StringUtils.isNotBlank(content))
            {
                condition.append(" and msgContent like '%"
                    + StringEscapeUtils.escapeSql(content.trim()) + "%' ");
            }
            if (StringUtils.isNotBlank(status))
            {
                condition.append(" and status = '"
                    + StringEscapeUtils.escapeSql(status.trim()) + "' ");
            }
            
            dataPage(conn,
                pageBean,
                " v_t_review_msgboard",
                " * ",
                " order by  id  desc ",
                condition.toString());
        }
        catch (SQLException e)
        {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        catch (DataException e)
        {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            conn.close();
        }
    }
    
    /**
     * 审核
     * 
     * @param id
     * @param status
     * @return
     * @throws SQLException [参数说明]
     * 
     * @return long [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public long updateUserReview(String id, String status,String dsc)
        throws SQLException
    {
        Connection conn = MySQL.getConnection();
        long result = -1L;
        try
        {
            result = userReviewDao.updateUserReview(conn, id, status,dsc);
            conn.commit();
        }
        catch (SQLException e)
        {
            log.error(e);
            conn.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            conn.close();
        }
        
        return result;
    }
    
    /**
     * 删除评论信息
     * 
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException
     */
    public void deleteUserReview(String ids)
        throws SQLException, DataException
    {
        Connection conn = connectionManager.getConnection();
        try
        {
            userReviewDao.deleteUserReview(conn, ids);
        }
        catch (SQLException e)
        {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        catch (DataException e)
        {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            conn.close();
        }
        
    }
    
    /** 
     * 根据ID查询评论信息
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String,String> findByUserReview(String id)throws SQLException, DataException
    {
        Connection conn = connectionManager.getConnection();
        Map<String, String> map = new HashMap<String, String>();
        try
        {
           map =  userReviewDao.findByUserReview(conn, id);
        }
        catch (SQLException e)
        {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        catch (DataException e)
        {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            conn.close();
        }
        return map;
    }
    
    public void setUserReviewDao(UserReviewDao userReviewDao)
    {
        this.userReviewDao = userReviewDao;
    }
}
