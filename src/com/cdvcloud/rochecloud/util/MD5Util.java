package com.cdvcloud.rochecloud.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;


/**
 * MD5验证工具
 * 
 * @author yangchao
 * @version $Id: MD5Util.java, v 0.1 2012-2-20 下午4:06:11 yangchao Exp $
 */
public class MD5Util {
	private static final Logger logger = Logger.getLogger(MD5Util.class);
	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final char hexDigits1[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
		    'A', 'B', 'C', 'D', 'E', 'F' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("MD5加密报没有该算法：" + e.getMessage());
		}
	}

	/**
	 * oss上传使用的md5加密
	 *
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String md5s(String plainText) {
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest md = null;
		StringBuffer buf = new StringBuffer("");
		try {
			md = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			md.update(plainText.getBytes());
			// 获得密文
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("对字符串：" + plainText + ",MD5加密失败！" + e.getMessage());
		}
		return buf.toString();

	}


	/**
	 * 得到参数加密后的MD5值
	 * @param inStr
	 * @return 32byte MD5 Value
	 */
	private static String getMD5(String inStr){
		byte[] inStrBytes = inStr.getBytes();
		try {
			MessageDigest mdFive = MessageDigest.getInstance("MD5");
			mdFive.update(inStrBytes);
			byte[] mdByte = mdFive.digest();
			char[] str = new char[mdByte.length * 2];
			int k = 0;
			for(int i=0;i<mdByte.length;i++) {
				byte temp = mdByte[i];
				str[k++] = hexDigits1[temp >>> 4 & 0xf];
				str[k++] = hexDigits1[temp & 0xf];
			}
			return new String(str).toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


	/** 
	* @Title: getMd5ToLower 
	* @Description: TODO(小写MD5-用于用户账号密码) 
	* @author lyh liyonghui@cdvcloud.com
	* @date 2018年6月29日 下午2:16:18 
	* @param str
	* @return
	* @return String
	* @throws 
	*/
	public static String getMd5ToLower(String str){
		String strResult = getMD5(str);
		if(!StringUtil.isEmpty(strResult)){
			return strResult.toLowerCase();
		}else{
			return null;
		}
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getMd5ToLower("123456"));
	}

}