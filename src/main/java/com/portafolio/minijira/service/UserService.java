package com.portafolio.minijira.service;

import com.portafolio.minijira.dto.UserCreateRequestDTO;
import com.portafolio.minijira.dto.UserResponseDTO;

public interface UserService {

    public UserResponseDTO createUser(UserCreateRequestDTO dto);
}
