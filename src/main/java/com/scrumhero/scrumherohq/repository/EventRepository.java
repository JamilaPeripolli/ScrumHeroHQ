package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}