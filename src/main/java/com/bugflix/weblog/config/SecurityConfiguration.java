package com.bugflix.weblog.config;

import com.bugflix.weblog.config.security.JwtAccessDeniedHandler;
import com.bugflix.weblog.config.security.JwtAuthenticationEntryPoint;
import com.bugflix.weblog.security.JwtAuthenticationFilter;
import com.bugflix.weblog.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
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
            "/api/v1/auths/test",
    };

    private static final String[] SWAGGER_URL_ARRAY = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

    private static final String[] PERMIT_TO_ALL = {
            "/api/v1/users",
            "/api/v1/auths/login",
            "/api/v1/auths/reissue",
            "/api/v1/healthcheck"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(security -> {
                    security.configurationSource(corsConfigurationSource());
                })
                // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .sessionManagement(configurer ->
                    configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(HttpMethod.POST, "/api/v1/posts").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/search/posts").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/posts").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/posts/mine").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").hasRole("USER")
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/likes/**").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/v1/posts", "/api/v1/posts/{postId}",
                                    "/api/v1/posts/preview", "/api/v1/posts/**").permitAll()

                            .requestMatchers(PERMIT_TO_USER).hasRole("USER")
                            .requestMatchers(PERMIT_TO_ALL).permitAll()
                            .requestMatchers(SWAGGER_URL_ARRAY).permitAll()
                            .anyRequest().hasRole("USER");
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(e -> e
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
