package com.portafolio.minijira.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "email", name = "idx_user_email")
})
public class User extends BaseEntity {

    @Column(name = "name", nullable = false, length = 120)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El nombre contiene caracteres inválidos")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 160)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 160, message = "El email no puede tener más de 160 caracteres")
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 60, max = 255, message = "El hash de la contraseña no tiene un formato válido")
    private String passwordHash;

    //relacion
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    //constructor
    public User() {}

    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    // Helpers
    /*
        un método de ayuda que simplifica tareas repetitivas o asegura que se mantenga la consistencia del objeto
        se usan para gestionar relaciones bidireccionales de manera segura.
     */
    public void addRole(Role role) { roles.add(role); }
    public void removeRole(Role role) { roles.remove(role); }
}
