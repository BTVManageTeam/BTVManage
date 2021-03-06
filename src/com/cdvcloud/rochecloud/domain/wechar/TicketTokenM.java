package com.cdvcloud.rochecloud.domain.wechar;

/**
 * 业务描述：微信ticket_token表
 * 注：进行主机和备机的部署时，accesstoken只能有一份，因此，通过数据库保存获取
 * 
 * @author chenchang
 *
 */
public class TicketTokenM {
	
	/**  数据库表  **/
	public static final String DB_COLLECTION = "wechat_ticket";
	
	/** mongodb 数据库的id主键 */
	public static final String ID = "_id";
	
	/** 获取到的凭证 **/
	public static final String TICKET = "ticket";
	
	/** 凭证有效时间，单位：秒 */
	public static final String EXPIRESIN = "expires_in";

	/** 创建日期 yyyy-MM-dd HH:mm:ss*/ 
	public static final String CREATE_TIME = "create_time";
	
	/** 生成accessToken的小时(24小时制) **/
	public static final String HOURS = "hours";
	
	/** 所属商项目ID 唯一**/
	public static final String PRODUCTID = "productId";
	
	private String id;
	private String ticket;
	private int expiresIn;
	private String createTime;
	private int hours;
	private String productId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
