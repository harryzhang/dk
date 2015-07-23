/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.task
 * @Title: DefaultTask.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月15日 上午10:00:26
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.task;

import org.apache.log4j.Logger;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月15日 上午10:00:26
 */
public abstract class DefaultTask implements ITask {
    
    protected static Logger logger = Logger.getLogger(DefaultTask.class);
    
    protected abstract String getJobName(); 

    /* (no-Javadoc) 
     * <p>Title: execute</p> 
     * <p>Description: </p>  
     * @see com.hehenian.biz.common.task.ITask#execute() 
     */
    @Override
    public void execute() {
        logger.info(getJobName()+"开始=======================");
        doJob();
        logger.info(getJobName()+"结束=======================");
    }
    
    protected abstract void doJob();

}
