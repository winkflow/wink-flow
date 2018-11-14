package com.wink.gateway.config;

import com.wink.dto.ResourcePermissionDTO;
import io.wink.tool.autoconfigure.SofaRpcAutoConfiguration;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebFluxSecurity
public class WinkGatewayAutoConfigure {
    @Autowired
    ReactiveRedisTemplate<String, String> redisTemplate;

    @Bean
    public SofaRpcAutoConfiguration sofaRpcAutoConfiguration() {
        return new SofaRpcAutoConfiguration();
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws IOException {
        final String permissionString = redisTemplate.opsForValue().get("resource:permissions").block();
        if (StringUtils.isNotEmpty(permissionString)) {
            final ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ResourcePermissionDTO.class);
            List<ResourcePermissionDTO> resourcePermissions = objectMapper.readValue(permissionString, javaType);
            ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = http.authorizeExchange();
            resourcePermissions.forEach(resourcePermission -> {
                final Set<String> permission = resourcePermission.getPermission();
                if (permission == null || permission.isEmpty()) {
                    authorizeExchangeSpec.pathMatchers(resourcePermission.getPath()).permitAll();
                    return;
                }
                authorizeExchangeSpec.pathMatchers(resourcePermission.getPath())
                        .access((authenticationMono, authorizationContext) -> authenticated(authenticationMono, permission));
            });
        }
        http.authorizeExchange().anyExchange().authenticated().and()
                .httpBasic().and()
                .formLogin();
        return http.build();
    }

    private Mono<AuthorizationDecision> authenticated(Mono<Authentication> authenticationMono, Set<String> permissions) {
        return authenticationMono
                .map(authentication -> {
                            if (permissions.isEmpty()) {
                                return true;
                            }
                            return authentication.getAuthorities().stream().filter(it -> permissions.contains(it.getAuthority())).findAny().isPresent();
                        }
                )
                .map(granted -> new AuthorizationDecision(granted));
    }
}
