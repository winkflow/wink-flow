package com.wink.server.support;

import com.wink.support.BizException;
import com.wink.support.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

import static com.wink.support.ErrorCode.SERVER_UNAVAIABLE;

@Component
public class ServerExceptionHandlers implements ErrorWebExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable t) {
        final ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorCode;
        if (t instanceof BizException) {
            errorCode = t.getMessage();
        } else {
            errorCode = SERVER_UNAVAIABLE;
        }
        Response errorResponse = Response.fail(errorCode, messageSource.getMessage(errorCode, null, Locale.CHINA));
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.toJson().getBytes())));
    }
}