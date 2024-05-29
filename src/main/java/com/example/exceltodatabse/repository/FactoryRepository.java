package com.example.exceltodatabse.repository;

import com.example.exceltodatabse.entity.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
    Boolean existsByInvNumber(String username);
    Optional<Factory> findByInvNumber(String invNumber);

}
