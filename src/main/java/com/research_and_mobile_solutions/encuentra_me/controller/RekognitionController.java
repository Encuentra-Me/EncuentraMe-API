package com.research_and_mobile_solutions.encuentra_me.controller;

import com.research_and_mobile_solutions.encuentra_me.dto.SimilarityResponse;
import com.research_and_mobile_solutions.encuentra_me.service.RekognitionService;
import lombok.RequiredArgsConstructor;
//import software.amazon.awssdk.services.rekognition.model.DeleteCollectionRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rekognition")
@RequiredArgsConstructor
public class RekognitionController {

    private final RekognitionService rekognitionService;

    @PostMapping("/collection/{collectionId}")
    public ResponseEntity<String> createCollection(@PathVariable String collectionId) {
        rekognitionService.createCollection(collectionId);
        return ResponseEntity.ok("Colección creada: " + collectionId);
    }

    @PostMapping(value = "/index-face/{collectionId}",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> indexFace(
            @PathVariable String collectionId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("externalId") String externalId) {
        rekognitionService.indexFace(collectionId, image, externalId);
        return ResponseEntity.ok("Rostro indexado correctamente");
    }

    @PostMapping(value = "/search-face/{collectionId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<SimilarityResponse>> searchFace(
            @PathVariable String collectionId,
            @RequestParam("image") MultipartFile image) {
        List<SimilarityResponse> results = rekognitionService.searchFace(collectionId, image);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/faces/{collectionId}")
    public ResponseEntity<List<Map<String, String>>> listFaces(@PathVariable String collectionId) {
        return ResponseEntity.ok(rekognitionService.listFaces(collectionId));
    }
    
    @DeleteMapping("/collection/{collectionId}")
    public ResponseEntity<String> deleteCollection(@PathVariable String collectionId) {
        rekognitionService.deleteCollection(collectionId);
        return ResponseEntity.ok("Colección eliminada: " + collectionId);
    }
}
