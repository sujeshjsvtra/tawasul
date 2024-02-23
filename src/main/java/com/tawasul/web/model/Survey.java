package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
public class Survey {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String gender;

    private String ageCategory;
    @OneToOne
    private Sector location;

    @OneToOne
    private Sector employmentType;

    private String proposal;

    @OneToMany
    private List<File> files;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    private String status;
}
