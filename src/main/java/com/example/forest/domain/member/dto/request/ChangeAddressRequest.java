package com.example.forest.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeAddressRequest {
	
	@NotNull(message = "주소를 입력해주세요.")
	private String address;
}
