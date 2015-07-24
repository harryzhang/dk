/**
 * 
 */
package com.hehenian.manager.actions.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @Title ExeclTools.java
 * 
 * @Description excel工具
 * 
 * @author huangzl QQ: 272950754
 * 
 * @date 2015年4月14日下午3:43:54
 * 
 * @Package com.hehenian.biz.common.tools
 * 
 */
public class ExeclTools {

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:从json数组中解析出java字符串数组
	 * </p>
	 * 
	 * @author jacobliang
	 * @param jsonString
	 * @return
	 */
//	public static String[] getStringArray4Json(String jsonString) {
//
//		JSONArray jsonArray = JSONArray.fromObject(jsonString);
//		String[] stringArray = new String[jsonArray.size()];
//		for (int i = 0; i < jsonArray.size(); i++) {
//			stringArray[i] = jsonArray.getString(i);
//
//		}
//
//		return stringArray;
//	}

	/**
	 * 根据List对象导出 execl
	 * 
	 * @param <T>
	 * @param execlhearList
	 *            表头
	 * @param excelData
	 *            表索引
	 * @param sheetName
	 *            表格名(英文)
	 * @param pojoList
	 *            导出值
	 * @return
	 */
	public static <T> HSSFWorkbook execlExport(List<String[]> execlhearList,
			String[] excelData, String sheetName, List<T> pojoList) {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		wb.setSheetName(0, sheetName);
		// String hearString = "";
		// String excelDataString = "";
		// try {
		// hearString = java.net.URLDecoder.decode(hear, "utf-8");
		// excelDataString = java.net.URLDecoder.decode(data, "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// String[] execlhear = getStringArray4Json(hearString);
		// String[] excelData = getStringArray4Json(excelDataString);
		HSSFCell cell;
		// 表头
		HSSFRow row ;
		int execlHearSize = execlhearList.size();
		for (int i = 0; i < execlHearSize; i++) {
			String[] execlhear = execlhearList.get(i);
			 row = sheet.createRow((int) i);
			int tempStringLength = execlhear.length;
			for (int a = 0; a < tempStringLength; a++) {
				cell = row.createCell(a);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(execlhear[a]);
			}
		}
		int dataStringLength = excelData.length;
		int count = pojoList.size();

		int a = 0;

		HSSFCellStyle cellStyle = wb.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

		// 写实体
		for (int i = 0; i < count; i++) {
			Object temp = pojoList.get(i);
			row = sheet.createRow((int) i + execlHearSize);
			a = 0;

			if (temp instanceof Map) {
				Map map = (Map) temp;

				for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
					cell = row.createCell( a++);
					Object obj = map.get(excelData[dataInt]);
					// 值是字符串
					if (obj instanceof String) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue((String) obj);

						// 值为数字
					} else if (obj instanceof BigDecimal) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(((BigDecimal) obj).doubleValue());

						// 值为日期
					} else if (obj instanceof Date) {
						cell.setCellValue((Date) obj);
						cell.setCellStyle(cellStyle);
					} else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(String.valueOf(obj));
					}

				}

			} else { // List<pojo> 处理
				Class c = temp.getClass();
				// 获取POJO所有方法
				Method m[] = c.getDeclaredMethods();

				for (int dataInt = 0; dataInt < dataStringLength; dataInt++) {
					cell = row.createCell((short) a++);
					// 数据索引
					String dataIndex = excelData[dataInt];

					String methodString = "get"
							+ dataIndex.substring(0, 1).toUpperCase()
							+ dataIndex.substring(1, dataIndex.length());
					// 根据循环获取索引值
					for (int k = 0; k < m.length; k++) {
						if (m[k].getName().equals(methodString)) {
							// System.out.println("方法名："+m[i].getName());
							try {
								Object obj = m[k].invoke(temp);
								// 值是字符串
								if (obj instanceof String) {
									cell.setCellType(HSSFCell.CELL_TYPE_STRING);
									cell.setCellValue((String) obj);

									// 值为数字
								} else if (obj instanceof BigDecimal) {
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									cell.setCellValue(((BigDecimal) obj)
											.doubleValue());

								} else if (obj instanceof Long) {
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									cell.setCellValue(((Long) obj));

									// 值为日期
								} else if (obj instanceof Date) {
									cell.setCellValue((Date) obj);
									cell.setCellStyle(cellStyle);
								} else {
									cell.setCellType(HSSFCell.CELL_TYPE_STRING);
									cell.setCellValue((String) obj);
								}

								break;
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

		// row = sheet.createRow(count + 1);
		// cell = row.createCell((short) 0);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		// cell.setCellValue("当前导出" + count + "条记录，总共" + count + "条记录");

		return wb;

	}

	/**
	 * 读取单元格的数据
	 * 
	 * @param row
	 * @param cell
	 * @return
	 * @throws NullPointerException
	 */
	public static String processCellValue(HSSFRow row, int cell)
			throws NullPointerException {
		HSSFCell headCell = row.getCell( cell);

		if (null == headCell) {
			return "";
		}

		String itemName = "";
		if (HSSFCell.CELL_TYPE_STRING == headCell.getCellType()) {
			itemName = headCell.getStringCellValue();
		}

		if (HSSFCell.CELL_TYPE_NUMERIC == headCell.getCellType()) {
			if (HSSFDateUtil.isCellDateFormatted(headCell)) {
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				double d = headCell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				itemName = formater.format(date);
			} else {
				BigDecimal a = new BigDecimal(String.valueOf(headCell
						.getNumericCellValue()));
				itemName = (a.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
			}
		}

		if (HSSFCell.CELL_TYPE_BOOLEAN == headCell.getCellType()) {
			itemName = String.valueOf(headCell.getBooleanCellValue());
		}

		if (HSSFCell.CELL_TYPE_BLANK == headCell.getCellType()) {
			itemName = "";
		}

		if (HSSFCell.CELL_TYPE_FORMULA == headCell.getCellType()) {
			itemName = headCell.getCellFormula();
		}
		return itemName;
	}

}
