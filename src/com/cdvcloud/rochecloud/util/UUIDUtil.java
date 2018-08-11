package com.cdvcloud.rochecloud.util;

public class UUIDUtil {

	public static String randomUUID() {

		String uuid = java.util.UUID.randomUUID().toString();

		uuid = uuid.toUpperCase();

		uuid = uuid.replaceAll("-", "");

		return uuid;
	}

}
