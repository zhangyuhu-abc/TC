package com.maven.tc.interceptor;

import com.maven.tc.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"请先登录\"}");
            return false;
        }

        token = token.substring(7); // 去掉 "Bearer " 前缀

        if (JwtUtils.isTokenExpired(token)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"登录已过期，请重新登录\"}");
            return false;
        }

        Claims claims = JwtUtils.parseToken(token);
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("role", claims.get("role"));

        return true;
    }
}