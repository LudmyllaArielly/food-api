package com.github.ludmylla.foodapi.domain.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordInputModel {

    @NotBlank
    private String passwordActual;

    @NotBlank
    private String newPassword;


}
