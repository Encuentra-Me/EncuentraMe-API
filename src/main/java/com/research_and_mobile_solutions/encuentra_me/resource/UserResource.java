package com.research_and_mobile_solutions.encuentra_me.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResource {
    private Long id; // de User
    private String email; // de User
    private String firstName;
    private String paternalLastName;
    private String maternalLastName;
    private String documentType;
    private String documentNumber;
    private String birthDate;
    private String countryCode;
    private String phone;
    private String ubigeo;
    private String roleName; // de Role
}
