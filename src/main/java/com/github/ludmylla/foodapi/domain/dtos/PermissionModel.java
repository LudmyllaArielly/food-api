package com.github.ludmylla.foodapi.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionModel {

    private Long id;
    private String name;
    private String description;
}
