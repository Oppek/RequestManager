package com.oposdevelopment.requestsmanager.exception.dto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ApplicationExceptionRS(String code, String message, ZonedDateTime timestamp) {
}
