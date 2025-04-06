package com.research_and_mobile_solutions.encuentra_me.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research_and_mobile_solutions.encuentra_me.resource.ReportResource;

@RestController
@RequestMapping("/api/v1")
public class ReportController {

    @GetMapping("/reports")
    public List<ReportResource> getAllReports(){
        return List.of(new ReportResource());
    }
}
