package com.space.datahub.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "users")
@ToString(of = {"id", "name"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private int active;

    private String role = "";

    public User(String username, String password, String email, String role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.active = 1;
    }

    protected User(){}

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public String getRole() {
        return role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
