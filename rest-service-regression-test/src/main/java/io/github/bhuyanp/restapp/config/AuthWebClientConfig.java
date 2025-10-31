package io.github.bhuyanp.restapp.config;

import io.github.bhuyanp.restapp.client.api.AuthenticationApi;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class AuthWebClientConfig {

    @Value("${api.auth.endpoint}")
    private String authApiEndpoint;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(authApiEndpoint)
                .defaultStatusHandler(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // Custom error handling logic
                                    return Mono.error(new DownstreamException(HttpStatus.valueOf(clientResponse.statusCode().value()), "Failed to invoke product api", errorBody));
                                })
                )
                .build();
    }

    @Bean
    public AuthenticationApi authenticationApi(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(AuthenticationApi.class);
    }
}

