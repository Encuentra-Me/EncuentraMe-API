package com.research_and_mobile_solutions.encuentra_me.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "La fecha de nacimiento debe tener el formato DD/MM/YYYY")
    private String birthDate;

    @NotBlank
    private String countryCode;

    @Pattern(regexp = "^[0-9]{9}$", message = "El celular debe tener 9 digitos")
    private String phone;

    @Pattern(regexp = "^[0-9]{6}$", message = "El ubigeo debe tener 6 digitos")
    private String ubigeo;

    @NotNull(message = "El rol es requerido")
    private Long roleId;
} 