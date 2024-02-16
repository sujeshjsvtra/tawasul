package com.tawasul.web.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String author;
    private String comment;

    private String article;


    private Integer likeCount;

    private Integer dislikeCount;

    //@CreatedOn
    private LocalDateTime createdOn;


    private String status;

}
