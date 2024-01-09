package com.bugflix.weblog.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 권한 문제가 발생했을 때 이 부분을 호출한다.
        response.setStatus(403);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        accessDeniedException.printStackTrace();
        response.getWriter().write("권한이 없는 사용자입니다.");
    }
}
