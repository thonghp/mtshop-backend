package com.mtshop.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends IdBasedEntity {

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "first_name", length = 100, nullable = false) // nvarchar 100, not null
    @Nationalized
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    @Nationalized
    private String lastName;

    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + '\'' + ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' + ", roles=" + roles + '}';
    }

    @Transient
    public String getPhotosImagePath() {
        if (id == null || photos == null) return "/images/default-user.png";

        return "/images/user-photos/" + this.id + "/" + this.photos;
    }

    @Transient
    public String getFullName() {
        return lastName + " " + firstName;
    }

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = roles.iterator();

        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
