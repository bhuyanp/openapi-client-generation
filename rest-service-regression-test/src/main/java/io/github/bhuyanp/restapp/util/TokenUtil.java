package io.github.bhuyanp.restapp.util;

import io.github.bhuyanp.restapp.client.api.AuthenticationApi;
import io.github.bhuyanp.restapp.client.model.AuthRequest;
import io.github.bhuyanp.restapp.client.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final AuthenticationApi authenticationApi;

    @Value("${api.product.user}")
    private String userName;
    @Value("${api.product.pass}")
    private String password;

    @Cacheable("token")
    public String getToken() {
        log.info("Fetching token.");
        AuthRequest authRequest = new AuthRequest(userName, password);
        ResponseEntity<TokenResponse> tokenResponseEntity = authenticationApi.authenticate(authRequest);
        assert tokenResponseEntity.getBody() != null;
        return tokenResponseEntity.getBody().getToken();
    }

}
