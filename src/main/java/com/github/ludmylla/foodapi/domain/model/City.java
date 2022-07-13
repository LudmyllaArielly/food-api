package com.github.ludmylla.foodapi.domain.model;

import com.github.ludmylla.foodapi.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class City {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.StateId.class)
    @NotNull
    @ManyToOne
    private State state;
}
