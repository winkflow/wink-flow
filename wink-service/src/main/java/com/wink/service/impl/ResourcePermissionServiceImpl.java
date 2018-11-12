package com.wink.service.impl;

import com.wink.dao.ResourcePermissionDao;
import com.wink.dto.ResourcePermissionDTO;
import com.wink.model.ResourcePermission;
import com.wink.service.ResourcePermissionService;
import com.wink.support.BeanUtil;
import io.wink.tool.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@RpcProvider
@Service
public class ResourcePermissionServiceImpl implements ResourcePermissionService {
    @Autowired
    ResourcePermissionDao resourcePermissionDao;

    @Override
    public List<ResourcePermissionDTO> findAllResourcePermissions() {
        final List<ResourcePermission> resourcePermissions = this.resourcePermissionDao.list();
        if (CollectionUtils.isEmpty(resourcePermissions))
            return new ArrayList<>();
        Map<Long, Set<Long>> resourceAncestor = new HashMap<>();
        for (ResourcePermission resourcePermission : resourcePermissions) {
            final Long resourceId = resourcePermission.getId();
            resourceAncestor.computeIfAbsent(resourceId, k1 -> new HashSet<Long>() {{
                add(resourceId);
            }});
            final Long parentId = resourcePermission.getPid();
            if (parentId != null) {
                resourceAncestor.computeIfAbsent(parentId, k -> new HashSet<Long>() {{
                    add(parentId);
                }});
                resourceAncestor.get(resourceId).addAll(resourceAncestor.get(parentId));
            }
        }
        Map<Long, Set<String>> originalPermissions = resourcePermissions.stream()
                .collect(Collectors.toMap(it -> it.getId(), it -> it.getPermission()));
        for (ResourcePermission resourcePermission : resourcePermissions) {
            final Set<Long> ancestor = resourceAncestor.get(resourcePermission.getId());
            if (ancestor.isEmpty()) {
                continue;
            }
            final Set<String> permission = resourcePermission.getPermission();
            ancestor.stream().map(originalPermissions::get).filter(ancestorPermissions -> !ancestorPermissions.isEmpty())
                    .forEachOrdered(permission::addAll);
        }
        return BeanUtil.copyList(resourcePermissions, ResourcePermissionDTO.class);
    }
}
