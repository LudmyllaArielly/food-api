package com.github.ludmylla.foodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Team> teams = new HashSet<>();

    public boolean passwordMatchesWith(String password){
        return getPassword().equals(password);
    }

    public boolean passwordDoesNotMatch(String password){
        return !passwordMatchesWith(password);
    }

    public boolean addTeam(Team team){
        return getTeams().add(team);
    }

    public boolean removeTeam(Team team){
        return getTeams().remove(team);
    }

}
