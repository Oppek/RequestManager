package com.oposdevelopment.requestsmanager.domain.request.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record DeleteRejectRequestRQ(
        @NotBlank
        @Size(min = 10, max = 128)
        String reason) {
}
