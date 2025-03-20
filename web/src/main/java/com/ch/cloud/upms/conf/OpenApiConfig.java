package com.ch.cloud.upms.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户管理")
                .pathsToMatch("/user/**") // 只匹配 /user/ 开头的 API
                .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API")
                        .version("1.0")
                        .description("Springdoc OpenAPI 示例"));
    }


    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("权限管理")
                .pathsToMatch("/permission/**") // 只匹配 /order/ 开头的 API
                .build();
    }

    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
                .group("全部接口")
                .pathsToMatch("/**") // 匹配所有 API
                .build();
    }
}

