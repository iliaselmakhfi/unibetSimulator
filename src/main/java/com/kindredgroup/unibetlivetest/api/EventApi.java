package com.kindredgroup.unibetlivetest.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kindredgroup.unibetlivetest.entity.Event;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.EventRepository;
import com.kindredgroup.unibetlivetest.service.EventService;
import com.kindredgroup.unibetlivetest.service.SelectionService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.utils.Urls;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(Urls.BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventApi {

	private final EventService eventService;

	private final EventRepository eventRepository;

	private final SelectionService selectionService;

	/**
	 * Récupére une liste d'Event.
	 *
	 * @param isLive indique si les events qu'on souhaite rechercher sont en direct
	 *               ou pas ( paramétre optionnel )
	 * @return List d'events
	 */
	@GetMapping(value = Urls.EVENTS)
	public List<Event> getEvents(@RequestParam(value = "isLive", required = false) Boolean isLive) {
		log.debug("Getting Events");
		List<Event> events = eventService.getEvents(isLive);
		return events;
	}

	/**
	 * Récupére une liste de Selections liée à un Event.
	 *
	 * @param isLive indique si les events qu'on souhaite rechercher sont en direct
	 *               ou pas
	 * @param state  indique si les events qu'on souhaite rechercher sont en direct
	 *               ou pas
	 * @return List d'events
	 */
	@GetMapping(Urls.SELECTIONS)
	public List<Selection> getSelection(@PathVariable("id") Long eventId,
			@RequestParam(value = "state", required = false) SelectionState state) {
		log.debug("Getting Selection for Event {}", eventId);
		eventRepository.findById(eventId)
				.orElseThrow(() -> new CustomException("Evénement introuvable", ExceptionType.EVENT_NOT_FOUND));
		return selectionService.getEventSelection(eventId, state);
	}

}
