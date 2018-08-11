package com.cdvcloud.rochecloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.domain.BtvUser;
import com.cdvcloud.rochecloud.mapper.BtvUserMapper;

/**
 * 用户管理业务处理类
 * 
 * @author lyh
 * 
 */
@Service
public class UserService {

	@Autowired
	private BtvUserMapper btvUserMapper;

	public BtvUser login(BtvUser user) {
		return btvUserMapper.selectByloginIdAndPas(user);
	}
	
//
//	/**
//	 * 根据Id查询当前用户
//	 * 
//	 * @param userId
//	 *            用户Id
//	 * @return
//	 */
//	public OpRmsUser findUserById(String userId) {
//		return opRmsUserMapper.selectByPrimaryKey(userId);
//	}
//
//	/**
//	 * 添加本地用户
//	 * 
//	 * @param opRmsUser
//	 * @return
//	 */
//	public int saveUser(OpRmsUser opRmsUser) {
//		return opRmsUserMapper.insert(opRmsUser);
//	}
//
//	/**
//	 * 查询所有用户数量支持分页和条件查询
//	 * 
//	 * @param page
//	 * @return
//	 */
//	public int countFindAll(Pages<OpRmsUser> page) {
//		return opRmsUserMapper.countFindAll(page);
//	}
//
//	/**
//	 * 查询所有用户信息支持分页和条件查询
//	 * 
//	 * @param page
//	 * @return
//	 */
//	public List<OpRmsUser> selectFindAll(Pages<OpRmsUser> page) {
//		return opRmsUserMapper.selectFindAll(page);
//	}
//
//	/**
//	 * 根据用户id删除指定用户
//	 * 
//	 * @param id
//	 * @param request
//	 * @return
//	 */
//	public int deleteUsers(String id, HttpServletRequest request) {
//		OpRmsUser opRmsUser = new OpRmsUser();
//		opRmsUser.setUserId(id);
//		opRmsUser.setUserState(1);
//		return opRmsUserMapper.updateByPrimaryKeySelective(opRmsUser);
//	}
//
//	/**
//	 * 根据用户id查询指定用户
//	 * 
//	 * @param id
//	 * @param request
//	 * @return
//	 */
//	public OpRmsUser selectByUserId(String id, HttpServletRequest request) {
//		return opRmsUserMapper.selectByPrimaryKey(id);
//	}
//
//	/**
//	 * 修改用户信息
//	 * 
//	 * @param opRmsUser
//	 * @param request
//	 * @return
//	 */
//	public int updateUser(OpRmsUser opRmsUser, HttpServletRequest request) {
//		return opRmsUserMapper.updateByPrimaryKeySelective(opRmsUser);
//	}


}
