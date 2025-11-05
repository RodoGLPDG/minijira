package com.portafolio.minijira.service;

import com.portafolio.minijira.dto.UserCreateRequestDTO;
import com.portafolio.minijira.dto.UserResponseDTO;
import com.portafolio.minijira.entity.Role;
import com.portafolio.minijira.entity.User;
import com.portafolio.minijira.exception.role.RoleNotFoundException;
import com.portafolio.minijira.exception.user.EmailAlreadyUsedException;
import com.portafolio.minijira.repository.RoleRepository;
import com.portafolio.minijira.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        // 1) Validar email único
        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new EmailAlreadyUsedException(dto.getEmail());
        });

        // 2) Buscar roles si se proporcionaron roleIds
        Set<Role> roles = new HashSet<>();
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            List<Role> found = roleRepository.findAllById(dto.getRoleIds());
            if (found.size() != dto.getRoleIds().size()) {
                Set<Long> foundIds = found.stream().map(Role::getId).collect(Collectors.toSet());
                Set<Long> missing = dto.getRoleIds().stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toSet());
                throw new RoleNotFoundException("Faltan roles para los ids: " + missing);
            }
            roles.addAll(found);
        }

        // 3) Hashear la contraseña


        // 4) Construir entidad User (hereda BaseEntity — se asumie que id/fechas las maneja BaseEntity)
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword()); // si hashed==null, guardará null (mejor validar antes)
        user.setRoles(roles);

        // 5) Persistir
        User saved = userRepository.save(user);

        // 6) Mapear a DTO de respuesta (solo nombres de roles)
        Set<String> roleNames = saved.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), roleNames);
    }
}
