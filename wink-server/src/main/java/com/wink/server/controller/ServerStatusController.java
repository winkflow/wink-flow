package com.wink.server.controller;

import com.wink.support.BusinessException;
import com.wink.support.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.wink.support.ErrorCode.SERVER_UN_KNOWN_VERSION;

@RestController
public class ServerStatusController {

    @Value("${server.version}")
    private String serverVersion;


    @GetMapping("/status")
    public Mono<Response<String>> getApi() {
        if (StringUtils.isEmpty(serverVersion))
            throw new BusinessException(SERVER_UN_KNOWN_VERSION);
        return Mono.just(Response.ok(serverVersion));
    }

}
