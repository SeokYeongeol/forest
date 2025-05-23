package com.example.forest.domain.member.controller;

import com.example.forest.domain.member.dto.request.ChangeAddressRequest;
import com.example.forest.domain.member.dto.request.ChangeNicknameRequest;
import com.example.forest.domain.member.dto.request.ChangePasswordRequest;
import com.example.forest.domain.member.dto.request.DeleteMemberRequest;
import com.example.forest.domain.member.dto.response.ChangeAddressResponse;
import com.example.forest.domain.member.dto.response.ChangeNicknameResponse;
import com.example.forest.domain.member.service.MemberService;
import com.example.forest.global.entity.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	private final MemberService memberService;
	
	@PatchMapping("/v1/members/address")
	public ResponseEntity<ChangeAddressResponse> changeAddress(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody ChangeAddressRequest request
	) {
		return ResponseEntity.ok(memberService.changeAddress(authUser, request));
	}
	
	@PatchMapping("/v1/members/password")
	public ResponseEntity<String> changePassword(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody ChangePasswordRequest request
	) {
		memberService.changePassword(authUser, request);
		return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
	}
	
	@PatchMapping("/v1/members/nickname")
	public ResponseEntity<ChangeNicknameResponse> changeNickname(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody ChangeNicknameRequest request
	) {
		return ResponseEntity.ok(memberService.changeNickname(authUser, request));
	}
	
	@DeleteMapping("/v1/members/delete")
	public ResponseEntity<String> deleteMember(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody DeleteMemberRequest request
	) {
		memberService.deleteMember(authUser, request);
		return ResponseEntity.ok().body("해당 멤버가 삭제되었습니다.");
	}
}
