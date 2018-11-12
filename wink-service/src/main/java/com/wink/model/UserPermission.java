package com.wink.model;

import java.util.List;

public class UserPermission extends BaseModel {
    private static final long serialVersionUID = -4314233819759688799L;

    private Long projectId;

    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}