package com.backend.springboot.tickets.repository;

import com.backend.springboot.tickets.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    //Steelseries
    Optional<Developer> findByEmail(String email);

    Optional<Developer> findByUsername(String username);

}
