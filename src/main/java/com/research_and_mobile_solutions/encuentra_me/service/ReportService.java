package com.research_and_mobile_solutions.encuentra_me.service;

import com.research_and_mobile_solutions.encuentra_me.model.Report;
import com.research_and_mobile_solutions.encuentra_me.repository.ReportRepository;
import com.research_and_mobile_solutions.encuentra_me.resource.ReportResource;
import com.research_and_mobile_solutions.encuentra_me.dto.ReportIndexRequest;
import com.research_and_mobile_solutions.encuentra_me.dto.ReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.net.URL;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RekognitionService rekognitionService;

    public List<ReportResource> getAllReports() {
        return reportRepository.findAll().stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
    }

    public ReportResource getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        return convertToResource(report);
    }

    public ReportResource createReport(ReportRequest reportRequest) {
        Report report = new Report();
        mapDtoToEntity(reportRequest, report);
        Report savedReport = reportRepository.save(report);
        return convertToResource(savedReport);
    }

    public ReportResource updateReport(Long id, ReportRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        mapDtoToEntity(request, report);
        Report updatedReport = reportRepository.save(report);
        return convertToResource(updatedReport);
    }

    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new RuntimeException("Report not found with id: " + id);
        }
        reportRepository.deleteById(id);
    }

    private ReportResource convertToResource(Report report) {
    return new ReportResource(  
        report.getId(),
        report.getName(),
        report.getLastName(),
        report.getStatus(),
        report.getAge(),
        report.getBornCountry(),
        report.getLastSeen(),
        report.getPlaceLastSeen(),
        report.getAlertNoteUrl(),
        report.getImage1Url(),
        report.getImage2Url(),
        report.getImage3Url(),
        report.getImage4Url(),
        report.getImage5Url(),
        report.getImage6Url(),
        report.getTez(),
        report.getSangre(),
        report.getContextura(),
        report.getEstatura(),
        report.getCabello(),
        report.getBoca(),
        report.getOjos(),
        report.getNariz(),
        report.getReconocimiento()
    );
}

   private void mapDtoToEntity(ReportRequest request, Report report) {
    report.setName(request.getName());
    report.setLastName(request.getLastName());
    report.setAge(request.getAge());
    report.setBornCountry(request.getBornCountry());
    report.setLastSeen(request.getLastSeen());
    report.setPlaceLastSeen(request.getPlaceLastSeen());
    report.setAlertNoteUrl(request.getAlertNoteUrl());

    report.setImage1Url(request.getImage1Url());
    report.setImage2Url(request.getImage2Url());
    report.setImage3Url(request.getImage3Url());
    report.setImage4Url(request.getImage4Url());
    report.setImage5Url(request.getImage5Url());
    report.setImage6Url(request.getImage6Url());

    report.setTez(request.getTez());
    report.setSangre(request.getSangre());
    report.setContextura(request.getContextura());
    report.setEstatura(request.getEstatura());
    report.setCabello(request.getCabello());
    report.setBoca(request.getBoca());
    report.setOjos(request.getOjos());
    report.setNariz(request.getNariz());
    }

    public ReportResource updateReconocimiento(Long id, Double reconocimiento) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        
        report.setStatus("Encontrado");
        report.setReconocimiento(reconocimiento);

        Report updated = reportRepository.save(report);
        return convertToResource(updated);
    }

    public List<ReportResource> getMenores() {
    return reportRepository.findByAgeLessThan(18)
            .stream()
            .map(this::convertToResource)
            .toList();
    }

    public List<ReportResource> getMayores() {
        return reportRepository.findByAgeGreaterThanEqual(18)
                .stream()
                .map(this::convertToResource)
                .toList();
    }

    public Report createAndIndexReport(ReportIndexRequest request) {
        // Crear y guardar Report en DB
        Report report = new Report();
        report.setName(request.getName());
        report.setLastName(request.getLastName());
        report.setAge(request.getAge());
        report.setBornCountry(request.getBornCountry());
        report.setLastSeen(request.getLastSeen());
        report.setPlaceLastSeen(request.getPlaceLastSeen());
        report.setTez(request.getTez());
        report.setSangre(request.getSangre());
        report.setContextura(request.getContextura());
        report.setEstatura(request.getEstatura());
        report.setCabello(request.getCabello());
        report.setBoca(request.getBoca());
        report.setOjos(request.getOjos());
        report.setNariz(request.getNariz());
        report.setAlertNoteUrl(request.getAlertNoteUrl());
        report.setImage1Url(request.getImage1Url());

        Report savedReport = reportRepository.save(report);

        // Indexar en AWS Rekognition usando image1Url y el ID como externalId
        try {
            URL imageUrl = new URL(request.getImage1Url());
            rekognitionService.indexFaceFromUrl(request.getCollectionId(), imageUrl, String.valueOf(savedReport.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error al indexar la imagen en Rekognition", e);
        }

        return savedReport;
    }

    public ReportResource resetReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        report.setStatus("Desaparecido");
        report.setReconocimiento(0.0);

        Report updated = reportRepository.save(report);
        return convertToResource(updated);
    }

    public List<ReportResource> getReportsByStatus(String status) {
        List<Report> reports = reportRepository.findByStatus(status);
        return reports.stream()
                .map(this::convertToResource)
                .toList();
    }
}