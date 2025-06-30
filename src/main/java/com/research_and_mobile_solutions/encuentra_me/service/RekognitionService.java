package com.research_and_mobile_solutions.encuentra_me.service;

import com.research_and_mobile_solutions.encuentra_me.dto.SimilarityResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.io.InputStream;
import java.net.URL;


@Service
public class RekognitionService {

    private final RekognitionClient rekognitionClient;

    public RekognitionService() {
        this.rekognitionClient = RekognitionClient.builder()
                .region(Region.US_EAST_1) // Cambia si usas otra región
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public void createCollection(String collectionId) {
        CreateCollectionRequest request = CreateCollectionRequest.builder()
                .collectionId(collectionId)
                .build();

        CreateCollectionResponse response = rekognitionClient.createCollection(request);
        //System.out.println("Colección creada: " + response.collectionArn());
    }

    public void indexFace(String collectionId, MultipartFile image, String externalId) {
        try {
            ByteBuffer imageBytes = ByteBuffer.wrap(image.getBytes());

            Image awsImage = Image.builder()
                    .bytes(SdkBytes.fromByteBuffer(imageBytes))
                    .build();

            // Validación previa con detectFaces()
            DetectFacesRequest detectRequest = DetectFacesRequest.builder()
                    .image(awsImage)
                    .attributes(Attribute.DEFAULT)
                    .build();

            DetectFacesResponse detectResponse = rekognitionClient.detectFaces(detectRequest);
            if (detectResponse.faceDetails().isEmpty()) {
                throw new RuntimeException("No se detectó ningún rostro en la imagen a indexar.");
            }

            IndexFacesRequest request = IndexFacesRequest.builder()
                    .collectionId(collectionId)
                    .image(awsImage)
                    .externalImageId(externalId)
                    .detectionAttributesWithStrings("DEFAULT")
                    .build();

            rekognitionClient.indexFaces(request);

        } catch (RekognitionException e) {
            System.err.println("Error de Rekognition al indexar: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Error al indexar la imagen: " + e.statusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error general al indexar la imagen", e);
        }
    }

    public List<SimilarityResponse> searchFace(String collectionId, MultipartFile image) {
        try {
            ByteBuffer imageBytes = ByteBuffer.wrap(image.getBytes());

            Image awsImage = Image.builder()
                    .bytes(SdkBytes.fromByteBuffer(imageBytes))
                    .build();

            // Validación previa con detectFaces()
            DetectFacesRequest detectRequest = DetectFacesRequest.builder()
                    .image(awsImage)
                    .attributes(Attribute.DEFAULT)
                    .build();

            DetectFacesResponse detectResponse = rekognitionClient.detectFaces(detectRequest);
            if (detectResponse.faceDetails().isEmpty()) {
                throw new RuntimeException("No se detectó ningún rostro en la imagen.");
            }

            SearchFacesByImageRequest request = SearchFacesByImageRequest.builder()
                    .collectionId(collectionId)
                    .image(awsImage)
                    .faceMatchThreshold(80F)
                    .maxFaces(3)
                    .build();

            SearchFacesByImageResponse response = rekognitionClient.searchFacesByImage(request);

            return response.faceMatches().stream()
                    .map(match -> new SimilarityResponse(
                            match.face().externalImageId(),
                            match.similarity()
                    ))
                    .collect(Collectors.toList());

        } catch (RekognitionException e) {
            System.err.println("Error de Rekognition al buscar coincidencias: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Error al buscar coincidencias faciales: " + e.statusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error general al procesar la imagen", e);
        }
    }

    public List<Map<String, String>> listFaces(String collectionId) {
        ListFacesRequest request = ListFacesRequest.builder()
                .collectionId(collectionId)
                .maxResults(1000)
                .build();

        ListFacesResponse response = rekognitionClient.listFaces(request);

        return response.faces().stream()
                .map(face -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("externalId", face.externalImageId());
                    map.put("faceId", face.faceId());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public void deleteCollection(String collectionId) {
        DeleteCollectionRequest request = DeleteCollectionRequest.builder()
                .collectionId(collectionId)
                .build();

        rekognitionClient.deleteCollection(request);
    }

    public void indexFaceFromUrl(String collectionId, URL imageUrl, String externalId) {
    try (InputStream inputStream = imageUrl.openStream()) {
        byte[] imageBytesArray = inputStream.readAllBytes();
        ByteBuffer imageBytes = ByteBuffer.wrap(imageBytesArray);

        Image awsImage = Image.builder()
                .bytes(SdkBytes.fromByteBuffer(imageBytes))
                .build();

        // Validación previa con detectFaces()
        DetectFacesRequest detectRequest = DetectFacesRequest.builder()
                .image(awsImage)
                .attributes(Attribute.DEFAULT)
                .build();

        DetectFacesResponse detectResponse = rekognitionClient.detectFaces(detectRequest);
        if (detectResponse.faceDetails().isEmpty()) {
            throw new RuntimeException("No se detectó ningún rostro en la imagen desde URL.");
        }

        IndexFacesRequest request = IndexFacesRequest.builder()
                .collectionId(collectionId)
                .image(awsImage)
                .externalImageId(externalId)
                .detectionAttributesWithStrings("DEFAULT")
                .build();

        rekognitionClient.indexFaces(request);

    } catch (RekognitionException e) {
        System.err.println("Error de Rekognition al indexar desde URL: " + e.awsErrorDetails().errorMessage());
        throw new RuntimeException("Error al indexar desde URL: " + e.statusCode(), e);
    } catch (Exception e) {
        throw new RuntimeException("Error general al indexar desde URL", e);
    }
}
}
