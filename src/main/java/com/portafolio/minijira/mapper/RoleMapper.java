package com.portafolio.minijira.mapper;

import com.portafolio.minijira.dto.role.RoleCreateRequestDTO;
import com.portafolio.minijira.dto.role.RoleResponseDTO;
import com.portafolio.minijira.entity.Role;

public class RoleMapper {

    public static Role toEntity(com.portafolio.minijira.dto.role.RoleCreateRequestDTO dto) {
        Role r = new Role();
        r.setName(dto.getName().trim());
        return r;
    }

    public static RoleResponseDTO toDto(Role role) {
        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getCreatedAt(),
                role.getUpdateAt()
        );
    }
}

