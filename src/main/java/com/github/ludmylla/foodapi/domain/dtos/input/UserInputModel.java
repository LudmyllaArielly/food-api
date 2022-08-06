package com.github.ludmylla.foodapi.domain.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInputModel {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
