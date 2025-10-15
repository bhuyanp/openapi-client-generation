package io.github.bhuyanp.restapp.config;

import io.github.bhuyanp.restapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "Bearer", bearerFormat = "JWT", name = "bearerAuth")
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "Apply Default Global SecurityScheme in springdoc-openapi",
        version = "1.0.0"),
        security = {@SecurityRequirement(name = "bearerAuth")}
)
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.version}")
    private String appVersion;

    @Bean
    public OpenAPI openApi() {

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(appName)
                        .version(appVersion)
                        .license(new License().name("XYZ Corp").identifier("License identifier"))
                        .description("""
                                Application description
                                """));

    }


}
