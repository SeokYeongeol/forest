package com.example.forest.domain.auth.controller;

import com.example.forest.domain.auth.dto.request.SignUpRequest;
import com.example.forest.domain.auth.dto.response.AuthResponse;
import com.example.forest.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/v1/signup")
	public ResponseEntity<AuthResponse> signUpV1(
		@RequestBody SignUpRequest request
	) {
		return ResponseEntity.ok(authService.signUpV1(request));
	}
}
