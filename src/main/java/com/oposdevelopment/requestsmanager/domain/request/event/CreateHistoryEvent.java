package com.oposdevelopment.requestsmanager.domain.request.event;

import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;

import java.util.Date;

public record CreateHistoryEvent(Long requestId,
                                 RequestStatus previousStatus,
                                 RequestStatus currentStatus,
                                 String changeReason,
                                 Date modificationDate) {
}
