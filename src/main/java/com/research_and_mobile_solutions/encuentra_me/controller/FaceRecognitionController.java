package com.research_and_mobile_solutions.encuentra_me.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.research_and_mobile_solutions.encuentra_me.service.FaceRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import com.research_and_mobile_solutions.encuentra_me.dto.AddPersonFaceRequest;


@RestController
@RequestMapping("/api/face")
public class FaceRecognitionController {
    
    private static final String AZURE_ENDPOINT = "https://ai-face-mp.cognitiveservices.azure.com";
    private static final String SUBSCRIPTION_KEY = "5mGMWvr7lStqrJdH7z2BU4sgDgszDI0Ml3gyMCiXYftMZIaMrjTxJQQJ99BDACYeBjFXJ3w3AAAKACOGx1KI";

    @Autowired
    private FaceRecognitionService faceService;
    
    @PostMapping("/create-person-group")
    public ResponseEntity<String> createPersonGroup() {
        try {
            String groupId = "grupo-personas";
            String url = AZURE_ENDPOINT + "/face/v1.0/persongroups/" + groupId;

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut request = new HttpPut(url);

            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

            StringEntity body = new StringEntity("{\"name\":\"Grupo Principal\"}");
            request.setEntity(body);

            HttpResponse response = httpClient.execute(request);
            String responseText = EntityUtils.toString(response.getEntity());

            return new ResponseEntity<>("Grupo creado: " + responseText, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-person")
    public ResponseEntity<String> createPerson(@RequestParam String name) {
        try {
            String result = faceService.createPerson(name);
            return ResponseEntity.ok("Persona creada: " + result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/add-person-face")
    public ResponseEntity<String> addPersonFace(@RequestBody AddPersonFaceRequest request) {
    try {
        String result = faceService.addPersonFace(request.getPersonId(), request.getImageBase64());
        return ResponseEntity.ok("Rostro agregado: " + result);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
    }
}
}
