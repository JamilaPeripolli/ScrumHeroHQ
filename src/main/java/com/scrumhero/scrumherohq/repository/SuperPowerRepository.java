package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.SuperPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperPowerRepository extends JpaRepository<SuperPower, Long> {

}