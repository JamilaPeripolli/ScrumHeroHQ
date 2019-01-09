package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntergalacticMissionRepository extends JpaRepository<IntergalacticMission, Long> {

    Optional<IntergalacticMission> findByName(String name);

    @Query("SELECT i FROM IntergalacticMission i WHERE i.name = :name AND i.id != :id")
    Optional<IntergalacticMission> findByNameWhereIdIsNotEquals(@Param("name") String name, @Param("id") Long id);

}