package com.vaibhavbansode.apiGateWay.filters;


import com.vaibhavbansode.apiGateWay.JwtService;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class JwtAuthenticationFilter
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final String USER_ID_HEADER = "X-User-Id";

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header");
                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            try {
                Long userId = jwtService.extractUserId(token);

                ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .headers(headers -> headers.remove(USER_ID_HEADER))
                        .header(USER_ID_HEADER, String.valueOf(userId))
                        .build();

                return chain.filter(
                        exchange.mutate()
                                .request(mutatedRequest)
                                .build()
                );

            } catch (Exception e) {

                log.warn("JWT authentication failed", e);

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }
        };
    }

    public static class Config {
    }
}