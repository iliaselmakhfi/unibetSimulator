package com.kindredgroup.unibetlivetest.serviceImpl;

import com.kindredgroup.unibetlivetest.dto.BetDto;
import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.BetRepository;
import com.kindredgroup.unibetlivetest.repository.CustomerRepository;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.types.BetState;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

	private final BetRepository betRepository;

	private final CustomerRepository customerRepository;

	private final SelectionRepository selectionRepository;

	@Transactional
	public void checkAndAddBetForCustomer(BetDto bet, Customer customer) throws CustomException {
		Selection selection = checkSelection(bet);
		checkBetAndBalance(bet, customer, selection);
		log.info("Save Bet for Customer {} and Selection {}", customer.getId(), selection.getId());
		// save bet
		betRepository.save(new Bet().setState(null).setAmount(bet.getMise()).setOdd(bet.getCote()).setCustomer(customer)
				.setSelection(selection).setDate(new Date()));
		// Soustrait le montant de la mise de la balance du customer
		customerRepository.save(customer.setBalance(customer.getBalance().subtract(bet.getMise())));
	}

	/**
	 * Vérifie s'il existe le même Paris et si la Balance du client est suffisante
	 * 
	 * @param bet       betDto envoyé par le client
	 * @param customer  Client qui souhaite ouvrir un Bet
	 * @param selection la selection concernée par le Bet
	 */
	private void checkBetAndBalance(BetDto bet, Customer customer, Selection selection) {
		List<Bet> existingBet = betRepository.findByCustomerId(customer.getId());
		if (existingBet.stream().filter(b -> b.getState() == null).count() != 0) {
			throw new CustomException("Conflit, pari déjà en cours", ExceptionType.CONFLICT);
		}
		if (customer.getBalance().compareTo(bet.getMise()) == -1) {
			throw new CustomException("Balance insusante", ExceptionType.INSUFFUCIENT_BALANCE);
		}
	}

	/**
	 * Vérifie si la selection existe, est au statut ouvert et que la côte n'a pas
	 * changé
	 * 
	 * @param bet betDto envoyé par le client
	 * @return la selection liée au Bet
	 */
	private Selection checkSelection(BetDto bet) {
		Selection selection = selectionRepository.findById(Long.parseLong(bet.getSelectionId()))
				.orElseThrow(() -> new CustomException("Requête mal formée", ExceptionType.SELECTION_NOT_FOUND));
		if (selection.getState().equals(SelectionState.CLOSED)) {
			throw new CustomException("Selection fermée", ExceptionType.SELECTION_CLOSED);
		}
		if (selection.getCurrentOdd().compareTo(bet.getCote()) != 0) {
			throw new CustomException("Changement de cote", ExceptionType.ODD_CHANGE);
		}
		return selection;
	}

	/**
	 * Paiement des bets des selections terminées
	 * 
	 */
	public void PayBets() {
		selectionRepository.getSelectionByState(SelectionState.CLOSED).parallelStream().forEach(s -> {
			s.getBets().parallelStream().filter(b -> b.getState() == null).forEach(b -> {
				log.info("Traitement du Bet {} de la selection {}", b.getId(), s.getId());
				Customer c = b.getCustomer();
				try {
					PayBet(s, b, c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				customerRepository.save(c);
				betRepository.save(b);
			});
		});
	}

	@Transactional
	private void PayBet(Selection s, Bet b, Customer c) {
		if (s.getResult().equals(SelectionResult.WON)) {
			log.info("Paris {} gagant, paiement du client {}", b.getId(), c.getId());
			c.setBalance(c.getBalance().add(b.getAmount()) // Ajout du montant parié
					.add((b.getAmount().multiply(b.getOdd())))); // Ajout des gains
			b.setState(BetState.WON);
		} else {
			log.info("Paris {} perdant", b.getId());
			b.setState(BetState.LOST);
		}
	}

}
