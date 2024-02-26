package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
public class Consultation {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String role;

	private String image_url;

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
