package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvUser;
import com.cdvcloud.rochecloud.mapper.BtvUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理业务处理类
 *
 * @author lyh
 */

public interface UserService {


	/***
	 * 登录
	 * @param user
	 * @return
	 */
	BtvUser login(BtvUser user);

	/**
	 * 校验登录名唯一
	 * @param accountName
	 * @return
	 */
	List<BtvUser> selectByRepeatAccountName(String accountName);


	/**
	 * 增加用户
	 * @param record
	 * @return
	 */
	int insertSelective(BtvUser record);
}
