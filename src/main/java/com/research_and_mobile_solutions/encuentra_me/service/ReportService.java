package com.research_and_mobile_solutions.encuentra_me.service;

import com.research_and_mobile_solutions.encuentra_me.model.Report;
import com.research_and_mobile_solutions.encuentra_me.repository.ReportRepository;
import com.research_and_mobile_solutions.encuentra_me.resource.ReportResource;
import jakarta.transaction.Transactional;
import com.research_and_mobile_solutions.encuentra_me.dto.ReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;
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

    private boolean isImageUrlValid(String imageUrl){
        return (imageUrl != null && !imageUrl.isEmpty());
    }

    @Transactional
    public ReportResource createAndIndexReport(ReportRequest request, String collectionId) {
        
        Report report = new Report();
        mapDtoToEntity(request, report);

        Report savedReport = reportRepository.save(report);
        String externalId = String.valueOf(savedReport.getId());

        List<String> imageUrls = new ArrayList<>();
        if(isImageUrlValid(request.getImage1Url())) imageUrls.add(request.getImage1Url());
        if(isImageUrlValid(request.getImage2Url())) imageUrls.add(request.getImage2Url());
        if(isImageUrlValid(request.getImage3Url())) imageUrls.add(request.getImage3Url());
        if(isImageUrlValid(request.getImage4Url())) imageUrls.add(request.getImage4Url());
        if(isImageUrlValid(request.getImage5Url())) imageUrls.add(request.getImage5Url());
        if(isImageUrlValid(request.getImage6Url())) imageUrls.add(request.getImage6Url());

        // Indexar en AWS Rekognition usando image1Url y el ID como externalId
        try {
            for(String urlString: imageUrls){
                URL imageUrl = URI.create(urlString).toURL();
                rekognitionService.indexFaceFromUrl(
                    collectionId, 
                    imageUrl, 
                    externalId
                );

            }
        } catch (Exception e) {
            //throw new RuntimeException("Falló la indexación en AWS Rekognition El reporte no fue creado."+e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return convertToResource(savedReport);
    }
}