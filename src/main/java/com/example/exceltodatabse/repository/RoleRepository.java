package com.example.exceltodatabse.repository;

import com.example.exceltodatabse.entity.Role;
import com.example.exceltodatabse.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
