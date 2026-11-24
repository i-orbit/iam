package com.inmaytide.orbit.iam.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orbit IAM API")
                        .description("统一身份认证与权限管理系统接口文档")
                        .version("1.0.0"));
    }

}
