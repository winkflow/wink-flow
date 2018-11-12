package com.wink.service;

import com.wink.dto.ResourcePermissionDTO;

import java.util.List;

public interface ResourcePermissionService {
    List<ResourcePermissionDTO> findAllResourcePermissions();
}
