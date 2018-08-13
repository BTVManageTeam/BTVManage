package com.cdvcloud.rochecloud.domain.wechar;

import java.io.Serializable;

import com.cdvcloud.wechat.bean.AccessToken;
import com.cdvcloud.wechat.bean.Ticket;

/**
 * 业务描述：微信key信息记录表
 * 
 * @author chenchang
 *
 */
public class WechatKeyVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/** mongodb 数据库的id主键 */
	private String id ;

	/** AppID */
	private String appid;
	
	/** AppSecret */
	private String appsecret;
	
	/** 创建日期 yyyy-MM-dd HH:mm:ss*/ 
	private String create_time;
	
	/** 所属商项目ID 唯一**/
	private String productId;
	
	/** 所属商项目描述**/
	private String productDesc;
	
	/**项目的中文名称**/
	private String productName;
	
	/**订阅页面的访问路径**/
	private String subscribeUrl;
	
	/**匹配路径**/
	private String matchPath;
	
	private AccessToken accessToken;
	
	private Ticket ticket;
	
	private String classBean; //每一个productId，对应一个Bean的CoreController
	
	/**当前素材分类列表，json串。如{asd:全国两会,gwe:湖南两会,adsf:经视新闻}**/
	private String classIds;
	/**
	 * 头像路径
	 */
	private String meta_pic;
	/**
	 * 二维码路径
	 */
	private String qrcode_pic;
	/**
	 * 微信号
	 */
	private String weixinnumber;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 介绍
	 */
	private String introduce;
	/**
	 * 认证情况
	 */
	private String certification;
	/**
	 * 客服电话
	 */
	private String telephone;
	
	/**
	 * 所在地址
	 */
	private String address;
	
	/**
	 * 主体信息
	 */
	private String information;
	
	private String initAccessTokenFlag;
	
	private String probability;
	
	private Integer isdel;
	
	private String tag;
	
	private String range;
	
	private String companyId;
	
	private String appType; //应用类型
	
	public Integer getIsdel() {
		return isdel;
	}


	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}


	public String getMeta_pic() {
		return meta_pic;
	}


	public void setMeta_pic(String meta_pic) {
		this.meta_pic = meta_pic;
	}

	public String getQrcode_pic() {
		return qrcode_pic;
	}

	public void setQrcode_pic(String qrcode_pic) {
		this.qrcode_pic = qrcode_pic;
	}

	public String getWeixinnumber() {
		return weixinnumber;
	}

	public void setWeixinnumber(String weixinnumber) {
		this.weixinnumber = weixinnumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubscribeUrl() {
		return subscribeUrl;
	}

	public void setSubscribeUrl(String subscribeUrl) {
		this.subscribeUrl = subscribeUrl;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getMatchPath() {
		return matchPath;
	}

	public void setMatchPath(String matchPath) {
		this.matchPath = matchPath;
	}

	public String getClassBean() {
		return classBean;
	}

	public void setClassBean(String classBean) {
		this.classBean = classBean;
	}

	public String getClassIds() {
		return classIds;
	}

	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}

	public String getInitAccessTokenFlag() {
		return initAccessTokenFlag;
	}

	public void setInitAccessTokenFlag(String initAccessTokenFlag) {
		this.initAccessTokenFlag = initAccessTokenFlag;
	}
	
	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getRange() {
		return range;
	}


	public void setRange(String range) {
		this.range = range;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getAppType() {
		return appType;
	}


	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	
}
