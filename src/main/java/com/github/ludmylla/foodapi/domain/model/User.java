package com.github.ludmylla.foodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime registrationDate;

    @ManyToMany
    @JoinTable(name = "user_team",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams = new ArrayList<>();

    public boolean passwordMatchesWith(String password){
        return getPassword().equals(password);
    }

    public boolean passwordDoesNotMatch(String password){
        return !passwordMatchesWith(password);
    }

}
