package com.portafolio.minijira.service;

import com.portafolio.minijira.dto.role.RoleCreateRequestDTO;
import com.portafolio.minijira.dto.role.RoleResponseDTO;
import com.portafolio.minijira.dto.role.RoleUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleResponseDTO create(RoleCreateRequestDTO dto);
    RoleResponseDTO getById(Long id);
    Page<RoleResponseDTO> list(Pageable pageable);
    RoleResponseDTO update(Long id, RoleUpdateRequestDTO dto);
    void delete(Long id);
}