package com.wink.gateway.filter;

import com.wink.gateway.support.WinkUser;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//TODO save sessionId->userId to redis
public class RecordSessionFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getPrincipal().map(
                principal -> {
                    final WinkUser winkUser = (WinkUser) principal;
                    return Mono.just(winkUser.getId());
                }
        ).map(userId -> exchange.getSession().map(webSession -> {
            String sessionId = webSession.getId();

            System.out.println(sessionId + ": " + userId);
            System.out.println(sessionId + ": " + userId);
            System.out.println(sessionId + ": " + userId);
            System.out.println(sessionId + ": " + userId);
            System.out.println(sessionId + ": " + userId);
            return Mono.just("ok");
        })).then(chain.filter(exchange));
    }


}
