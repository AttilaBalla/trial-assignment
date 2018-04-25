package com.usermanager.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPassword;

    @Column(unique=true, nullable=false)
    private String userEmail;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Role role;

    public User() {
    }

    public User(String userName, String password, String email) {
        this.userEmail = email;
        this.userName = userName;
        this.userPassword = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Role getRole() {
        return role;
    }

    public String getRoleString() {
        return role.toString();
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
