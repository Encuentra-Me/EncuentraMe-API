package com.research_and_mobile_solutions.encuentra_me.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FaceRecognitionService {

    private static final String AZURE_ENDPOINT = "https://ai-face-mp.cognitiveservices.azure.com";
    private static final String SUBSCRIPTION_KEY = "5mGMWvr7lStqrJdH7z2BU4sgDgszDI0Ml3gyMCiXYftMZIaMrjTxJQQJ99BDACYeBjFXJ3w3AAAKACOGx1KI";
    private static final String GROUP_ID = "grupo-personas";

    public String createPerson(String name) throws Exception {
        String url = AZURE_ENDPOINT + "/face/v1.0/persongroups/" + GROUP_ID + "/persons";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

        String json = "{\"name\":\"" + name + "\"}";
        request.setEntity(new StringEntity(json));

        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    public String addPersonFace(String personId, String base64Image) throws Exception {
        String url = AZURE_ENDPOINT + "/face/v1.0/persongroups/" + GROUP_ID + "/persons/" + personId + "/persistedFaces";
    
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
    
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
    
        request.setHeader("Content-Type", "application/octet-stream");
        request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
    
        ByteArrayEntity entity = new ByteArrayEntity(imageBytes);
        request.setEntity(entity);
    
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
    
}
