package com.kindredgroup.unibetlivetest.batchs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kindredgroup.unibetlivetest.service.BetService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarketBatch {

	@Autowired
	private final BetService betService;

	@Scheduled(fixedRateString = "${job.payBet.Scheduled}")
	public final void payerLesParisBatch() {
		betService.PayBets();
	}

}
