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
public class Survey {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "reference_number")
	private String referenceNumber;

	@Column(name = "submission_date")
	private LocalDate submissionDate;

	private String email;

	private String gender;

	@Column(name = "age_group")
	private String ageGroup;

	private String location;

	@Column(name = "employment_type")
	private String employmentType;

	private String proposal;

	private Integer rating;

	@ManyToOne
	@JoinColumn(name = "consultation_id")
	private Consultation consultation;

	@OneToMany
	private List<File> files;

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
