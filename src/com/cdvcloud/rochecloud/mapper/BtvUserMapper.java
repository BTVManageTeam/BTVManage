package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.BaseMapper;
import com.cdvcloud.rochecloud.domain.BtvUser;

public interface BtvUserMapper   extends BaseMapper<BtvUser,String> {
    int deleteByPrimaryKey(String userId);

    int insert(BtvUser record);

    int insertSelective(BtvUser record);

    BtvUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(BtvUser record);

    int updateByPrimaryKey(BtvUser record);

    public BtvUser selectByloginIdAndPas(BtvUser user);

}