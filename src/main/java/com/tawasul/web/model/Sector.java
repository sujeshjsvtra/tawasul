package com.tawasul.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Sector {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String status;
	private LocalDateTime createdAt;
}
