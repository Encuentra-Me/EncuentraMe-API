package com.research_and_mobile_solutions.encuentra_me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.research_and_mobile_solutions.encuentra_me.dto.RegisterRequest;
import com.research_and_mobile_solutions.encuentra_me.model.Profile;
import com.research_and_mobile_solutions.encuentra_me.model.User;
import com.research_and_mobile_solutions.encuentra_me.repository.IProfileRepository;

@Service
public class ProfileService {
    
    @Autowired
    private IProfileRepository profileRepository;

    public Profile registerProfile(RegisterRequest request, User user){

        Profile profile = Profile.builder()
            .firstName(request.getFirstName())
            .paternalLastName(request.getPaternalLastName())
            .maternalLastName(request.getMaternalLastName())
            .documentType(request.getDocumentType())
            .documentNumber(request.getDocumentNumber())
            .birthDate(request.getBirthDate())
            .countryCode(request.getCountryCode())
            .phone(request.getPhone())
            .ubigeo(request.getUbigeo())
            .user(user)
            .build();
            
        return profileRepository.save(profile);
    }

}
