package com.wink.model;

import java.util.Set;

public class ResourcePermission extends BaseModel {
    private static final long serialVersionUID = 1978020796414429054L;

    private Long pid;
    private String path;
    private String method;
    private Set<String> permission;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<String> getPermission() {
        return permission;
    }

    public void setPermission(Set<String> permission) {
        this.permission = permission;
    }
}
