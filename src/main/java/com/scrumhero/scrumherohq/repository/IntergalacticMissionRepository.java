package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntergalacticMissionRepository extends JpaRepository<IntergalacticMission, Long> {

}