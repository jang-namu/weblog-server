package com.bugflix.weblog.config;

import com.bugflix.weblog.config.security.JwtAccessDeniedHandler;
import com.bugflix.weblog.config.security.JwtAuthenticationEntryPoint;
import com.bugflix.weblog.security.JwtAuthenticationFilter;
import com.bugflix.weblog.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] PERMIT_TO_USER = {
            "/api/users/test",
            "/api/auth/reissue"
    };

    private static final String[] SWAGGER_URL_ARRAY = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/webjars/**",
    };

    private static final String[] PERMIT_TO_ALL = {
            "/api/users",
            "/api/auth/login",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic().disable()
                .csrf().disable()

                // CORS 설정
                .cors().configurationSource(corsConfigurationSource())

                // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 조건별로 요청 허용/제한 설정
                .and()
                .authorizeRequests()

                .requestMatchers(PERMIT_TO_USER).hasRole("USER")
                .requestMatchers(PERMIT_TO_ALL).permitAll()
                .requestMatchers(SWAGGER_URL_ARRAY).permitAll()
                .anyRequest().authenticated() // .anyRequest().denyAll() -> FilterChain.doFilter(...) AccessDeniedException

                // JWT 인증 필터 적용
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

                // 에러 핸들링
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /*
        CorsConfig - https://toycoms.tistory.com/37
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cors 허용 패턴
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
