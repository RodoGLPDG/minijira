package com.portafolio.minijira.controller;


import com.portafolio.minijira.dto.user.UserCreateRequestDTO;
import com.portafolio.minijira.dto.user.UserResponseDTO;
import com.portafolio.minijira.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateRequestDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> readUser(@PathVariable Long id) {
        UserResponseDTO response = userService.readUser(id);
        return ResponseEntity.ok(response);
    }

}