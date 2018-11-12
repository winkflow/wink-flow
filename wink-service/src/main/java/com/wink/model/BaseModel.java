package com.wink.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
    private static final long serialVersionUID = -5278890512540450372L;

    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void preInsert() {
        this.createdAt = new Date();
        this.updatedAt = createdAt;
        this.isDeleted = false;
    }

    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
