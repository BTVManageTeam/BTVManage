package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvLawyer;

public interface BtvLawyerMapper {
    int deleteByPrimaryKey(String lawyerId);

    int insert(BtvLawyer record);

    int insertSelective(BtvLawyer record);

    BtvLawyer selectByPrimaryKey(String lawyerId);

    int updateByPrimaryKeySelective(BtvLawyer record);

    int updateByPrimaryKey(BtvLawyer record);


    /**
     * 根据用户ID查询律师信息
     * @param userId
     * @return
     */
    BtvLawyer findLawyerByUserId(String userId);
}