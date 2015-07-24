package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.UserReviewService;

/**
 * 用户评论管理控制类
 * 
 * @author xiemin
 * @version [版本号, 2013-9-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserReviewAction extends BaseFrontAction {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public static Log log = LogFactory.getLog(UserReviewAction.class);

	/**
	 * 注入Service
	 */
	public UserReviewService userReviewService;

	/**
	 * 用户评论初始化页面
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String findUserReviewIndex() {
		return SUCCESS;
	}

	/**
	 * 查询用户评论列表
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String findUserReview() throws SQLException, DataException {

		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("msgContent"));
		String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status"));

		userReviewService.findUserReview(pageBean, userName, content, status);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 审核
	 * 
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @throws  
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String updateUserReview() throws SQLException, DataException, IOException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status"));
		String dsc = SqlInfusion.FilteSqlInfusion(paramMap.get("dsc"));
		long result = 0;
		try {
			result = userReviewService.updateUserReview(id, status,dsc);
			if(result > 0){
				JSONUtils.printStr("1");
			}else{
				JSONUtils.printStr("2");
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除用户评论
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String deleteUserReview() throws DataException, SQLException {
		String ids = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		try {
			userReviewService.deleteUserReview(ids);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_report",
					admin.getUserName(), IConstants.DELETE, admin.getLastIP(),
					0, "删除用户评论", 2);
			return SUCCESS;
		} catch (DataException e) {
			e.printStackTrace();
			return ERROR;
		} catch (SQLException e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	/**
	 * 根据ID查询用户评论信息
	 * 
	 * @return [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String findByUserReview() throws DataException, SQLException {
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
		Map<String, String> map = null;
		try {
			map = userReviewService.findByUserReview(id);
			request().setAttribute("map", map);
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public UserReviewService getUserReviewService() {
		return userReviewService;
	}

	public void setUserReviewService(UserReviewService userReviewService) {
		this.userReviewService = userReviewService;
	}
}
