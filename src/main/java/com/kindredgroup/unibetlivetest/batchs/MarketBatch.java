package com.kindredgroup.unibetlivetest.batchs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.kindredgroup.unibetlivetest.service.BetService;

@Component
@Log4j2
@RequiredArgsConstructor
public class MarketBatch {

	@Autowired
	private final BetService betService;

	@Scheduled(fixedRate = 5000)
	public final void payerLesParisBatch() {
		StopWatch timeMeasure = new StopWatch();
		timeMeasure.start("Task paiement des paris");
		log.debug("Exécution du batch de paiement des paris.");
		betService.PayBets();
		timeMeasure.stop();
		log.debug("fin d'excécution du batch de paiement des paris, durée d'excécution {} ms",
				+timeMeasure.getLastTaskTimeMillis());
	}

}
