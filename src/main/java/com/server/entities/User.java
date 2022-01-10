package com.server.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty(message = "Name can not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Surname can not be empty")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email id")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Column(name = "login", nullable = false)
    private String login;

    @NotEmpty(message = "Password can not be empty")
    @Column(name = "password", nullable = false)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}