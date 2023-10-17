package com.reactive.security.domain.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterAccountDto {
    private String identification;
    private String fullname;
    private String password;
}
