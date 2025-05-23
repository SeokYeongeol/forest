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
	private String email;
	
	@Column(unique = true)
	private String nickname;
	
	private String password;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	private LocalDateTime deletedAt;
	
	@Builder
	public Member(String email, String nickname, String password, String address, MemberRole role) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.address = address;
		this.role = role;
	}
	
	public void changeAddress(String address) {
		this.address = address;
	}
	
	public void changePassword(String password) {
		this.password = password;
	}
	
	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void deleteMember() {
		this.deletedAt = LocalDateTime.now();
	}
	
	private Member(Long id) {
		this.id = id;
	}
	
	public static Member fromAuth(Long authId) {
		return new Member(authId);
	}
}
