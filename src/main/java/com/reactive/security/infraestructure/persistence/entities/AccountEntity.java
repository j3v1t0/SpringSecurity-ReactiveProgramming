package com.reactive.security.infraestructure.persistence.entities;

import com.reactive.security.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountEntity implements Serializable {
    private static final long SerialVersionUUID = 1L;
    @Id
    @Column(name = "account_uuid")
    private String accountUuid;
    @Column(name = "identification", nullable = false, length = 10, unique = true)
    private String identification;
    @Column(name = "fullname", nullable = false)
    private String fullname;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role roles;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    @Column(name = "last_login_at", nullable = false)
    private LocalDate lastLoginAt;
}
