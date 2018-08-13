package com.cdvcloud.rochecloud.domain.wechar;

/**
 * 业务描述：微信key信息记录表
 * 
 * @author chenchang
 *
 */
public class WechatKey {
	/**  数据库表  **/
	public static final String DB_COLLECTION = "wechat_key";
	/** mongodb 数据库的id主键 */
	public static final String ID = "_id";
	
	public static final String APPTYPE = "appType"; //weixin,app,h5
	
	/** 所属商项目ID 唯一**/
	public static final String PRODUCTID = "productId";
	/**项目的中文名称**/
	public static final String PRODUCTNAME = "productName"; //映射到微信名称weichatName
	/** 所属商项目描述**/
	public static final String PRODUCTDESC = "productDesc";
	
	public static final String CONTACTADDRESS = "contactAddress"; //联系地址
	public static final String CONTACTUSER = "contactUser"; //联系人
	public static final String CONTACTEMAIL = "contactEmail"; //联系邮箱
	public static final String CONTACTPHONE = "contactPhone"; //联系电话
	
	public static final String COMPANYID = "companyId"; //所属厂商

	/**公众号显示需要字段 **/
	/** AppID */
	public static final String APPID = "appid";
	/** AppSecret */
	public static final String APPSECRET = "appsecret";
	/** 头像路径 */
	public static final String META_PIC  = "meta_pic";
	/** 二维码路径 */
	public static final String QRCODE_PIC  = "qrcode_pic";
	/** 微信名称 */
	public static final String WEIXINNAME  = "weichatName";
	/** 微信号 */
	public static final String WEIXINNUMBER  = "weichatNumber";
	/** 类型 */
	public static final String TYPE = "type";
	/** 介绍 */
	public static final String INTRODUCE  = "introduce";
	/** 认证情况 */
	public static final String CERTIFICATION = "certification";
	/** 客服电话 */
	public static final String TELEPHONE = "telephone";
	/** 所在地址 */
	public static final String ADDRESS = "address";
	/** 主体信息 */
	public static final String INFORMATION = "information";
	/** 运营者 */
	public static final String OWNERUSER = "ownerUser";
	
	/** 登录邮箱 */
	public static final String LOGINEMAIL = "loginEmail";
	/** 原始ID */
	public static final String GHCODE = "ghCode";
	
	/**订阅页面的访问路径**/
	public static final String SUBSCRIBEURL = "subscribeUrl";
	
	/**项目ID匹配的应用路径**/
	public static final String MATCHPATH = "matchPath";
	/**项目ID匹配的Controller控制器。默认：com.cdvcloud.wx.service.wechat.coreImpl.CoreServiceImpl**/
	public static final String CLASSBEAN = "classBean";
	
	/** 启动时，是否进行初始化accessToken，1：初始化；0：不要进行初始化 **/
	public static final String INITACCESSTOKENFLAG = "initAccesstokenFlag";
	
	public static final String CREATEUSERID = "createUserId"; //创建者
	public static final String CREATE_TIME = "create_time"; //创建时间
	public static final String UPDATEUSERID = "updateUserId"; //更新者
	public static final String UPDATETIME = "updateTime"; //更新时间
	public static final String DELETE = "delete"; //1：已删除；0：未删除
	
	public static final String AUTHACCESSUSERINFO = "authAccessUserInfo"; //1：获取用户的昵称、性别等；0：不获取用户的昵称、性别等
	/**
	 * 授权方式。
	 	1:通过当前微信认证服务号/订阅号来收集.
		2:通过其他微信认证服务号/订阅号来收集.
		3:第三方客户代理授权后给与我们用户信息.
	 */
	public static final String AUTHTYPE = "authType";
	public static final String ANOTHERAPPID = "anotherAppId"; //其他服务号/订阅号的AppID
	public static final String ANOTHERAPPSECRET = "anotherAppSecret"; //其他服务号/订阅号的Secret
	public static final String PROXYURL = "proxyURL"; //第三方代理的URL
	
	public static final String TAG = "tag"; //标签名称
	
	public static final String RANGE = "range"; //所属租户群
}
