package com.example.forest.domain.auth.service;

import com.example.forest.domain.auth.dto.request.SignUpRequest;
import com.example.forest.domain.auth.dto.response.AuthResponse;
import com.example.forest.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	
	@Transactional
	public AuthResponse signUpV1(SignUpRequest request) {
		return null;
	}
}
