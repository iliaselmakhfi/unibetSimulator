package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import java.util.List;

public interface SelectionService {

	/**
	 * 1. Récupère toute les selections ouvertes 2. Mis à jour la cote aléatoirement
	 */

	Long updateOddsRandomly();

	/**
	 * 1. Récupère toute les selections ouvertes 2. Ferme 5 sélections
	 * aléatoirement.
	 */

	Long closeOddsRandomly();

	/**
	 * Récupére la list des selections liés à un event avec un state donné
	 * 
	 * @param eventId l'id de l'event concerné
	 * @param state   l'état des selections souhaitées ( donnée optionnelle )
	 * @return la list des selections lié à l'event , avec le state renseigné ,
	 *         sinon on récupére tout par défaut
	 */
	List<Selection> getEventSelection(Long eventId, SelectionState state);

}
