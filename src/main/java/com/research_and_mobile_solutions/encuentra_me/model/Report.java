package com.research_and_mobile_solutions.encuentra_me.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String alertNoteUrl;

    private String name;
    private String lastName;

    @Column
    private String status = "Desaparecido";

    private Integer age;
    private String bornCountry;
    private String lastSeen;
    private String placeLastSeen;

    // URLs de imágenes
    @Column(name = "image1url")
    private String image1Url;

    @Column(name = "image2url")
    private String image2Url;

    @Column(name = "image3url")
    private String image3Url;

    @Column(name = "image4url")
    private String image4Url;

    @Column(name = "image5url")
    private String image5Url;

    @Column(name = "image6url")
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
    @Column
    private Double reconocimiento = 0.00;
}   
