package com.wink.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -9215459396017570673L;

    private Long id;

    private String userName;

    private String password;

    private String nickname;

    private List<UserPermissionDTO> permissions;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserPermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UserPermissionDTO> permissions) {
        this.permissions = permissions;
    }
}
