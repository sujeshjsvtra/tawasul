package com.tawasul.web.model;

import 	java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode
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

/*	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(
			name = "consultation_survey",
			joinColumns = @JoinColumn(name = "consultation_id"),
			inverseJoinColumns = @JoinColumn(name = "survey_id")
	)
	private Set<Survey> survey= new HashSet<>();*/

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
