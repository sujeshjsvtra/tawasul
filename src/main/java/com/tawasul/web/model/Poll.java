package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
public class Poll {

	@Id
	@GeneratedValue
	private Long id;
	private String question;

	private String imageUrl;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@OneToMany
	private List<Sector> sectorAccess;

	@OneToMany
	private List<PollOption> pollOptions;

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
