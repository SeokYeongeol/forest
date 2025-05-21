package com.example.forest.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotNull(message = "아이디를 입력해주세요.")
	private String loginId;
	
	@NotNull(message = "닉네임을 입력해주세요.")
	private String nickname;
	
	@NotNull(message = "비밀번호를 입력해주세요.")
	@Pattern(
		message = "비밀번호는 8~12자의 영문, 숫자, 특수문자를 포함해야 합니다.",
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$"
	)
	private String password;
	
	@NotNull(message = "비밀번호를 재입력 해주세요.")
	private String rePassword;
	
	@NotNull(message = "주소를 입력해주세요.")
	private String address;
	
	public boolean validPassword() { return this.password.equals(rePassword); }
}
