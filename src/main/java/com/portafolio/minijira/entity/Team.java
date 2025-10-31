package com.portafolio.minijira.entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams", indexes = {
        @Index(columnList = "name", name = "idx_team_name")
})
public class Team extends BaseEntity{
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            indexes = {
                    @Index(columnList = "team_id, user_id", name = "idx_team_members")
            }
    )
    private Set<User> members = new HashSet<>();

    public Team() {}

    public Team(String name) {
        this.name = name;
    }

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<User> getMembers() { return members; }
    public void setMembers(Set<User> members) { this.members = members; }

    // Helpers
    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }
}
