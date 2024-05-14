package com.ssafy.starflix.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {

	@Value("${jwt.salt}")
	private String salt;

	@Value("${jwt.refresh-token.expiretime}")
	private long accessTokenExpireTime;

	@Value("${jwt.refresh-token.expiretime}")
	private long refreshTokenExpireTime;

	public String createAccessToken(String userId) {
		return create(userId, "access-token", accessTokenExpireTime);
	}

	// AccessToken에 비해 유효기간을 길게 설정.
	public String createRefreshToken(String userId) {
		return create(userId, "refresh-token", refreshTokenExpireTime);
	}

	// Token 발급
	// key : Claim에 세팅할 key 값
	// value : Claim에 세팅할 data 값
	// subject : payload에 sub의 value로 들어갈 subject 값
	// expire : 토큰 유효기간 설정을 위한 값
	// jwt 토큰의 구성 : header + payload + signature
	private String create(String userId, String subject, long expireTime) {
		// payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration)
		// token 제목 (subject), 데이터(claim) 등 정보 세팅.
		Claims claims = Jwts.claims().setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expireTime));
		// 저장할 data의 key, value
		claims.put("userId", userId);

		String jwt = Jwts.builder()
				// header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
				.setHeaderParam("typ", "JWT")
				.setClaims(claims)
				.signWith(generateKey(), SignatureAlgorithm.HS256)
				.compact(); // 직렬화 처리
		
		log.info("jwt : {}", jwt);
		return jwt;
	}

	// Signature 설정에 들어갈 key 생성
	private Key generateKey() {
		byte[] keyBytes = null;
		// charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩 됨.
		keyBytes = salt.getBytes(StandardCharsets.UTF_8);
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
	}
	
	//	전달 받은 토큰이 제대로 생성된 것인지 확인 하고 문제가 있다면 UnauthorizedException 발생.
	public boolean checkToken(String token) {
		try {
//			Json Web Signature? 서버에서 인증을 근거로 인증 정보를 서버의 private key 서명 한것을 토큰화 한것
//			setSigningKey : JWS 서명 검증을 위한  secret key 세팅
//			parseClaimsJws : 파싱하여 원본 jws 만들기
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token);
//			Claims 는 Map 구현체 형태
			log.debug("claims: {}", claims);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public String getUserId(String authorization) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(authorization);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("계정 권한이 유효하지 않습니다. 다시 로그인해주세요.");
		}
		Map<String, Object> value = claims.getBody();
		log.info("value : {}", value);
		return (String) value.get("userId");
	}
}
