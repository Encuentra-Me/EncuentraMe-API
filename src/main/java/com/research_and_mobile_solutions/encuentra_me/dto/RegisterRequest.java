package com.research_and_mobile_solutions.encuentra_me.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email
    @NotBlank
    private String email;

    @Size(min = 6, message = "[Contraseña] Debe tener mínimo 6 caracteres")
    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String paternalLastName;

    @NotBlank
    private String maternalLastName;

    @NotBlank
    private String documentType;

    @NotBlank
    private String documentNumber;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "[Fecha de nacimiento] El formato debe ser DD/MM/YYYY")
    private String birthDate;

    @NotBlank
    private String countryCode;

    @Pattern(regexp = "^[0-9]{9}$", message = "[Celular] Debe tener 9 digitos")
    private String phone;

    @Pattern(regexp = "^[0-9]{6}$", message = "[Ubigeo] Debe tener 6 digitos")
    private String ubigeo;

    @NotBlank(message = "[Rol] El rol es requerido")
    private Long roleId;
} 