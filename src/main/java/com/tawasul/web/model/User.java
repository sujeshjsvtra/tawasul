package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    @OneToMany
    private List<Sector> sectorAccess;

    private LocalDateTime createdAt;

    private String status;
}
