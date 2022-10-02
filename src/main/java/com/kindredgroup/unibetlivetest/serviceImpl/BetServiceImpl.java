package com.kindredgroup.unibetlivetest.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.batchs.PayBetThread;
import com.kindredgroup.unibetlivetest.dto.BetDto;
import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.BetRepository;
import com.kindredgroup.unibetlivetest.repository.CustomerRepository;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

	private final BetRepository betRepository;

	private final CustomerRepository customerRepository;

	private final SelectionRepository selectionRepository;

	@Transactional
	public void checkAndAddBetForCustomer(BetDto betDto, Customer customer) throws CustomException {
		Selection selection = checkSelection(betDto);
		checkBetAndBalance(betDto, customer, selection);
		log.info("Save Bet for Customer {} and Selection {}", customer.getId(), selection.getId());
		// save bet
		betRepository.save(new Bet().setState(null).setAmount(betDto.getMise()).setOdd(betDto.getCote())
				.setCustomer(customer).setSelection(selection).setDate(new Date()));
		// Soustrait le montant de la mise de la balance du customer
		customerRepository.save(customer.setBalance(customer.getBalance().subtract(betDto.getMise())));
	}

	/**
	 * Vérifie s'il existe le même Paris et si la Balance du client est suffisante
	 * 
	 * @param bet       betDto envoyé par le client
	 * @param customer  Client qui souhaite ouvrir un Bet
	 * @param selection la selection concernée par le Bet
	 */
	private void checkBetAndBalance(BetDto betDto, Customer customer, Selection selection) {
		List<Bet> existingBet = betRepository.findByCustomerId(customer.getId());
		// l'utilisateur ne peut pas ouvrir plusieurs Bets sur la même Selection
		if (existingBet.stream().filter(b -> b.getState() == null && b.getSelection().equals(selection)).count() != 0) {
			throw new CustomException("Conflit, pari déjà en cours", ExceptionType.CONFLICT);
		}
		if (customer.getBalance().compareTo(betDto.getMise()) == -1) {
			throw new CustomException("Balance insufisante", ExceptionType.INSUFFUCIENT_BALANCE);
		}
	}

	/**
	 * Vérifie si la selection existe, au statut ouvert et que la côte n'a pas
	 * changé
	 * 
	 * @param bet betDto envoyé par le client
	 * @return la selection liée au Bet
	 */
	private Selection checkSelection(BetDto betDto) {
		Selection selection = selectionRepository.findById(Long.parseLong(betDto.getSelectionId()))
				.orElseThrow(() -> new CustomException("Requête mal formée", ExceptionType.SELECTION_NOT_FOUND));
		if (selection.getState().equals(SelectionState.CLOSED)) {
			throw new CustomException("Selection fermée", ExceptionType.SELECTION_CLOSED);
		}
		if (selection.getCurrentOdd().compareTo(betDto.getCote()) != 0) {
			throw new CustomException("Changement de cote", ExceptionType.ODD_CHANGE);
		}
		return selection;
	}

	/**
	 * Paiement des bets des selections terminées
	 * 
	 */
	public void PayBets() {
		ExecutorService pool = Executors.newCachedThreadPool();
		selectionRepository.getSelectionByState(SelectionState.CLOSED).parallelStream().forEach(s -> {
			s.getBets().parallelStream().filter(b -> b.getState() == null).forEach(b -> {
				try {
					Thread paybet = new PayBetThread(s, b, customerRepository, betRepository);
					pool.execute(paybet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		pool.shutdown();

	}

}
