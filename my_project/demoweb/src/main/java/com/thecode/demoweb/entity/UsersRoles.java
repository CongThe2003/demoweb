package com.thecode.demoweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_roles")
@IdClass(UserRolesId.class)
public class UsersRoles {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "role_id")
    private int roleId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    // Constructors, getters, setters, etc.

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
