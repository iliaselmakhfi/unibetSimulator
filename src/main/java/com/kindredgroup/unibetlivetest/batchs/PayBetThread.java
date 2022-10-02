package com.kindredgroup.unibetlivetest.batchs;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.repository.BetRepository;
import com.kindredgroup.unibetlivetest.repository.CustomerRepository;
import com.kindredgroup.unibetlivetest.types.BetState;
import com.kindredgroup.unibetlivetest.types.SelectionResult;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PayBetThread extends Thread {
	
	private BetRepository betRepository;
	private CustomerRepository customerRepository;
	
	private Selection s;
	private Bet b;
	
	@Autowired
	public PayBetThread(Selection s, Bet b,CustomerRepository customerRepository,BetRepository betRepository) {
		this.customerRepository = customerRepository;
		this.betRepository = betRepository;
		this.s = s;
		this.b = b;
	}
	
	/**
	 * Paiement d'un Bet de la selection
	 * 
	 */
	@Transactional
	public void run() {
		StopWatch timeMeasure = new StopWatch();
		timeMeasure.start("Task paiement du bet");
		Customer c = b.getCustomer();
		if (s.getResult().equals(SelectionResult.WON)) {
			c.setBalance(c.getBalance().add((b.getAmount().multiply(b.getOdd()))));
			b.setState(BetState.WON);
		} else {
			b.setState(BetState.LOST);
		}
		customerRepository.save(c);
		betRepository.save(b);
		timeMeasure.stop();
		log.info("Traitement du bet {} qui est ({}) , durée du traitement {} ms",b.getId(),b.getState().equals(BetState.LOST)?"perdant":"gagant",timeMeasure.getLastTaskTimeMillis() );
	}
	
}
