package com.example.forest.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String loginId;
	
	@Column(unique = true)
	private String nickname;
	
	private String password;
	private String address;
	
	@Builder
	public Member(String loginId, String nickname, String password, String address) {
		this.loginId = loginId;
		this.nickname = nickname;
		this.password = password;
		this.address = address;
	}
}
