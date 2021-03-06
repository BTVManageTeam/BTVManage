package com.cdvcloud.rochecloud.mdomain;

/**
 * 业务描述：接收微信消息
 * 
 * @author zhangyuelong
 *
 */
public class XyWechatMessage{
	
	/**  数据库表  **/
	public static final String XYWECHATMESSAGE = "xy_wechatMessage";
	/** 公众号appid**/
	public static final String ID = "_id";
	/** 公众号appid**/
	public static final String TOWECHATAPPID = "toWechatAppId";
	 /** c端用户id*/
    public static final String OPENID = "openId";
	/** 发送方帐号（一个OpenID）  */
	public static final String FROMWECHATID = "fromWechatId";
	/** 消息类型 文本消息[text]/图片消息[image]/语音消息[voice]/视频消息[video]/小视频[shortvideo]/地理位置消息[location]/链接消息[link]  */
	public static final String MSGTYPE = "msgType";
	/** 消息Id **/
	public static final String MSGID = "msgId";

	/** 消息类型----------文本消息[text]  */
	public static final String CONTENT = "content"; 

	/** 消息类型----------图片消息[image]、语音消息[voice]、视频消息[video]和小视频[shortvideo]  */
	/** 消息媒体id，可以调用多媒体文件下载接口拉取数据  **/
	public static final String MEDIAID = "mediaId"; 
	
	public static final String MEDIALOCALURL = "mediaLocalUrl"; //下载到本地后的连接地址
	
	/** 图片消息[image] 图片链接  **/
	public static final String PICURL = "picUrl"; 
	
	/** 语音消息[voice] 语音格式，如amr，speex等  **/
	public static final String FORMAT = "format";

	/** 视频消息[video]和小视频[shortvideo] 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据  **/
	public static final String THUMBMEDIAID = "thumbMediaId";
	
	public static final String THUMBMEDIALOCALURL = "thumbMediaLocalUrl"; //下载到本地后的连接地址
	
	/** 地理位置消息[location] **/
	public static final String LOCATION_X = "location_X"; 
	public static final String LOCATION_Y  = "location_Y "; 
	/** 地图缩放大小  **/
	public static final String SCALE = "scale";
	/** 地理位置信息   **/
	public static final String LABEL = "label";
	
	
	/** 链接消息[link] **/
	public static final String TITLE = "title"; 
	public static final String DESCRIPTION  = "description"; 
	public static final String Url = "url";
	
	/**回复状态  值为1表示已经回复；值为0表示未回复**/
	public static final String REPLAYED = "replayed";
	
	/** 消息级别：值为1表示是接收微信消息；如果回复用户消息，则pid为一级消息的_id值 */
	public static final String PID = "pid";

	/*---------------------------*/
	/** 用户昵称 */
	public static final String NICKNAME = "nickname";
	/** 用户头像 */
	public static final String HEADIMGURL = "headimgurl";
	
	/** 所属商项目ID 唯一**/
	public static final String PRODUCTID = "productId";
	/** 回复消息类型----------文本消息[text]  */
	public static final String REPLAYCONTENT = "replayContent"; 
    /** 更新人主键： */
    public static final String USERID = "userId";
    /** 更新人*/
    public static final String USERNAME = "userName";
    /** 创建时间 */
    public static final String CTIME = "ctime";

	/** 是否是卡片，1卡片 2为评论 */
	public static final String CARD = "card";
}
