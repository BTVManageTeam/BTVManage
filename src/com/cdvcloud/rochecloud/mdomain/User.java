package com.cdvcloud.rochecloud.mdomain;

/**
 * 业务描述：微信用户表
 * 
 * @author chenchang
 *
 */
public class User {
	
	/**  数据库表  **/
	public static final String DB_COLLECTION = "user";
	
	/** mongodb 数据库的id主键 */
	public static final String ID = "_id";
	
	/** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。**/
	public static final String SUBSCRIBE = "subscribe";
	
	/** 用户的微信标识 对当前公众号唯一 */
	public static final String OPENID = "openid";

	/** 用户昵称 */
	public static final String NICKNAME = "nickname";
	
	/** 用户备注 */
	public static final String REMARK = "remark";
	
	/** 用户的性别 值为1时是男性，值为2时是女性，值为0时是未知 */
	public static final String SEX = "sex";
	
	/** 用户的语言 简体中文为zh_CN*/
	public static final String LANGUAGE = "language";
	
	/** 用户所在城市 */
	public static final String CITY = "city";
	
	/** 用户所在省份 */
	public static final String PROVINCE = "province";
	
	/** 用户所在国家 */
	public static final String  COUNTRY = "country";
	
	/** 用户头像 */
	public static final String HEADIMGURL = "headimgurl";
	
	/** 用户关注时间 */
	public static final String SUBSCRIBE_TIME = "subscribe_time";
	
	/** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段**/
	public static final String UNIONID = "unionid";
	
	/** 创建日期 yyyy-MM-dd HH:mm:ss*/ 
	public static final String CREATE_TIME = "create_time";

	/** 创建日期 yyyy-MM-dd 通过subscribe_time进行转换*/
	public static final String SUBSCRIBE_TIME_SDF = "subscribe_time_sdf";
	
	/** 创建日期 yyyy-MM-dd 当前日期进行转换*/
	public static final String UNSUBSCRIBE_TIME_SDF = "unsubscribe_time_sdf";
	
	/** 所属商项目ID 唯一**/
	public static final String PRODUCTID = "productId";
	
	/** 黑名单  1：表示黑名单 ,"" :表示非黑名单**/
	public static final String BLACKLIST = "blacklist";
	
	/** 用户所属的分组id**/
	public static final String GROUPID = "groupId";
	
	/** 用户所属的分组名称**/
	public static final String GROUPNAME = "groupName";
	
	/** 参与活动的时间**/
	public static final String CURRENT_ACTIVITY_TIME = "current_activity_time";
	
	/** 当前参与活动的次数**/
	public static final String ACTIVITY_COUNT = "activity_count";
	
}
