package com.kindredgroup.unibetlivetest.serviceImpl;

import com.kindredgroup.unibetlivetest.entity.Event;
import com.kindredgroup.unibetlivetest.repository.EventRepository;
import com.kindredgroup.unibetlivetest.service.EventService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	public List<Event> getEvents(Boolean isLive) {
		return isLive == null ? eventRepository.findAll()
				: isLive ? eventRepository.getLiveEvent() : eventRepository.getNotLiveEvent();
	}

}
