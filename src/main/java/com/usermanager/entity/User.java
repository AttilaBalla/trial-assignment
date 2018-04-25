package com.usermanager.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashMap;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    @Min(message = "Password must be at least 6 characters long!", value = 6)
    private String userPassword;

    @Column(unique=true, nullable=false)
    @Email(message = "E-mail address is invalid!")
    private String userEmail;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Role userRole;

    public User() {
    }

    public User(String userName, String password, String email, Role userRole) {
        this.userEmail = email;
        this.userName = userName;
        this.userPassword = password;
        this.userRole = userRole;
    }

    public HashMap<String, String> getSimpleUserDetails() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put("id", id.toString());
        userData.put("username", userName);
        userData.put("email", userEmail);
        userData.put("role", userRole.getRoleType().toString());

        return userData;
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
        return userRole;
    }

    public String getRoleString() {
        return userRole.toString();
    }

    public void setRole(Role role) {
        this.userRole = role;
    }
}
