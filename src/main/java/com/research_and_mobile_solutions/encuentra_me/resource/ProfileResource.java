package com.research_and_mobile_solutions.encuentra_me.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResource {
    private Long id;
    private String firstName;
    private String paternalLastName;
    private String maternalLastName;
    private String documentType;
    private String documentNumber;
    private String birthDate;
    private String countryCode;
    private String phone;
    private String ubigeo;
    private String email; // from User
    private String roleName; // from User.Role
} 