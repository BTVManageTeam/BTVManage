package com.cdvcloud.rochecloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.domain.wechar.TicketTokenM;
import com.cdvcloud.rochecloud.mapper.TicketTokenMapper;
import com.cdvcloud.rochecloud.service.TicketTokenService;
@Service
public class TicketTokenServiceImpl implements TicketTokenService {

	
	@Autowired
	private TicketTokenMapper ticketTokenMapper;
	
	@Override
	public int addTicketTokenM(TicketTokenM params) {
		return ticketTokenMapper.addTicketTokenM(params);
	}

	@Override
	public int updateTicketTokenM(TicketTokenM params) {
		return ticketTokenMapper.updateTicketTokenM(params);
	}

	@Override
	public TicketTokenM findTicketTokenM(TicketTokenM whereParams) {
		return ticketTokenMapper.findTicketTokenM(whereParams);
	}


}
