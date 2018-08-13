package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.wechar.AccessTokenM;

public interface AccessTokenMapper {
    int addAccessTokenM(AccessTokenM accessToken);

    int updateAccessTokenM(AccessTokenM accessToken);

    AccessTokenM findAccessTokenM(AccessTokenM accessToken);

}