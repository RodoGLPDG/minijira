package com.portafolio.minijira.dto.role;

import java.time.Instant;

public class RoleResponseDTO {
    private long id;
    private String name;
    private Instant createdAt;
    private Instant updateAt;

    public RoleResponseDTO() {}
    public RoleResponseDTO(long id, String name, Instant createdAt, Instant updateAt) {
        this.id = id; this.name = name; this.createdAt = createdAt; this.updateAt = updateAt;
    }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdateAt() { return updateAt; }
    public void setUpdateAt(Instant updateAt) { this.updateAt = updateAt; }
}