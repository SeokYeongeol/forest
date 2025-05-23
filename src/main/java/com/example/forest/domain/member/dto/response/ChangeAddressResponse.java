package com.example.forest.domain.member.dto.response;

import com.example.forest.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChangeAddressResponse {
	
	private final Long id;
	private final String address;
	
	public static ChangeAddressResponse of(Member member) {
		return new ChangeAddressResponse(
			member.getId(),
			member.getAddress()
		);
	}
}
