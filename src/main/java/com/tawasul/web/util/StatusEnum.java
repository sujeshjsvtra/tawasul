package com.tawasul.web.util;

import lombok.ToString;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;

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

	public static String getByStatus(String status) {
		for (StatusEnum value : values()) {
			if (value.getStatus().equals(status)) {

				return value.getStatus().toLowerCase();
			}
		}
		return "Invalid";
	}

	public static String getDisplayNameByStatus(String status) {
		return Arrays.stream(values())
				.filter(enumConstant -> enumConstant.getStatus().equalsIgnoreCase(status))
				.findFirst()
				.map(StatusEnum::name) // Using name() to get the enum constant name
				.map(WordUtils::capitalizeFully)
				.orElse("Invalid");
	}
}
