package io.github.bhuyanp.clientapp.config;

import io.github.bhuyanp.clientapp.exception.DownstreamException;
import io.github.bhuyanp.clientapp.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class ProductWebClientConfig {


    private final TokenUtil tokenUtil;
    @Value("${api.product.endpoint}")
    private String productApiEndpoint;

    @Bean
    public WebClient productApiWebClient(WebClient.Builder builder) {
        Function<ClientResponse, Mono<? extends Throwable>> PRODUCT_API_EXCEPTION_HANDLER = clientResponse ->
                clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            // Custom error handling logic
                            return Mono.error(new DownstreamException(HttpStatus.valueOf(clientResponse.statusCode().value()), "Failed to invoke product api", errorBody));
                        });
        return builder
                .baseUrl(productApiEndpoint)
                .filter(new DynamicHeaderFilter(tokenUtil))
                .defaultStatusHandler(HttpStatusCode::isError, PRODUCT_API_EXCEPTION_HANDLER)
                .build();
    }

    @Bean
    public io.github.bhuyanp.restapp.client.api.ProductsApi productApi(WebClient productApiWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(productApiWebClient))
                .build();
        return httpServiceProxyFactory.createClient(io.github.bhuyanp.restapp.client.api.ProductsApi.class);
    }
}

@RequiredArgsConstructor
class DynamicHeaderFilter implements ExchangeFilterFunction {
    private final TokenUtil tokenUtil;

    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction nextFilter) {
        // Create a new ClientRequest with the additional headers
        ClientRequest modifiedRequest = ClientRequest
                .from(clientRequest)
                .header("Authorization", "Bearer " + tokenUtil.getToken())
                .build();

        return nextFilter.exchange(modifiedRequest);
    }
}
