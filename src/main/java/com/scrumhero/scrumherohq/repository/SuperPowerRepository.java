package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.SuperPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperPowerRepository extends JpaRepository<SuperPower, Long> {

    Optional<SuperPower> findByName(String name);

    @Query("SELECT s FROM SuperPower s WHERE s.name = :name AND s.id != :id")
    Optional<SuperPower> findByNameWhereIdIsNotEquals(@Param("name") String name, @Param("id") Long id);

}