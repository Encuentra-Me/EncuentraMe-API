package com.research_and_mobile_solutions.encuentra_me.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String alertNoteUrl;

    private String image1Url;
    private String image2Url;
    private String image3Url;
    private String image4Url;
    private String image5Url;
}
