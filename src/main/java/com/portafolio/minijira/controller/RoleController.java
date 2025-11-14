package com.portafolio.minijira.controller;


import com.portafolio.minijira.dto.role.RoleCreateRequestDTO;
import com.portafolio.minijira.dto.role.RoleResponseDTO;
import com.portafolio.minijira.dto.role.RoleUpdateRequestDTO;
import com.portafolio.minijira.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleCreateRequestDTO dto){
        RoleResponseDTO response = roleService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleId(@PathVariable Long id){
        RoleResponseDTO response = roleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RoleResponseDTO>> listRoles(Pageable pageable) {
        Page<RoleResponseDTO> page = roleService.list(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequestDTO dto) {

        RoleResponseDTO updated = roleService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
