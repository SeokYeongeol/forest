package com.example.forest.domain.member.dto.response;

import com.example.forest.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeNicknameResponse {
	
	private final Long id;
	private final String nickname;
	
	public static ChangeNicknameResponse of(Member member) {
		return new ChangeNicknameResponse(
			member.getId(),
			member.getNickname()
		);
	}
}
