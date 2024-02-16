package com.tawasul.web.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String content;
}
