package com.wink.dto;

import java.io.Serializable;
import java.util.List;

public class UserPermissionDTO implements Serializable {
    private static final long serialVersionUID = 1425649486777199353L;

    private Long projectId;
    private List<String> permission;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }
}