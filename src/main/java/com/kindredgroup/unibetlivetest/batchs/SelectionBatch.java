package com.kindredgroup.unibetlivetest.batchs;

import com.kindredgroup.unibetlivetest.service.SelectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class SelectionBatch {

	@Autowired
    private final SelectionService selectionService;

    @Scheduled(fixedRate = 1000 * 5)
    public final void updateOddsRandomly() {
		log.info("{} selection(s) updated randomly.", selectionService.updateOddsRandomly());
    }

    @Scheduled(fixedRate = 1000 * 60)
    public final void closeOddsRandomly() {
        log.info("{} selections(s) closed randomly.", selectionService.closeOddsRandomly());
    }

}
