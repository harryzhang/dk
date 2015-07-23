package com.hehenian.biz.common.util.huifu;

/**
 * @author 杨程
 * @version Oct 21, 2011 11:32:27 PM
 * @declaration
 */
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.data.ColumnCollection;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.RowCollection;

/**
 * 
 * @Description Map域实体对象转换
 * @Author Yang Cheng
 * @Date: Feb 9, 2012 1:55:22 AM
 * @Version
 * 
 */
public class BeanMapUtils {
	public static Log log = LogFactory.getLog(BeanMapUtils.class);
	public static String format ="yyyy-MM-dd HH:mm:ss";
	/**
	 * @Description: 将对象转为Map
	 * @Author Yang Cheng
	 * @Date: Feb 9, 2012 1:56:20 AM
	 * @param <T>
	 * @param bean
	 * @return
	 * @return Map<String,String>
	 */
	public static <T> Map<String, String> beanToMap(T bean) {
		Class<? extends Object> type = bean.getClass();
		Map<String, String> returnMap = new HashMap<String, String>();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					returnMap.put(propertyName, result(result));
				}
			}
		} catch (IntrospectionException e) {
			log.error(e);
			throw new RuntimeException("分析类属性失败", e);
		} catch (IllegalAccessException e) {
			log.error(e);
			throw new RuntimeException("分析类属性失败", e);
		} catch (InvocationTargetException e) {
			log.error(e);
			throw new RuntimeException("分析类属性失败", e);
		}
		return returnMap;
	}

	private static String result(Object result) {
		if (null == result) {
			return "";
		}
		if (result instanceof Number) {
			return result + "";
		}
		if (result instanceof Boolean) {
			return result + "";
		}
		if (result instanceof Date) {
			if(null != result){
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				return  formatter.format(result);
			}
			
			
			return result + "";
		}
		return (String) result;
	}

	public static Map<String, String> dataSetToMap(DataSet dataSet)
			throws DataException {
		RowCollection rowCollection = dataSet.tables.get(0).rows;
		ColumnCollection columnCollection = dataSet.tables.get(0).columns;
		Map<String, String> paramMap =  new HashMap<String, String>();
		for (int i = 0; i < rowCollection.getCount(); i++) {
			for (int j = 0; j < columnCollection.getCount(); j++) {
				paramMap.put(columnCollection.get(j).getName(),result( rowCollection
						.get(i).get(j)));
			}
		}
		if(paramMap.isEmpty()){
			return null;
		}
		return paramMap;
	}
}