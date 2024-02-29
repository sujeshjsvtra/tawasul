package com.tawasul.web.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Consultation {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;

	private String description;

	private String topic;

	private LocalDate startDate;

	private LocalDate endDate;

	private String role;

	@OneToOne
	private File image;

	@OneToOne
	private Sector sector;

	@OneToMany
	private List<Survey> surveys;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	private String status;
}
