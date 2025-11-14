package com.portafolio.minijira.service.impl;

import com.portafolio.minijira.dto.role.RoleCreateRequestDTO;
import com.portafolio.minijira.dto.role.RoleResponseDTO;
import com.portafolio.minijira.dto.role.RoleUpdateRequestDTO;
import com.portafolio.minijira.entity.Role;
import com.portafolio.minijira.exception.role.RoleException;
import com.portafolio.minijira.mapper.RoleMapper;
import com.portafolio.minijira.repository.RoleRepository;
import com.portafolio.minijira.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public RoleResponseDTO create(RoleCreateRequestDTO dto) {
        String name = dto.getName().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new RoleException.DuplicateResourceException("Ya existe un rol con el nombre: " + name);
        }

        Role role = new Role();
        role.setName(name);

        Role saved = repository.save(role);

        return RoleMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO getById(Long id) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new RoleException.RoleNotFoundException("Rol no encontrado con id: " + id));

        return RoleMapper.toDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleResponseDTO> list(Pageable pageable){
        return repository.findAll(pageable).map(RoleMapper::toDto);
    }

    @Override
    @Transactional
    public RoleResponseDTO update(Long id, RoleUpdateRequestDTO dto) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new RoleException.RoleNotFoundException("Rol no encontrado con id: " + id));

        String newName = dto.getName().trim();

        // Si existe otro role con ese nombre -> error
        repository.findByNameIgnoreCase(newName)
                .filter(r -> r.getId() != id) // mantuve tu comparación previa; si usas Long cambia a !r.getId().equals(id)
                .ifPresent(r -> { throw new RoleException.DuplicateResourceException("Ya existe un rol con el nombre: " + newName); });

        role.setName(newName);
        Role updated = repository.save(role);

        return RoleMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RoleException.RoleNotFoundException("Rol no encontrado con id: " + id);
        }

        repository.deleteById(id);
    }

}