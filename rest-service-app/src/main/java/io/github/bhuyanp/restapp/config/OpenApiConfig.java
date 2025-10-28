package io.github.bhuyanp.restapp.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "Bearer", bearerFormat = "JWT", name = "bearerAuth")
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.version}")
    private String appVersion;
    @Value("${spring.profiles.active:PROD}")
    private String activeProfile;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(appName+" "+activeProfile)
                        .version(appVersion)
                        .license(new License().name("XYZ Corp").identifier("License identifier"))
                        .description("""
                                Application description
                                """))
                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }


}
