package com.wink.gateway.filter;

import com.wink.gateway.support.GatewayAuthenticationFailureHandler;
import com.wink.gateway.support.GatewayAuthenticationSuccessHandler;
import com.wink.gateway.support.ServerBodyAuthenticationConverter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class LoginWebFilter extends AuthenticationWebFilter {

    public LoginWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
        );
        setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        setAuthenticationConverter(new ServerBodyAuthenticationConverter());
        setAuthenticationSuccessHandler(new GatewayAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(new GatewayAuthenticationFailureHandler());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return super.filter(exchange, chain);
    }
}