package com.kindredgroup.unibetlivetest.serviceImpl;

import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.service.SelectionService;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.utils.Helpers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class SelectionServiceImpl implements SelectionService {

	private final SelectionRepository selectionRepository;

	/**
	 * 1. Récupère toute les selections ouvertes 2. Mis à jour la cote aléatoirement
	 */

	public Long updateOddsRandomly() {
		final List<Selection> selectionsOpened = selectionRepository.getSelectionByState(SelectionState.OPENED);
		return selectionsOpened.isEmpty() ? 0
				: selectionsOpened.stream()
						.map(selection -> selection.setCurrentOdd(Helpers.updateOddRandomly(selection.getCurrentOdd())))
						.map(selectionRepository::save).count();
	}

	/**
	 * 1. Récupère toute les selections ouvertes 2. Ferme 5 sélections
	 * aléatoirement.
	 */

	public Long closeOddsRandomly() {
		final List<Selection> selectionsOpened = selectionRepository.getSelectionByState(SelectionState.OPENED);
		if(!selectionsOpened.isEmpty()) {	
			List<Selection> selections = new ArrayList<>();
			for(int i=0;i<5;i++) {
				selections.add(selectionsOpened.get(Helpers.getRandomIndex(0, selectionsOpened.size()))
						.setState(SelectionState.CLOSED).setResult(Helpers.setResultRandomly()));
			}
			selectionRepository.saveAll(selections);
		}	
		return selectionsOpened.isEmpty() ? 0 : 5l;
	}

	/**
	 * Récupére la list des selections liés à un event avec un state donné
	 * 
	 * @param eventId l'id de l'event concerné
	 * @param state   l'état des selections souhaitées ( donnée optionnelle )
	 * @return la list des selections lié à l'event , avec le state renseigné ,
	 *         sinon on récupére tout par défaut
	 */
	public List<Selection> getEventSelection(Long eventId, SelectionState state) {
		return state == null ? selectionRepository.getSelectionByEventId(eventId)
				: selectionRepository.getSelectionByEventIdAndState(eventId, state);
	}

}
