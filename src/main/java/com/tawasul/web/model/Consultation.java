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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consultation_seq")
	@SequenceGenerator(name = "consultation_seq", sequenceName = "consultation_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	private String name;

	private String description;

	private String topic;

	@Column(unique = true, name = "name_arabic")
	private String nameArabic;

	@Column(name = "description_arabic")
	private String descriptionArabic;

	@Column(name = "topic_arabic")
	private String topicArabic;

	private Date startDate;

	private Date endDate;

	private String role;

	@OneToOne
	private File image;

	@OneToOne
	private Sector sector;

	@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
	//@JoinTable(name = "consultation_survey", joinColumns = @JoinColumn(name = "consultation_id"), inverseJoinColumns = @JoinColumn(name = "surveys_id"))
	//@JoinColumn(name = "consultation_id", referencedColumnName = "consultation_id") // Specify the referenced column
	@ToString.Exclude
	private Set<Survey> surveys = new HashSet<>();

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
