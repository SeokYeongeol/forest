package com.example.forest.global.oauth.info;

import java.util.Map;

public interface OAuth2UserInfo {
	
	String getProviderId();
	String getEmail();
	String getNickname();
	Map<String, Object> getAttributes();
}
