package com.cdvcloud.rochecloud.common;

/**
 * 常量类
 * 
 * @author lisheng
 * 
 */
public class Constants {

	/** 状态 */
	public static final String CODE = "code";
	/** 返回结果*/
	public static final String DATA = "data";

	/** Token */
	public static final String ACCESS_TOKEN = "accessToken";
	/** Token配置地址 */
	public static final String CHECK_TOKEN_URL = "CHECK_TOKEN_URL";

	/** 内容 */
	public static final String MESSAGE = "message";

	/** 编码格式 */
	public static final String CODED_FORMAT = "UTF-8";
	public static final int TASK_TYPE_TRANSCODE = 1;
	/** 缓存大小 */
	public static final int BYTE_LENGTH = 1024 * 1024;
	public static final String PIC_INTERVAL = "picInterval";
	public static final String PIC_NUM = "picNum";
	public static final String HTTPCODE = "http://";
	public static final String FTPCODE = "ftp://";

	/** 数字0 */
	public static final int ZERO = 0;
	/** 数字1 */
	public static final int INONE = 1;
	/** 字符串0 */
	public static final String SZERO = "0";
	/** 字符串1 */
	public static final String ONE = "1";
	/** 用户id */
	public static final String COMPANYID = "cdvcloud";
	/** 成功 */
	public static final String SERVICE_SUCCESS = "success";
	/** 失败 */
	public static final String SERVICE_ERROR = "error";
	/** 其他 */
	public static final String SERVICE_OTHER = "other";
	/** 产品名称 */
	public static final String PRODUCT = "portal";

	/** 分隔符 逗号',' */
	public static final String DELIMITER = ",";

	/** 是否使用网卡绑定 */
	public static final boolean BIND = false;

	/** 当前在线用户名 */
	public static final String CURRENT_USER_NAME = "userName";

	/** 当前在线用户id */
	public static final String CURRENT_USER_ID = "userId";
	/** 验证框架是 **/
	public static final String VALIDATE_YES = "y";
	/** cas返回的companyCode **/
	public static final String COMPANY_NAME = "companyName";
	/** cas返回的登录名 **/
	public static final String LOGIN_ID = "loginId";
	/** 超级管理员的名称 **/
	public static final String ADMINISTRATOR = "root";
	
	/** 媒资4.1用户管理地址 */
	public static final String RMS_RESOURCE_URL = "RMS_RESOURCE_URL";
	/** 媒资4.1用户管理token */
	public static final String RMS_RESOURCE_TOKEN = "RMS_RESOURCE_TOKEN";
	/** 媒资4.1用户管理serviceCode */
	public static final String RMS_RESOURCE_SERVICE_CODE = "RMS_RESOURCE_SERVICE_CODE";
	/** 媒资4.1用户管理appCode */
	public static final String RMS_RESOURCE_APP_CODE = "RMS_RESOURCE_APP_CODE";	
	/** 角色排序字段 */
	public static final String CTIME = "ctime";
}