package com.tawasul.web.util;

import lombok.ToString;

@ToString
public enum StatusEnum {

	OPEN("O"), CLOSED("C"), DELETED("D");

	private String status;

	StatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
