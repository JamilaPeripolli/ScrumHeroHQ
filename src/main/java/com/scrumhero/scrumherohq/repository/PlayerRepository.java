package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}