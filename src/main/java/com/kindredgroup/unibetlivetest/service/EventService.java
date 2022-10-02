package com.kindredgroup.unibetlivetest.service;

import java.util.List;

import com.kindredgroup.unibetlivetest.entity.Event;

public interface EventService {

	/**
	 * Récupére la list des Events Live ou non Live 
	 * @param isLive indique si les event qu'on souhaite recherchés sont Live ou pas 
	 * @return List des events
	 */
	public List<Event> getEvents(Boolean isLive);

}
