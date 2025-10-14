package io.github.bhuyanp.restapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
