package com.example.forest.global.entity;

import com.example.forest.domain.member.role.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class AuthUser {

	private final Long id;
	private final String email;
	private final String nickname;
	private final MemberRole role;
	private final List<? extends GrantedAuthority> authorities;
	
	public AuthUser(Long id, String email, String nickname, MemberRole role) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.role = role;
		this.authorities = List.of(new SimpleGrantedAuthority(role.name()));
	}
}
