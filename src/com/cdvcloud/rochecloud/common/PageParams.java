package com.cdvcloud.rochecloud.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.cdvcloud.rochecloud.util.UUIDUtil;
import com.cdvcloud.rochecloud.util.UserUtil;

/**
 * 对于前台页面穿过来的值进行判断赋值的工具类
 * 
 * @author ZCZ
 * 
 */
public class PageParams {
	public final static String getSort(Map<Object, Object> params) {
		String sort = null;
		if (params.get("orderField") != null) {
			if (!params.get("orderField").equals("")) {
				sort = params.get("orderField") + " " + params.get("orderDirection");
			}
		}
		return sort;

	}

	/**
	 * 根据参数取当前第几页的工具类
	 * 
	 * @param params
	 * @return
	 */
	public final static Integer getCurr(Map<Object, Object> params) {
		Integer curr = 1;
		if (params.get("currentPage") != null && !params.get("currentPage").equals("")) {
			curr = Integer.valueOf((String) params.get("currentPage"));
		}
		return curr;
	}

	/**
	 * 根据参数取一页显示多少条的工具类
	 * 
	 * @param params
	 * @return
	 */
	public final static Integer getNumPerPage(Map<Object, Object> params, Integer num) {
		Integer numPerPage = num;
		if (params.get("numPerPage") != null) {
			numPerPage = Integer.valueOf((String) params.get("numPerPage"));
		}
		return numPerPage;
	}

	/**
	 * 解析前台传过来的ids，返回list集合
	 * 
	 * @param s
	 * @return list
	 */
	public final static List<String> getStringtoList(String s) {
		List<String> list = new ArrayList<String>();
		String[] idArray = s.split(",");
		for (int i = 0; i < idArray.length; i++) {
			list.add(idArray[i]);
		}
		return list;
	}

	/**
	 * 拼装sql的工具类
	 * 
	 * @param params
	 * @return
	 */
	public final static String getCondition(Map<String, Object> params) {
		StringBuffer sqlcount = new StringBuffer();
		Set<String> set = params.keySet();
		Iterator<String> intertor = set.iterator();
		while (intertor.hasNext()) {
			String key = intertor.next();
			String pattern = ".*(LIKE|EQ|LT|GT|LE|GE)";
			if (params.get(key) != null && !params.get(key).equals("") && key.matches(pattern)) {
				if (key.contains("LIKE")) {
					sqlcount.append(" and ").append(key.split("LIKE")[0]).append("  like '%").append(params.get(key)).append("%'");
				} else if (key.contains("EQ")) {
					sqlcount.append(" and ").append(key.split("EQ")[0]).append("  = '").append(params.get(key) + "'");
				} else if (key.contains("LT")) {
					sqlcount.append(" and ").append(key.split("LT")[0]).append("  < '").append(params.get(key) + "'");
				} else if (key.contains("GT")) {
					sqlcount.append(" and ").append(key.split("GT")[0]).append("  > '").append(params.get(key) + "'");
				} else if (key.contains("LE")) {
					sqlcount.append(" and ").append(key.split("LE")[0]).append("  <= '").append(params.get(key) + "'");
				} else if (key.contains("GE")) {
					sqlcount.append(" and ").append(key.split("GE")[0]).append("  >= '").append(params.get(key) + "'");
				} else {
					sqlcount.append("");
				}
			}
		}
		String sql = sqlcount.toString();
		return sql;
	}

	public final static String getConditionByCAS(HttpServletRequest request, Map<String, Object> params) {
		StringBuffer sqlcount = new StringBuffer();
		Set<String> set = params.keySet();
		Iterator<String> intertor = set.iterator();
		while (intertor.hasNext()) {
			String key = intertor.next();
			String pattern = ".*(LIKE|EQ|LT|GT|LE|GE)";
			if (params.get(key) != null && !params.get(key).equals("") && key.matches(pattern)) {
				if (key.contains("LIKE")) {
					sqlcount.append(" and ").append(key.split("LIKE")[0]).append("  like '%").append(params.get(key)).append("%'");
				} else if (key.contains("EQ")) {
					sqlcount.append(" and ").append(key.split("EQ")[0]).append("  = '").append(params.get(key) + "'");
				} else if (key.contains("LT")) {
					sqlcount.append(" and ").append(key.split("LT")[0]).append("  < '").append(params.get(key) + "'");
				} else if (key.contains("GT")) {
					sqlcount.append(" and ").append(key.split("GT")[0]).append("  > '").append(params.get(key) + "'");
				} else if (key.contains("LE")) {
					sqlcount.append(" and ").append(key.split("LE")[0]).append("  <= '").append(params.get(key) + "'");
				} else if (key.contains("GE")) {
					sqlcount.append(" and ").append(key.split("GE")[0]).append("  >= '").append(params.get(key) + "'");
				} else {
					sqlcount.append("");
				}
			}
		}
		String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
		sqlcount.append(" and ownerBusCode = '").append(companyName).append("'");
		String sql = sqlcount.toString();
		return sql;
	}

	public final static String getConditionByCASOld(HttpServletRequest request, Map<String, Object> params) {
		StringBuffer sqlcount = new StringBuffer();
		Set<String> set = params.keySet();
		Iterator<String> intertor = set.iterator();
		while (intertor.hasNext()) {
			String key = intertor.next();
			String pattern = ".*(LIKE|EQ|LT|GT|LE|GE)";
			if (params.get(key) != null && !params.get(key).equals("") && key.matches(pattern)) {
				if (key.contains("LIKE")) {
					sqlcount.append(" and ").append(key.split("LIKE")[0]).append("  like '%").append(params.get(key)).append("%'");
				} else if (key.contains("EQ")) {
					sqlcount.append(" and ").append(key.split("EQ")[0]).append("  = '").append(params.get(key) + "'");
				} else if (key.contains("LT")) {
					sqlcount.append(" and ").append(key.split("LT")[0]).append("  < '").append(params.get(key) + "'");
				} else if (key.contains("GT")) {
					sqlcount.append(" and ").append(key.split("GT")[0]).append("  > '").append(params.get(key) + "'");
				} else if (key.contains("LE")) {
					sqlcount.append(" and ").append(key.split("LE")[0]).append("  <= '").append(params.get(key) + "'");
				} else if (key.contains("GE")) {
					sqlcount.append(" and ").append(key.split("GE")[0]).append("  >= '").append(params.get(key) + "'");
				} else {
					sqlcount.append("");
				}
			}
		}
		String companyName = UserUtil.getUserByRequestOld(request, Constants.COMPANY_NAME);
		sqlcount.append(" and ownerBusCode = '").append(companyName).append("'");
		String sql = sqlcount.toString();
		return sql;
	}

	/**
	 * 动态编目表，拼装插入数据的sql工具类
	 * 
	 * @param params
	 *            页面中的属性 如果前包含5个字母是VMSCS是vms，catalog(编目),sql的缩写,则认为是动态编目模板中的字段
	 * @return
	 */
	public final static String insertCatalogSql(Map<String, Object> params, String materialid, String createname) {
		StringBuffer field = new StringBuffer();
		StringBuffer value = new StringBuffer();
		Set<String> set = params.keySet();
		Iterator<String> intertor = set.iterator();
		while (intertor.hasNext()) {
			String key = intertor.next();
			String pattern = "VMSCS.*";
			// 是否置顶匹配.
			String topPattern = "VMSCSshifouzhiding.*";// VMSCSshifouzhiding193550
			if (!key.equals("VMSCSid")) {
				if (key.matches(pattern)) {
					field.append(key.substring(5) + ",");
					String invalue = (String) params.get(key);
					if (("").equals(invalue)) {
						value.append("null,");
					} else {
						// top.
						if (key.matches(topPattern)) {
							String keyTopValue = (String) params.get(key);
							if (null != keyTopValue && !("").equals(keyTopValue)) {
								// 置顶为否
								if ("否".equals(keyTopValue)) {
									field.append("zhiding_time , ");
									value.append("'否' ,");
									value.append("null ,");
								} else {
									@SuppressWarnings("unused")
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 设置日期格式
									field.append("zhiding_time  ,");
									value.append("'是' ,");
									value.append("now(),");
								}

							}
						} else {
							value.append("'" + invalue + "',");
						}

					}
				}
			}
		}
		field.append("id,").append("material_id,").append("create_user,").append("create_time,");
		value.append("'" + UUIDUtil.randomUUID() + "',").append("'" + materialid + "',").append("'" + createname + "',").append("now(),");
		String fieldString = field.toString();
		fieldString = fieldString.substring(0, fieldString.length() - 1);
		String valueString = value.toString();
		valueString = valueString.substring(0, valueString.length() - 1);
		String tableName = (String) params.get("CatalogTableName");
		String sql = "insert into " + tableName + " (" + fieldString + ") values (" + valueString + ")";
		return sql;
	}

	/**
	 * 动态编目表，拼装插入数据的sql工具类
	 * 
	 * @param params
	 *            页面中的属性 如果前包含5个字母是VMSCS是vms，catalog(编目),sql的缩写,则认为是动态编目模板中的字段
	 * @return
	 */
	public final static String updateCatalogSql(Map<String, Object> params) {
		StringBuffer field = new StringBuffer();
		Set<String> set = params.keySet();
		Iterator<String> intertor = set.iterator();
		while (intertor.hasNext()) {
			String key = intertor.next();
			String pattern = "VMSCS.*";
			// 是否置顶匹配.
			String topPattern = "VMSCSshifouzhiding.*";// VMSCSshifouzhiding193550
			if (!key.equals("VMSCSid")) {
				if (key.matches(pattern)) {
					String invalue = (String) params.get(key);
					if (null != invalue && !("").equals(invalue)) {
						// top.
						if (key.matches(topPattern)) {
							String keyTopValue = (String) params.get(key);
							if (null != keyTopValue && !("").equals(keyTopValue)) {
								// 置顶为否
								if ("否".equals(keyTopValue)) {
									field.append("zhiding_time=null , ");
								} else {
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 设置日期格式
									field.append("zhiding_time= '" + df.format(new Date()) + "' ,");
								}

							}
						}
						field.append(key.substring(5) + "= '" + invalue + "' ,");
					} else {
						field.append(key.substring(5) + "=null ,");
					}
				}
			}
		}
		String fieldString = field.toString();
		fieldString = fieldString.substring(0, fieldString.length() - 1);
		String tableName = (String) params.get("CatalogTableName");
		String catalogid = (String) params.get("VMSCSid");
		String sql = "update " + tableName + " set " + fieldString + " where id='" + catalogid + "'";
		return sql;
	}


}
