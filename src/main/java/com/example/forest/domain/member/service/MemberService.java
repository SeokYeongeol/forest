package com.example.forest.domain.member.service;

import com.example.forest.domain.member.dto.request.ChangeAddressRequest;
import com.example.forest.domain.member.dto.request.ChangeNicknameRequest;
import com.example.forest.domain.member.dto.request.ChangePasswordRequest;
import com.example.forest.domain.member.dto.request.DeleteMemberRequest;
import com.example.forest.domain.member.dto.response.ChangeAddressResponse;
import com.example.forest.domain.member.dto.response.ChangeNicknameResponse;
import com.example.forest.domain.member.entity.Member;
import com.example.forest.domain.member.repository.MemberRepository;
import com.example.forest.global.entity.AuthUser;
import com.example.forest.global.exception.ErrorCode;
import com.example.forest.global.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	
	@Transactional
	public ChangeAddressResponse changeAddress(AuthUser authUser, ChangeAddressRequest request) {
		Member member = Member.fromAuth(authUser.getId());
		member.changeAddress(request.getAddress());
		
		return ChangeAddressResponse.of(member);
	}
	
	@Transactional
	public void changePassword(AuthUser authUser, ChangePasswordRequest request) {
		Member member = Member.fromAuth(authUser.getId());
		checkValidPassword(request.getOldPassword(), member.getPassword());
		
		if (request.getOldPassword().equals(request.getNewPassword())) {
			throw new ServerException(ErrorCode.PASSWORD_SAME_AS_OLD);
		}
		
		member.changePassword(passwordEncoder.encode(request.getNewPassword()));
	}
	
	@Transactional
	public ChangeNicknameResponse changeNickname(AuthUser authUser, ChangeNicknameRequest request) {
		if (memberRepository.existsByNickname(request.getNickname())) {
			throw new ServerException(ErrorCode.MEMBER_NICKNAME_DUPLICATION);
		}
		
		Member member = Member.fromAuth(authUser.getId());
		member.changeNickname(request.getNickname());
		return ChangeNicknameResponse.of(member);
	}
	
	@Transactional
	public void deleteMember(AuthUser authUser, DeleteMemberRequest request) {
		Member member = Member.fromAuth(authUser.getId());
		checkValidPassword(request.getPassword(), member.getPassword());
		member.deleteMember();
	}
	
	// 입력한 비밀번호와 해당 멤버의 비밀번호가 다를 시 예외처리
	private void checkValidPassword(String inputPassword, String memberPassword) {
		if (!passwordEncoder.matches(inputPassword, memberPassword)) {
			throw new ServerException(ErrorCode.INVALID_PASSWORD);
		}
	}
}
