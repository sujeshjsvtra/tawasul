package com.tawasul.web.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ibm.db2.cmx.annotation.JoinColumn;
import com.ibm.db2.cmx.annotation.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

 
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="email")
	private String email;
	@Column(name="otp")
	private String otp;
	@Column(name="created_at")
	private ZonedDateTime createdAt;
	@Column(name="expired_at")
	private ZonedDateTime expiredAt;
	@Column(name="is_deleted")
	private Integer isDeleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public ZonedDateTime getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(ZonedDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
}
