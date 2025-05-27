package com.example.forest.global.oauth.details;

import com.example.forest.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {
	
	private final Member member;
	private Map<String, Object> attrs;
	
	public PrincipalDetails(Member member, Map<String, Object> attrs) {
		this.member = member;
		this.attrs = attrs;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(member.getRole().toString()));
	}
	
	@Override
	public String getPassword() { return member.getPassword(); }
	
	@Override
	public String getUsername() { return member.getNickname(); }
	
	@Override
	public boolean isAccountNonExpired() { return true; }
	
	@Override
	public boolean isAccountNonLocked() { return true; }
	
	@Override
	public boolean isCredentialsNonExpired() { return true; }
	
	@Override
	public boolean isEnabled() { return true; }
	
	@Override
	public Map<String, Object> getAttributes() { return attrs; }
	
	@Override
	public String getName() {
		Object providerId = attrs.get("sub");
		
		if (providerId == null) {
			providerId = attrs.get("id");
		}
		return providerId.toString();
	}
}
