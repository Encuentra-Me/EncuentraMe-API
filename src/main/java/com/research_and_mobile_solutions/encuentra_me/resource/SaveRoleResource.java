package com.research_and_mobile_solutions.encuentra_me.resource;

import jakarta.persistence.Column;

public class SaveRoleResource {
    
    @Column(nullable = false, unique = true)
    private String name;
}
