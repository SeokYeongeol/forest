package com.example.forest.domain.member.entity;

import com.example.forest.domain.member.role.MemberRole;
import com.example.forest.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamped {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String loginId;
	
	@Column(unique = true)
	private String nickname;
	
	private String password;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	private LocalDateTime deletedAt;
	
	@Builder
	public Member(String loginId, String nickname, String password, String address, MemberRole role) {
		this.loginId = loginId;
		this.nickname = nickname;
		this.password = password;
		this.address = address;
		this.role = role;
	}
	
	public void deleteMember() {
		this.deletedAt = LocalDateTime.now();
	}
}
