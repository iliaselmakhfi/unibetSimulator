package com.kindredgroup.unibetlivetest.repository;

import com.kindredgroup.unibetlivetest.entity.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	/**
	 *
	 * Getting Live Events.
	 *
	 * @return List of Live Events.
	 */
	@Query("select e from Event e where TIMESTAMPDIFF(MINUTE,e.startDate,Sysdate) <= 5")
	List<Event> getLiveEvent();

	/**
	 *
	 * Getting Not Live Events.
	 *
	 * @return List of Not Live Events.
	 */
	@Query("select e from Event e where TIMESTAMPDIFF(MINUTE,e.startDate,Sysdate) > 5")
	List<Event> getNotLiveEvent();
}
