package com.example.forest.global.oauth.service;

import com.example.forest.domain.member.entity.Member;
import com.example.forest.domain.member.repository.MemberRepository;
import com.example.forest.domain.member.role.MemberRole;
import com.example.forest.global.oauth.details.PrincipalDetails;
import com.example.forest.global.oauth.info.GoogleOAuth2UserInfo;
import com.example.forest.global.oauth.info.KakaoOAuth2UserInfo;
import com.example.forest.global.oauth.info.NaverOAuth2UserInfo;
import com.example.forest.global.oauth.info.OAuth2UserInfo;
import com.example.forest.global.oauth.provider.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final MemberRepository memberRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
		
		String registrationId = userRequest
			.getClientRegistration()
			.getRegistrationId()
			.toUpperCase();
		AuthProvider authProvider = AuthProvider.valueOf(registrationId);
		
		OAuth2UserInfo userInfo = switch(authProvider) {
			case GOOGLE -> new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
			case NAVER -> new NaverOAuth2UserInfo(oAuth2User.getAttributes());
			case KAKAO -> new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
		};
		
		if (userInfo.getEmail() == null) {
			throw new OAuth2AuthenticationException("Email Not Found from OAuth2 Provider");
		}
		
		Member member = memberRepository.findByProviderTypeAndProviderId(authProvider, userInfo.getProviderId())
			.orElseGet(() -> registerNewMember(authProvider, userInfo));
		
		return new PrincipalDetails(member, oAuth2User.getAttributes());
	}
	
	private Member registerNewMember(AuthProvider authProvider, OAuth2UserInfo userInfo) {
		Member member = Member.builder()
			.email(userInfo.getEmail())
			.nickname(userInfo.getNickname())
			.password("")
			.address("")
			.role(MemberRole.ROLE_MEMBER)
			.providerType(authProvider)
			.providerId(userInfo.getProviderId())
			.build();
		memberRepository.save(member);
		
		return member;
	}
}
