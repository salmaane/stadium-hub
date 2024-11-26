package org.salmane.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TokenRelayOrClientCredentialsFilter implements GlobalFilter, Ordered {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    private final ReactiveOAuth2AuthorizedClientManager authorizedClientManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Check if request already has a Bearer token
        List<String> authHeaders = exchange.getRequest()
                .getHeaders()
                .getOrEmpty(HttpHeaders.AUTHORIZATION);

        if (!authHeaders.isEmpty() && authHeaders.get(0).startsWith("Bearer ")) {
            // User token exists
            return chain.filter(exchange);
        }

        // No user token, use client credentials
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal(clientId)
                .build();

        return authorizedClientManager.authorize(authorizeRequest)
                .map(client -> {
                    if (client != null && client.getAccessToken() != null) {
                        HttpHeaders writeableHeaders = HttpHeaders.writableHttpHeaders(
                                exchange.getRequest().getHeaders());

                        ServerHttpRequestDecorator writeableRequest = new ServerHttpRequestDecorator(
                                exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                HttpHeaders headers = new HttpHeaders();

                                // Copy all existing headers
                                headers.addAll(exchange.getRequest().getHeaders());

                                // Add or replace Authorization header
                                headers.setBearerAuth(client.getAccessToken().getTokenValue());

                                return headers;
                            }
                        };

                        return exchange.mutate()
                                .request(writeableRequest)
                                .build();
                    }
                    return exchange;
                })
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
