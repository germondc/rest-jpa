package test.clyde.restjpa.entities;

import java.util.Arrays;

public enum StudentType {
	UnderGrad("U"), PostGrad("P");

	private String code;

	private StudentType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static StudentType fromCode(String code) {
		return Arrays.stream(StudentType.values()).filter(st -> st.getCode().equals(code)).findAny()
				.orElseThrow(() -> new IllegalArgumentException("unknown student type code: " + code));
	}
}
