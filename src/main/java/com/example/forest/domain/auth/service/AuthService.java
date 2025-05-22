package com.example.forest.domain.auth.service;

import com.example.forest.domain.auth.dto.request.LoginRequest;
import com.example.forest.domain.auth.dto.request.SignUpRequest;
import com.example.forest.domain.auth.dto.response.AuthResponse;
import com.example.forest.domain.member.entity.Member;
import com.example.forest.domain.member.repository.MemberRepository;
import com.example.forest.global.exception.ErrorCode;
import com.example.forest.global.exception.ServerException;
import com.example.forest.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public AuthResponse signUpV1(SignUpRequest request) {
		if (memberRepository.existsByEmail(request.getEmail())) {
			throw new ServerException(ErrorCode.MEMBER_EMAIL_DUPLICATION);
		}
		
		if (memberRepository.existsByNickname(request.getNickname())) {
			throw new ServerException(ErrorCode.MEMBER_NICKNAME_DUPLICATION);
		}
		
		if (!request.validPassword()) {
			throw new ServerException(ErrorCode.INVALID_PASSWORD);
		}
		
		Member member = Member.builder()
			.email(request.getEmail())
			.nickname(request.getNickname())
			.password(passwordEncoder.encode(request.getPassword()))
			.address(request.getAddress())
			.role(request.getRole())
			.build();
		memberRepository.save(member);
		
		return new AuthResponse(jwtUtil.createAccessToken(member));
	}
	
	@Transactional
	public AuthResponse loginV1(LoginRequest request) {
		Member member = memberRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new ServerException(ErrorCode.MEMBER_NOT_FOUND));
		
		if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
			throw new ServerException(ErrorCode.INVALID_PASSWORD);
		}
		
		return new AuthResponse(jwtUtil.createAccessToken(member));
	}
}
