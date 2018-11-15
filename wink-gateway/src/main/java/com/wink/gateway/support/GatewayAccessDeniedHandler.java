package com.wink.gateway.support;

import com.wink.support.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GatewayAccessDeniedHandler implements ServerAccessDeniedHandler {
    private static final Response AUTH_DENIED_RESPONSE = Response.fail("9002", "Access Denied");

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException denied) {
        final ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(AUTH_DENIED_RESPONSE.toJson().getBytes())));
    }
}
