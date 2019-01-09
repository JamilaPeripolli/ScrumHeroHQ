package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t INNER JOIN t.intergalacticMission im " +
            "WHERE t.title = :title AND im.id = :imId")
    Optional<Task> findByTitleAndIntergalacticMissionId(@Param("title") String title, @Param("imId") Long leagueId);

    @Query("SELECT t FROM Task t INNER JOIN t.intergalacticMission im " +
            "WHERE t.title = :title AND im.id = :imId AND t.id != :id")
    Optional<Task> findDuplicatedResource(@Param("title") String title,
                                                   @Param("id") Long id, @Param("imId") Long imId);
}