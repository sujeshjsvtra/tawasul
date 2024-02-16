package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LegalConsultation {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;

    private String role;

    @OneToOne
    private Sector sector;
    @OneToMany
    private List<Article> articles;

    //@CreatedOn
    private LocalDateTime createdOn;

    private String status;

}
