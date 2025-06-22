package com.research_and_mobile_solutions.encuentra_me.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.research_and_mobile_solutions.encuentra_me.model.Report;

import java.util.Optional;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByAlertNoteUrl(String alertNoteUrl);
    List<Report> findByAgeLessThan(int age);
    List<Report> findByAgeGreaterThanEqual(int age);
    List<Report> findByStatus(String status);  
}
