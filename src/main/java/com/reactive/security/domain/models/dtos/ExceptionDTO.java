package com.reactive.security.domain.models.dtos;

import lombok.Builder;

@Builder
public record ExceptionDTO(
        String detail,
        String type,
        Integer status
) {
}