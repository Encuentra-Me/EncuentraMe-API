package com.research_and_mobile_solutions.encuentra_me.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.research_and_mobile_solutions.encuentra_me.model.Profile;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, Long> {
} 