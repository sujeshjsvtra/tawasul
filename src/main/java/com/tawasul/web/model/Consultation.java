package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Consultation {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String role;

    @OneToOne
    private Sector sector;

    @OneToMany
    private List<Survey> surveys;

    //@CreatedOn
    private LocalDateTime createdOn;

    private String status;

}
