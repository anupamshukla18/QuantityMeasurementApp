package com.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    
    private String name;
    private String provider; // e.g., "google"
    private String role;     // e.g., "ROLE_USER"

    // Constructor for OAuth2 users — password set to empty string (they never use password login)
    public UserEntity(String email, String name, String provider, String role) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.role = role;
        this.password = ""; // FIX: password column is NOT NULL; OAuth2 users don't need a real password
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(
                        role != null ? role : "ROLE_USER"
                )
        );
    }

    @Override
    public String getPassword() {
        // FIX: return the stored field so Spring Security can verify BCrypt hashes for local login
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}