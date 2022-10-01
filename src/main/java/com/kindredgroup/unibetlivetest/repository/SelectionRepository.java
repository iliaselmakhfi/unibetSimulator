package com.kindredgroup.unibetlivetest.repository;

import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelectionRepository extends JpaRepository<Selection, Long> {

	List<Selection> getSelectionByState(SelectionState state);
    
    
    @Query("select s from Selection s where s.market.event.id = :eventId")
    List<Selection> getSelectionByEventId(Long eventId);
    
    @Query("select s from Selection s where s.market.event.id = :eventId and s.state = :state")
    List<Selection> getSelectionByEventIdAndState(Long eventId, SelectionState state);
}
