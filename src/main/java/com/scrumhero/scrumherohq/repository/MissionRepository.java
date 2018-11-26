package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

}