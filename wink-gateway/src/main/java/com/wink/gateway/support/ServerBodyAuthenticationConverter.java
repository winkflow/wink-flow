package com.wink.gateway.support;

import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ServerBodyAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        ServerRequest serverRequest = new DefaultServerRequest(exchange);
        return serverRequest.bodyToMono(UserInfo.class)
                .flatMap(it -> Mono.just(new UsernamePasswordAuthenticationToken(it.getUsername(), it.getPassword())));
    }
}
