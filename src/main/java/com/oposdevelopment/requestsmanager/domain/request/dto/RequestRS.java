package com.oposdevelopment.requestsmanager.domain.request.dto;

import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;

public record RequestRS(Long id,
                        String title,
                        String content,
                        RequestStatus status,
                        String uniqueNumber) {
}
