package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.wechar.TicketTokenM;

public interface TicketTokenMapper {
    int addTicketTokenM(TicketTokenM accessToken);

    int updateTicketTokenM(TicketTokenM accessToken);

    TicketTokenM findTicketTokenM(TicketTokenM accessToken);

}