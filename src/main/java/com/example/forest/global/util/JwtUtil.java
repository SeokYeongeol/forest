package com.example.forest.global.util;

import com.example.forest.domain.member.role.MemberRole;
import com.example.forest.global.exception.ErrorCode;
import com.example.forest.global.exception.ServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

	private static final String BEARER_PREFIX = "Bearer ";
	
	@Value("${jwt.access.token}")
	private Long accessTokenTime;
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}
	
	public String createAccessToken(Long memberId, String loginId, String nickname, MemberRole role) {
		Date date = new Date();
		
		return BEARER_PREFIX + Jwts.builder()
			.setSubject(memberId.toString())
			.claim("loginId", loginId)
			.claim("nickname", nickname)
			.claim("role", role)
			.setExpiration(new Date(date.getTime() + accessTokenTime))
			.setIssuedAt(date)
			.signWith(key, signatureAlgorithm)
			.compact();
	}
	
	public String substringToken(String token) {
		if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
			return token.substring(BEARER_PREFIX.length());
		}
		throw new ServerException(ErrorCode.INVALID_TOKEN);
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
