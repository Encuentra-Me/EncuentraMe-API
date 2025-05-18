package com.research_and_mobile_solutions.encuentra_me.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.research_and_mobile_solutions.encuentra_me.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
} 