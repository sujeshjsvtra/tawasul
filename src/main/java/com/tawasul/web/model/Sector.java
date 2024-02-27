package com.tawasul.web.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ibm.db2.cmx.annotation.Required;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Sector {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

	private String arabicName;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	private String status;
}
