package com.ssafy.starflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.starflix.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			System.out.println("token : " + token);
			if (jwtUtil.checkToken(token)) {
				// 토큰이 유효한 경우, 필요 시 사용자 정보를 설정할 수 있습니다.
				request.setAttribute("userId", jwtUtil.getUserId(token));
				System.out.println("userId : " + request.getAttribute("userId"));
				return true;
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
				return false;
			}
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Missing or invalid Authorization header");
			return false;
		}
	}

}
