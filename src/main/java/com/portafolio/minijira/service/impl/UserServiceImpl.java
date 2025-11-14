package com.portafolio.minijira.service.impl;

import com.portafolio.minijira.dto.user.UserCreateRequestDTO;
import com.portafolio.minijira.dto.user.UserResponseDTO;
import com.portafolio.minijira.entity.Role;
import com.portafolio.minijira.entity.User;
import com.portafolio.minijira.exception.role.RoleException;
import com.portafolio.minijira.exception.user.EmailAlreadyUsedException;
import com.portafolio.minijira.repository.RoleRepository;
import com.portafolio.minijira.repository.UserRepository;
import com.portafolio.minijira.service.UserService;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
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

    /**
     * Crea un usuario validando email único, resolviendo roles y hasheando la contraseña.
     *
     * @param dto datos de creación
     * @return UserResponseDTO con información pública del usuario creado
     */
    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        // Validaciones
        if (dto == null) {
            throw new IllegalArgumentException("Request body inválido");
        }
        String rawEmail = dto.getEmail();
        if (rawEmail == null || rawEmail.isBlank()) {
            throw new IllegalArgumentException("Email requerido");
        }
        // Normalizar email: trim y lowercase para consistencia
        String normalizedEmail = rawEmail.trim().toLowerCase();
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password requerido");
        }
        userRepository.findByEmail(normalizedEmail).ifPresent(u -> {
            throw new EmailAlreadyUsedException(normalizedEmail);
        });

        //Resolver roles si se proporcionaron roleIds ---
        Set<Role> roles = new HashSet<>();
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            List<Role> found = roleRepository.findAllById(dto.getRoleIds());
            if (found.size() != dto.getRoleIds().size()) {
                Set<Long> foundIds = found.stream().map(Role::getId).collect(Collectors.toSet());
                Set<Long> missing = dto.getRoleIds().stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toSet());
                throw new RoleException.RoleNotFoundException("Faltan roles para los ids: " + missing);
            }
            roles.addAll(found);
        }

        //Hash BCrypt
        String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));

        //Construir entidad User
        User user = new User();
        user.setName(dto.getName() != null ? dto.getName().trim() : null);
        user.setEmail(normalizedEmail);
        user.setPasswordHash(hashed);
        user.setRoles(roles);
        // Guardar en BD
        User saved = userRepository.save(user);

        // --- 6) Mapear a DTO de respuesta
        Set<String> roleNames = saved.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), roleNames);
    }


    @Override
    public UserResponseDTO readUser(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser un número positivo");
            }

            // Buscar usuario
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

            // Mapear a respuesta (roles vacíos por ahora)
            Set<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            return new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    roleNames
            );

        } catch (IllegalArgumentException ex) {
            // Errores esperados (id inválido, usuario no existe)
            throw ex;

        } catch (Exception ex) {
            // Error inesperado en DB u otra capa
            throw new RuntimeException("Error al obtener usuario: " + ex.getMessage(), ex);
        }
    }

}