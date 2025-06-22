package com.research_and_mobile_solutions.encuentra_me.controller;

import java.util.List;

import com.research_and_mobile_solutions.encuentra_me.dto.ReportIndexRequest;
import com.research_and_mobile_solutions.encuentra_me.dto.ReportRequest;
import com.research_and_mobile_solutions.encuentra_me.model.Report;
import com.research_and_mobile_solutions.encuentra_me.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.research_and_mobile_solutions.encuentra_me.resource.ReportResource;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<ReportResource> getAllReports() {
        return reportService.getAllReports();
    }

    // GET report by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportResource> getReportById(@PathVariable Long id) {
        ReportResource report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    // POST new report
    @PostMapping
    public ResponseEntity<ReportResource> createReport(@RequestBody ReportRequest reportRequest) {
        ReportResource createdReport = reportService.createReport(reportRequest);
        return ResponseEntity.ok(createdReport);
    }   

    // PUT update report
    @PutMapping("/{id}")
    public ResponseEntity<ReportResource> updateReport(@PathVariable Long id, @RequestBody ReportRequest reportRequest) {
        ReportResource updatedReport = reportService.updateReport(id, reportRequest);
        return ResponseEntity.ok(updatedReport);
    }

    // DELETE report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    // PUT update report with new recognice
    @PutMapping("/{id}/reconocimiento")
    public ResponseEntity<ReportResource> updateReconocimiento(
            @PathVariable Long id,
            @RequestParam Double reconocimiento) {
        
        ReportResource updated = reportService.updateReconocimiento(id, reconocimiento);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/menores")
    public List<ReportResource> getMenores() {
        return reportService.getMenores();
    }

    @GetMapping("/mayores")
    public List<ReportResource> getMayores() {
        return reportService.getMayores();
    }

    @PostMapping("/index")
    public Report createAndIndexReport(@RequestBody ReportIndexRequest request) {
        return reportService.createAndIndexReport(request);
    }

    @PutMapping("/{id}/reset")
    public ResponseEntity<ReportResource> resetReport(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.resetReport(id));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ReportResource>> getReportsByStatus(
            @RequestParam String status) {
        List<ReportResource> results = reportService.getReportsByStatus(status);
        return ResponseEntity.ok(results);
    }

}
