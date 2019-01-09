package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<League> findByName(String name);

    @Query("SELECT l FROM League l WHERE l.name = :name AND l.id != :id")
    Optional<League> findByNameWhereIdIsNotEquals(@Param("name") String name, @Param("id") Long id);
}