package com.cdvcloud.rochecloud.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录信息工具类
 * 
 * @author TYW
 * 
 */
public class LoginUtil {

	private final static String g_strURI = "users/getUsersByAppId/";
	private final static String g_strAPPCODE = "urm";
//	private final static String g_strVERSIONID = "1.0";
//	private final static String g_strCOMPANYID = "cdvcloud";
//	private final static String g_strSERVICECODE = "";

	public static boolean canBrowse(String strUserId) {
		boolean blnCanBrowse = false;
		// 根据全局变量拼接正确的接口url，通过http工具类请求接口
		// 根据返回的用户信息结合，判断该集合中是否包含“strUsername”
		long timeStamp = new Date().getTime();
		String url = Configuration.getConfigValue("URL");
		url = url + "1/2/3/4/5/" + g_strURI;
		String paramsStr = "accessToken=accessToken&timeStamp=" + timeStamp + "&appId=" + g_strAPPCODE;
		String result = HttpUtil.postMethod(url, paramsStr);
		if (!StringUtil.isEmpty(result)) {
			Map<String, Object> map = JsonUtil.readJSON2Map(result);
			if (map != null && !map.isEmpty()) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> listMap = (List<Map<String, Object>>) ((Map<String, Object>) map.get("data")).get("users");
				if (listMap != null && listMap.size() > 0) {
					for (int i = 0; i < listMap.size(); i++) {
						if (strUserId.equals(listMap.get(i).get("userId"))) {
							blnCanBrowse = true;
							break;
						}
					}
				}
			}
		}
		return blnCanBrowse;
	}
}
