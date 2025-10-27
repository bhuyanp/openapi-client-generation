package io.github.bhuyanp.restapp.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Valid
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    @NotBlank
    private String secret;
}
