package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}