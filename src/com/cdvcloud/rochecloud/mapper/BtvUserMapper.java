package com.cdvcloud.rochecloud.mapper;

import java.util.List;
import java.util.Map;

import com.cdvcloud.rochecloud.common.BaseMapper;
import com.cdvcloud.rochecloud.domain.BtvUser;

public interface BtvUserMapper extends BaseMapper<BtvUser, String> {
	
	int deleteByPrimaryKey(String id);

	int insert(BtvUser record);

	int insertSelective(BtvUser record);

	BtvUser selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(BtvUser record);

	int updateByPrimaryKey(BtvUser record);

	public BtvUser selectByloginIdAndPas(BtvUser user);

	public List<Map<String, Object>> getUsersByParam(Map<String, Object> paramMap);
}