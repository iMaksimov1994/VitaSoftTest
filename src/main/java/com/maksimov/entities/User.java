package com.maksimov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(name = "user_name", unique = true)
    private String userName;
    @NonNull
    @Column(name = "password")
    private String password;
    @NonNull
    @Column(name = "user_roles")
    private String roles;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Requisition> requisitions = new ArrayList<>();


}
