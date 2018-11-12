package com.wink.service.resource;

import com.wink.dto.ResourcePermissionDTO;
import com.wink.service.ResourcePermissionService;
import com.wink.service.ServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResourcePermissionServiceTest extends ServiceBaseTest {

    @Autowired
    private ResourcePermissionService resourcePermissionService;

    @Test
    public void findAllResourcePermissions() {
        final List<ResourcePermissionDTO> resourcePermissions = this.resourcePermissionService.findAllResourcePermissions();
        Assert.assertNotNull(resourcePermissions);
        prettyResult(resourcePermissions);
    }

}