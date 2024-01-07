package com.bugflix.weblog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
        @Bean
        public OpenAPI openAPI() {
                // SecuritySecheme 이름
                String schemeName = "Authorization";
                // API 요청헤더에 인증정보 포함
                SecurityRequirement securityRequirement = new SecurityRequirement();
                securityRequirement.addList(schemeName);

                // SecuritySchemes 등록
                Components components = new Components()
                        .addSecuritySchemes(schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP) // HTTP 방식
                                .scheme("bearer")
                                .bearerFormat(schemeName)
                                .in(SecurityScheme.In.HEADER)); // 토큰 형식을 지정하는 임의의 문자(Optional)

                return new OpenAPI()
                        .info(info())
                        .components(components)
                        .addSecurityItem(securityRequirement);
        }


        private Info info() {
                return new Info()
                        .title("weblog 프로젝트 API Document")
                        .version("v0.0.1")
                        .description("weblog 프로젝트의 API 명세서입니다.");
        }

}