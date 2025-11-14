package com.portafolio.minijira.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleUpdateRequestDTO {
    @NotBlank
    @Size(max = 50)
    private String name;

    public RoleUpdateRequestDTO() {}
    public RoleUpdateRequestDTO(String name) { this.name = name; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}