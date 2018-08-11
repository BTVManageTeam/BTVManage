package com.cdvcloud.rochecloud.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacsonUtil {
	private final static Logger logger = Logger.getLogger(JacsonUtil.class);

	// 将map转换为json
	public static String writeMap2JSON(Map<String, Object> map) {
		String json = "";
		if (map != null && !map.isEmpty()) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				json = objectMapper.writeValueAsString(map);
			} catch (JsonGenerationException e) {
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return json;
	}

	// 将List转换为json
	public static String writeList2JSON(List<Map<String, Object>> list) {
		String json = "";
		if (list != null && !list.isEmpty()) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				json = objectMapper.writeValueAsString(list);
			} catch (JsonGenerationException e) {
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return json;
	}

	// 将json转换为map
	@SuppressWarnings("unchecked")
	public static Map<String, Object> readJSON2Map(String json) {
		Map<String, Object> resultMap = null;
		if (null != json && !"".equals(json)) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				resultMap = objectMapper.readValue(json, Map.class);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return resultMap;

	}

	// 将obj转换为json
	public static String writeEntity2JSON(Object obj) {
		String json = "";
		if (obj != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				json = objectMapper.writeValueAsString(obj);
			} catch (JsonGenerationException e) {
				logger.error(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return json;
	}

}
