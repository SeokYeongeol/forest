package com.example.forest.global.oauth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoogleMemberInfoDto {
	
	private final Long id;
	private final String nickname;
	private final String email;
}
