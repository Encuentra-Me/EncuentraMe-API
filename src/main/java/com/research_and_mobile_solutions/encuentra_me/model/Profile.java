package com.research_and_mobile_solutions.encuentra_me.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="profiles")
@Getter
@Setter
public class Profile extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private DocumentType documentType;

    private Integer documentNumber;

    private Date birthday;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
