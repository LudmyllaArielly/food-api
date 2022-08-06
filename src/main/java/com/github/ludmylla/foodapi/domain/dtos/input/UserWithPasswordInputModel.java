package com.github.ludmylla.foodapi.domain.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithPasswordInputModel extends UserInputModel {

    @NotBlank
    private String password;

}
