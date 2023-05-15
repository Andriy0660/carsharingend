package com.example.carsharing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    Integer id;
    @Column(name = "email")
    String email;
    @Column(name = "phone")
    String phone;
    @Column(name = "password")
    @JsonIgnore
    String password;
    @Column(name = "first_name")

    String firstName;
    @Column(name = "last_name")

    String lastName;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Car> ownedCars;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "renter")
    @JsonIgnore
    List<Car> rentedCars;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
