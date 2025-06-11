package com.research_and_mobile_solutions.encuentra_me.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {

    private String name;
    private String lastName;
    //private String status;
    private Integer age;
    private String bornCountry;
    private String lastSeen;
    private String placeLastSeen;
    private String alertNoteUrl;

    // URLs de imágenes
    private String image1Url;
    private String image2Url;
    private String image3Url;
    private String image4Url;
    private String image5Url;
    private String image6Url;

    // Datos físicos
    private String tez;
    private String sangre;
    private String contextura;
    private String estatura;
    private String cabello;
    private String boca;
    private String ojos;
    private String nariz;

    // Reconocimiento AI
    //private Double reconocimiento;
}