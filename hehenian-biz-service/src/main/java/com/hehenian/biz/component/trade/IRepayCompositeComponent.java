package com.hehenian.biz.component.trade;

import java.util.Map;

public interface IRepayCompositeComponent {
	/**
	 * 
	 * doInsteadOfRepay 代偿
	 * @param contextMap 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	void doInsteadOfRepay(Map contextMap);

	/**
	 * 
	 * doPreRepay 提前还款
	 * @param contextMap 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	void doPreRepay(Map contextMap);

	/**
	 * 
	 * doNormalRepay 正常还款
	 * @param contextMap 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	void doNormalRepay(Map contextMap);

}
