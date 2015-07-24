package com.shove.web.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtils {

	public static void main(String[] args) throws Exception {

		/*
		 * File file = new File("d:1234.xlsx");
		 * 
		 * String[][] result = getData(file, 1);
		 * 
		 * int rowLength = result.length;
		 * 
		 * for(int i=0;i<rowLength;i++) {
		 * 
		 * for(int j=0;j<result[i].length;j++) {
		 * 
		 * System.out.print(result[i][j]+"\t\t"); }
		 * 
		 * System.out.println(); }
		 */

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name1", "第一个——" + i);
			map.put("name2", "第er个——" + i);
			map.put("name3", "第san个——" + i);
			list.add(map);
		}
		// exportExcel(list);

	}

	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * 
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 * 
	 */

	public static String[][] getData(File file, int ignoreRows)

	throws FileNotFoundException, IOException {

		List<String[]> result = new ArrayList<String[]>();

		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;

		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			HSSFSheet st = wb.getSheetAt(sheetIndex);

			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}

				int tempRowSize = row.getLastCellNum() + 1;

				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}

				String[] values = new String[rowSize];
				Arrays.fill(values, "");

				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {

						// 注意：一定要设成这个，否则可能会出现乱码
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						switch (cell.getCellType()) {

						case HSSFCell.CELL_TYPE_STRING:

							value = cell.getStringCellValue();

							break;

						case HSSFCell.CELL_TYPE_NUMERIC:

							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();

								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}

							} else {
								if ("*年利率(%)".equals(st.getRow(1).getCell(columnIndex).getStringCellValue()))
									value = cell.getNumericCellValue() + "";
								else
									value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;

						case HSSFCell.CELL_TYPE_FORMULA:

							// 导入时如果为公式生成的数据则无值

							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}

							break;

						case HSSFCell.CELL_TYPE_BLANK:

							break;

						case HSSFCell.CELL_TYPE_ERROR:

							value = "";

							break;

						case HSSFCell.CELL_TYPE_BOOLEAN:

							value = (cell.getBooleanCellValue() == true ? "Y" : "N");

							break;

						default:

							value = "";

						}

					}

					if (columnIndex == 0 && value.trim().equals("")) {

						break;

					}

					values[columnIndex] = rightTrim(value);
					values[columnIndex] = value.trim();

					hasValue = true;

				}

				if (hasValue) {

					result.add(values);

				}

			}

		}

		in.close();

		String[][] returnArray = new String[result.size()][rowSize];

		for (int i = 0; i < returnArray.length; i++) {

			returnArray[i] = (String[]) result.get(i);

		}

		return returnArray;

	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 * 
	 */

	public static String rightTrim(String str) {

		if (str == null) {

			return "";

		}

		int length = str.length();

		for (int i = length - 1; i >= 0; i--) {

			if (str.charAt(i) != 0x20) {

				break;

			}

			length--;

		}

		return str.substring(0, length);

	}

	public static HSSFWorkbook exportExcel(String sheetName, List<Map<String, Object>> list, String[] titles, String[] fieldNames) {

		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			HSSFSheet sheet = null;
			String tb = "";
			int k = 0;
			// 对每个表生成一个新的sheet,并以表名命名
			if (sheetName == null) {
				sheetName = "sheet1";
			}
			sheet = wb.createSheet("sheet1");
			// 设置表头的说明
			HSSFRow topRow = sheet.createRow(0);

			// 设置列宽
			// sheet.setColumnWidth((short) 0, (short) 2000);
			// sheet.setColumnWidth((short) 1, (short) 5000);
			// sheet.setColumnWidth((short) 2, (short) 5000);
			// sheet.setColumnWidth((short) 3, (short) 2000);
			// sheet.setColumnWidth((short) 4, (short) 7000);
			// sheet.setColumnWidth((short) 5, (short) 7000);
			for (int i = 0; i < titles.length; i++) {
				setCellGBKValue(topRow.createCell((short) i), titles[i]);
			}

			k = list.size();
			for (Map<String, Object> map : list) {
				HSSFRow row = sheet.createRow(k);
				/*
				 * row.createCell((short) 0).setCellValue("值1");
				 * row.createCell((short) 1).setCellValue("值2");
				 * row.createCell((short) 2).setCellValue("值3");
				 */
				for (int i = 0; i < fieldNames.length; i++) {
					setCellGBKValue(row.createCell((short) i), map.get(fieldNames[i]) + "");
				}
				k--;
			}

			// 把生成的EXCEL文件输出保存
			// FileOutputStream fileOut = new
			// FileOutputStream("c:vouchers.xls");
			// wb.write(fileOut);
			// fileOut.close();
			// JOptionPane.showMessageDialog(null,"导出数据成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	private static void setCellGBKValue(HSSFCell cell, String value) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 设置CELL的编码信息
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
	}

}
