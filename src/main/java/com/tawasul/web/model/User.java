package com.tawasul.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

//import org.apache.deltaspike.data.api.audit.CreatedOn;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "public")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String email;

	private String password;

	private String role;

	@OneToMany
	@JoinTable(name = "user_sector", joinColumns = { @JoinColumn(name = "sector_access_id") }, inverseJoinColumns = {
			@JoinColumn(name = "") })
	private List<Sector> sectorAccess;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createDate;

	private String status;
}
