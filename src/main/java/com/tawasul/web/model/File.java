package com.tawasul.web.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
	@SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
	private Long id;

	private String name;

	private String url;

	private String type;

	private String module;

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
