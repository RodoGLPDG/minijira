package entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "role", indexes = {
        @Index( columnList = "name", name = "idx_role_name")
})
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    //constructor
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Role(long id, Instant createdAt, Instant updateAt, String name) {
        super(id, createdAt, updateAt);
        this.name = name;
    }

    // Getter y setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

