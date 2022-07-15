package com.oposdevelopment.requestsmanager.domain.request.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UpdateRequestRQ(
        @NotBlank
        @Size(min = 20, max = 1024)
        String content) {
}