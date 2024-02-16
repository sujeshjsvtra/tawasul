package com.tawasul.web.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

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

	//@CreatedOn
	private LocalDateTime createdOn;

	private String status;
}
