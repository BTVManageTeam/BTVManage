package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.domain.wechar.TicketTokenM;


/**
 * 业务描述：微信TicketToken相关操作
 * 
 * @author chenchang
 *
 */
public interface TicketTokenService{
	
	
	int addTicketTokenM(TicketTokenM params);
	
	int updateTicketTokenM(TicketTokenM params);

	TicketTokenM findTicketTokenM(TicketTokenM whereParams);

}
