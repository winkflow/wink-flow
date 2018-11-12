package com.wink.gateway.service;

import com.wink.dto.UserDTO;
import com.wink.dto.UserPermissionDTO;
import com.wink.gateway.support.WinkUser;
import com.wink.service.LoginService;
import io.wink.tool.annotation.RpcConsumer;
import io.wink.tool.reference.Reference;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @RpcConsumer
    LoginService loginService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        final CompletableFuture<UserDTO> completableFuture = Reference.callBack(apply -> loginService.findUserByName(username));
        return Mono.fromFuture(completableFuture).switchIfEmpty(Mono.empty()).flatMap(userDTO -> {
            final User.UserBuilder userBuilder = User.builder()
                    .username(userDTO.getUserName())
                    .password(userDTO.getPassword());
            final List<UserPermissionDTO> permissions = userDTO.getPermissions();
            if (!CollectionUtils.isEmpty(permissions)) {
                permissions.forEach(permission -> permission.getPermission().forEach(projectPermission -> {
                    final Long projectId = permission.getProjectId();
                    userBuilder.authorities((projectId == null ? projectPermission : projectId + ":" + projectPermission));
                }));
            }
            WinkUser admin = new WinkUser(userBuilder.build());
            return Mono.just(admin);
        });

    }
}
