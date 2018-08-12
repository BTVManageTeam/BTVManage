package com.cdvcloud.rochecloud.common;

import java.io.Serializable;

/**
 * Token错误状态
 * 
 * @author LX
 */
public enum ReturnState implements Serializable {
	//成功
	success(0, "success", "success"),
	//失败
	failure(1, "failure", "failure"),
	//标识不存在
	id_not_exist(20001, "Id is not exist", "Id is not exist"),
	//参数不合法
	parameter_invalid(20002, "Parameter invalid", "Parameter invalid"),
	// 系统内部错误                                                                                                                            
	system_internal(20003, "Inner error", "Inner error"),
	// 未知错误                                                                                                                            
	unknow_error(20006, "Unknow error", "Unknow error"),
	//AccessToken验证
	accessToken_failure(1, "AccessToken failure", "AccessToken failure"),;
	
	public Integer status; // 状态值 
	public String detail; // 状态描述信息
	public String enDetail;

	private ReturnState(Integer status, String detail, String enDetail) {
		this.status = status;
		this.detail = detail;
		this.enDetail = enDetail;
	}

	@Override
	public String toString() {
		return "{status:" + this.status + ",detail:" + this.detail + "}";
	}
}
