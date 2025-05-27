package com.example.forest.global.oauth.info;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
	
	private final Map<String, Object> attrs;
	
	@Override
	public String getProviderId() { return attrs.get("id").toString(); }
	
	@Override
	public String getEmail() { return attrs.get("email").toString(); }
	
	@Override
	public String getNickname() { return attrs.get("nickname").toString(); }
	
	@Override
	public Map<String, Object> getAttributes() { return attrs; }
}
