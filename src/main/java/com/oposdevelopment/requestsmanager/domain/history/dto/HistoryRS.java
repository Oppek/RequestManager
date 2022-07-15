package com.oposdevelopment.requestsmanager.domain.history.dto;

import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;

import java.util.Date;

public record HistoryRS(Long id,
                        Long requestId,
                        RequestStatus previousStatus,
                        RequestStatus currentStatus,
                        String changeReason,
                        Date modificationDate) {
}
