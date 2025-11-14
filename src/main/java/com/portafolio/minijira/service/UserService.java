package com.portafolio.minijira.service;

import com.portafolio.minijira.dto.user.UserCreateRequestDTO;
import com.portafolio.minijira.dto.user.UserResponseDTO;

public interface UserService {

    public UserResponseDTO createUser(UserCreateRequestDTO dto);

    public UserResponseDTO readUser(Long id);
}
