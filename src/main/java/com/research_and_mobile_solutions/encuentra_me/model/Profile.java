package com.research_and_mobile_solutions.encuentra_me.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String paternalLastName;

    @Column(nullable = false)
    private String maternalLastName;

    @Column(nullable = false)
    private String documentType;

    @Pattern(regexp = "^[0-9]{8}$")
    @Column(nullable = false)
    private String documentNumber;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    @Column(nullable = false)
    private String birthDate;
    
    @Column(nullable = false)
    private String countryCode;

    @Pattern(regexp = "^[0-9]{9}$")
    @Column(nullable = false)
    private String phone;

    @Pattern(regexp = "^[0-9]{6}$")
    @Column(nullable = false)
    private String ubigeo;


    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
