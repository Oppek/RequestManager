package com.oposdevelopment.requestsmanager.domain.request.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateRequestRQ(
        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        @Size(min = 20, max = 1024)
        String content) {
}
