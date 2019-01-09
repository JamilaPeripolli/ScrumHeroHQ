package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.EvolutionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvolutionItemRepository extends JpaRepository<EvolutionItem, Long> {

    @Query("SELECT ei FROM EvolutionItem ei INNER JOIN ei.league l " +
            "WHERE ei.title = :title AND l.id = :leagueId")
    Optional<EvolutionItem> findByTitleAndLeagueId(@Param("title") String title, @Param("leagueId") Long leagueId);

    @Query("SELECT ei FROM EvolutionItem ei INNER JOIN ei.league l " +
            "WHERE ei.title = :title AND l.id = :leagueId AND ei.id != :id")
    Optional<EvolutionItem> findDuplicatedResource(@Param("title") String title,
                                             @Param("id") Long id, @Param("leagueId") Long leagueId);
}