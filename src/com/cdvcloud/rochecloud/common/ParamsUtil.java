package com.cdvcloud.rochecloud.common;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 取页面传值的参数拼装成map集合
 * @author ZCZ
 *
 */
@SuppressWarnings("all")
public class ParamsUtil {
	/**
	 * 从request中获得包括参数的map并过滤掉参数值的前后空格
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getParamsMapWithTrim(HttpServletRequest request) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> requestParams = getParamsMap(request);

		Iterator<Map.Entry<String,Object>> it = requestParams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,Object> pairs = (Map.Entry<String,Object>) it.next();
			Object value = pairs.getValue();
			String key = (String)pairs.getKey();
			if (value != null) {
				if (value.getClass().isArray()) {
					int length = Array.getLength(value);
					if (length == 1) {
						params.put(key, java.net.URLDecoder.decode((String) Array.get(value, 0), "UTF-8").trim());
					}
					if (length > 1) {
						params.put(key, getFirstNotNull((String[]) value));
					}
				} else {
					params.put(key, java.net.URLDecoder.decode((String) value, "UTF-8").trim());
				}
			}
		}
		return params;
	}
	
	/**
	 * 从request中获得包括参数的map并过滤掉参数值的前后空格,如果有同名的则以,累加
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getAllParamsMapWithTrim(HttpServletRequest request) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> requestParams = getParamsMap(request);

		Iterator<Map.Entry<String,Object>> it = requestParams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,Object> pairs = (Map.Entry<String,Object>) it.next();
			Object value = pairs.getValue();
			String key = (String)pairs.getKey();
			if (value != null) {
				if (value.getClass().isArray()) {
					int length = Array.getLength(value);
					if (length == 1) {
						params.put(key, java.net.URLDecoder.decode((String) Array.get(value, 0), "UTF-8").trim());
					}
					if (length > 1) {
						params.put(key, getAllNotNull((String[]) value));
					}
				} else {
					params.put(key, java.net.URLDecoder.decode((String) value, "UTF-8").trim());
				}
			}
		}
		return params;
	}
	
	/**
	 * 从请求中获得包含所有参数信息的map
	 * @param request
	 * @return
	 */
	public final static Map getParamsMap(HttpServletRequest request){
		Map map = request.getParameterMap();
		return map;
	}
	
	/**
	 * 同名多个参数传值 只取第一个不为空的 其余忽略
	 * @param strarr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public final static String getFirstNotNull(String[] strarr) throws UnsupportedEncodingException{
		String str="";
		for (int i = 0; i < strarr.length; i++) {
			if(strarr[i]!=null&&!"".equalsIgnoreCase(strarr[i].trim())){
				str=java.net.URLDecoder.decode((String) strarr[i], "UTF-8");
				return str;
			}
		}
		return str;
	}
	
	/**
	 * 同名多个参数传值 如果有同名的则以,累加
	 * @param strarr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public final static String getAllNotNull(String[] strarr) throws UnsupportedEncodingException{
		String str="";
		for (int i = 0; i < strarr.length; i++) {
			if(strarr[i]!=null&&!"".equalsIgnoreCase(strarr[i].trim())){
				String str1=java.net.URLDecoder.decode((String) strarr[i], "UTF-8");
				str+=str1+",";
			}
		}
		if(str.length()>0){
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	
	/**
	 * 以key=value的形式打印出map到控制台
	 * @param map
	 */
	public final static void printMap(Map<Object,Object> map){
		StringBuffer sb = new StringBuffer();
		sb.append("===========Params Map Begin===================");
		sb.append("\n");
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry) it.next();
			Object value = pairs.getValue();
			Object key = pairs.getKey();
			if(value.getClass().isArray()){
				value = Array.get(value, 0);
			}
			sb.append(String.format("%15s",key));
			sb.append("=");
			sb.append(value);
			sb.append("\n");
		}
		sb.append("===========Params Map End=====================");
	
	}
}
