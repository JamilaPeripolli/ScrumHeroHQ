package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.SuperPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperPowerRepository extends JpaRepository<SuperPower, Long> {

    Optional<SuperPower> findByName(String name);

}