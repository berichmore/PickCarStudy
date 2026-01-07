package com.erp.global.dto;

public record ErrorResponse(
        Integer code,
        String message
) {
}
