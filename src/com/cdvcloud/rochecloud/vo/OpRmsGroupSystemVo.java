package com.cdvcloud.rochecloud.vo;

import java.util.Date;

/**
 * 用户组和应用系统
 * 
 * @author syd
 * 
 */
public class OpRmsGroupSystemVo {
	/** 用户组id */
	private String groupId;

	/** 应用系统id */
	private String systemId;

	/** 用户组名称 */
	private String groupName;

	/** 系统名称 */
	private String systemName;

	/** 用户组描述 */
	private String groupDesc;

	/** 创建人 */
	private String createUserName;

	/** 创建时间 */
	private Date createTime;

	/** 用户组中用户数量 */
	private Integer secount;

	private String ownerBusName;
	
	/** 过期时间 */
	private Date expireTime;

	/** 用户组状态( 0：正常 1：已过期 2：已释放) */
	private int type;

	/** 华为续费过期的实例ID */
	private String instanceId;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public Integer getSecount() {
		return secount;
	}

	public void setSecount(Integer secount) {
		this.secount = secount;
	}

	public String getOwnerBusName() {
		return ownerBusName;
	}

	public void setOwnerBusName(String ownerBusName) {
		this.ownerBusName = ownerBusName;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	
}