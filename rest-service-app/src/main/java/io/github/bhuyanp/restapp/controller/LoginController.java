package io.github.bhuyanp.restapp.controller;

import io.github.bhuyanp.restapp.dto.AuthRequest;
import io.github.bhuyanp.restapp.dto.ErrorResponse;
import io.github.bhuyanp.restapp.dto.TokenResponse;
import io.github.bhuyanp.restapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping(path = "/login", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(operationId = "authenticate", responses = {
            @ApiResponse(responseCode = "200", description = "api_key to be used in the secured-ping endpoint", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @SecurityRequirements()
    public ResponseEntity<TokenResponse> createAuthenticationToken(@RequestBody @Valid AuthRequest request) {
        authenticate(request.userName(), request.password());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.userName());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
