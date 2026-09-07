package com.bank.userservice.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails{

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE, 
        generator = "user_seq")
    @SequenceGenerator(
    name = "user_seq",
    sequenceName = "user_sequence",
    initialValue = 101,
    allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    private String role;

    private String email;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime dateOfBirth;

    private int age;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
            new SimpleGrantedAuthority(role)
        );
    }

    @Override
    public String getUsername() {
        return this.email;
    }


}
