package com.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by patryk on 03.09.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "User.find",
                query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"),
        @NamedQuery(
                name = "User.list",
                query = "SELECT u FROM User u")
})
@SuppressWarnings("serial")
@XmlRootElement
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "userroles", joinColumns = { @JoinColumn(name = "iduser") })
    @Column(name = "role")
    private List<Role> roles;


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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
