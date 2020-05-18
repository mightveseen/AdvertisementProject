package com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.UserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class SystemUserDto implements Serializable {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;
    @NotNull(groups = {Create.class, Update.class})
    private String username;
    @NotNull(groups = {Create.class, Update.class})
    private String password;
    @NotNull(groups = {Create.class, Update.class})
    private UserRole role;

    public SystemUserDto() {

    }

    public SystemUserDto(Long id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public interface Create {

    }

    public interface Update {

    }
}
