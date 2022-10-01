package com.kindredgroup.unibetlivetest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kindredgroup.unibetlivetest.dto.BetDto;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.service.CustomerService;
import com.kindredgroup.unibetlivetest.utils.Urls;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(Urls.BETS)
@CrossOrigin(origins = "*")
public class BetApi {

	@Autowired
	private final CustomerService customerService;

	@Autowired
	private final BetService betService;

	@PostMapping(Urls.ADD_BET)
	public void addBet(@RequestBody BetDto bet) {
		Customer customer = customerService.findCustomerByPseudo("unibest");
		log.debug("Check And add Bet for Selection {} and for Customer {}", bet.getSelectionId(), customer.getId());
		betService.checkAndAddBetForCustomer(bet, customer);
	}
}
