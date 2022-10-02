package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.dto.BetDto;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.exception.CustomException;

public interface BetService {

	/**
	 * Vérifie la posibilité d'enregistrer un Bet et l'enregistre si c'est le cas
	 * 
	 * @param bet       betDto envoyé par le client
	 * @param customer  Client qui souhaite ouvrir un Bet
	 * @param selection la selection concernée par le Bet
	 */
	void checkAndAddBetForCustomer(BetDto betDto, Customer customer) throws CustomException;

	/**
	 * Paiement des bets des selections terminées
	 * 
	 */
	void PayBets();

}
