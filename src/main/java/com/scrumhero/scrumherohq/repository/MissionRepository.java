package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.Mission;
import com.scrumhero.scrumherohq.model.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByName(String name);

    @Query("SELECT m FROM Mission m INNER JOIN m.intergalacticMission im " +
            "WHERE m.name = :name AND im.id = :imId AND m.id != :id")
    Optional<Mission> findDuplicatedResource(@Param("name") String name,
                                                   @Param("id") Long id, @Param("imId") Long imId);
}