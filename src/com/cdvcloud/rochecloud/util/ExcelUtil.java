package com.cdvcloud.rochecloud.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * POI 解析 excel 兼容2007
 * 
 * @author TYW
 * 
 */
public class ExcelUtil {

	private static final Logger logger = Logger.getLogger(ExcelUtil.class);

	/**
	 * 值对象封装
	 * 
	 * @param cell
	 * @param cellType
	 * @return
	 */
	private static Object getValue(Cell cell, int cellType) {
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
			return (long)cell.getNumericCellValue();
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			return cell.getRichStringCellValue();
		} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if (cellType == Cell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		} else if (cellType == Cell.CELL_TYPE_BLANK) {
			return "";
		} else if (cellType == Cell.CELL_TYPE_ERROR) {
			return "";
		} else {
			return "";
		}
	}

	/**
	 * 解析Excel文件
	 * 
	 * @param sheetNum
	 * @param path
	 * @return
	 */
	public static List<Map<String, Object>> readExcelToMap(int sheetNum, String path) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			InputStream ins = new FileInputStream(path);
			Workbook wkbook = WorkbookFactory.create(ins);
			// 获取sheet
			Sheet sheet = wkbook.getSheetAt(sheetNum);
			if (null == sheet) {
				return null;
			}
			// 遍历行
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				// 遍历列
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					Cell cell = row.getCell(cellNum);
					String strValue = "";
					if (null == cell) {
						strValue = String.valueOf(cell);
						if ("null".equals(strValue)) {
							strValue = "";
						}
					} else {
						int cellType = cell.getCellType();
						Object obj = getValue(cell, cellType);
						strValue = String.valueOf(obj);
					}
					strValue = strValue.replace("\r", "").replace("\n", "");
					if (cellNum == 0) {
						map.put("loginId", strValue);
					} else if (cellNum == 1) {
						map.put("userName", strValue);
					} else if (cellNum == 2) {
						map.put("depName", strValue);
					} else if (cellNum == 3) {
						map.put("userPhone", strValue);
					} else if (cellNum == 4) {
						map.put("userEmail", strValue);
					} else if (cellNum == 5) {
						map.put("userAddress", strValue);
					}
				}
				if (null != map && !map.isEmpty()) {
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取excel失败,路径=" + path + "\n" + e.getMessage());
			return null;
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		String strPath = "D:/下载/template.xlsx";
		List<Map<String, Object>> lists = readExcelToMap(0, strPath);
		System.err.println("开始执行！！！");
		for (Map<String, Object> map : lists) {
			String strJson = JsonUtil.writeEntity2JSON(map);
			System.err.println("采集数据：" + strJson);
		}
		System.err.println("执行完成！！！！");
	}

}