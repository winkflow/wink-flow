package com.wink.gateway.support;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.CookieWebSessionIdResolver;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GatewayCookieResolver extends CookieWebSessionIdResolver {

    public GatewayCookieResolver() {
        super();
    }

    @Override
    public void setCookieName(String cookieName) {
        super.setCookieName(cookieName);
    }

    @Override
    public String getCookieName() {
        return super.getCookieName();
    }

    @Override
    public void setCookieMaxAge(Duration maxAge) {
        super.setCookieMaxAge(maxAge);
    }

    @Override
    public Duration getCookieMaxAge() {
        return super.getCookieMaxAge();
    }

    @Override
    public List<String> resolveSessionIds(ServerWebExchange exchange) {
        return super.resolveSessionIds(exchange);
    }

    @Override
    public void setSessionId(ServerWebExchange exchange, String id) {
        Assert.notNull(id, "'id' is required");
        setSessionCookie(exchange, id, getCookieMaxAge());
    }

    @Override
    public void expireSession(ServerWebExchange exchange) {
        super.expireSession(exchange);
    }

    private void setSessionCookie(ServerWebExchange exchange, String id, Duration maxAge) {
        String name = getCookieName();
        final ServerHttpRequest request = exchange.getRequest();
        boolean secure = "https".equalsIgnoreCase(request.getURI().getScheme());
        String path = request.getPath().contextPath().value() + "/";
        final String domainName = this.getDomainName(request.getURI().getHost());
        final ResponseCookie build = ResponseCookie.from(name, id).path(path)
                .maxAge(maxAge).httpOnly(true).secure(secure)
                .domain(domainName)
                .build();
        exchange.getResponse().getCookies().set(name,
                build);
    }

    private String getDomainName(String host) {
        Matcher matcher = Pattern.compile("^.+?\\.(\\w+\\.[a-z]+)$",
                Pattern.CASE_INSENSITIVE).matcher(host);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return host;
    }
}
