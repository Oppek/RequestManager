package com.oposdevelopment.requestsmanager.domain.request.validation;

import com.oposdevelopment.requestsmanager.domain.request.Request;
import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import com.oposdevelopment.requestsmanager.exception.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

import static com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus.*;
import static com.oposdevelopment.requestsmanager.exception.ErrorCode.*;

@Component
public class RequestValidator {

    public void validateRequestUpdate(Request request) {
        if (!EnumSet.of(CREATED, VERIFIED).contains(request.getStatus())) {
            throw new ApplicationException(WRONG_REQUEST_STATUS, request.getStatus());
        }
    }

    public void validateStatusChange(Request request, RequestStatus targetStatus) {
        RequestStatus currentStatus = request.getStatus();
        if (targetStatus.equals(DELETED) && !currentStatus.equals(CREATED)) {
            throw new ApplicationException(REQUEST_NOT_DELETED_WRONG_STATUS, request.getId(), request.getStatus());
        }

        if (targetStatus.equals(REJECTED) && !EnumSet.of(VERIFIED, ACCEPTED).contains(currentStatus)) {
            throw new ApplicationException(REQUEST_NOT_REJECTED_WRONG_STATUS, request.getId(), request.getStatus());
        }

        if (targetStatus.equals(VERIFIED) && !currentStatus.equals(CREATED)) {
            throw new ApplicationException(REQUEST_NOT_VERIFIED_WRONG_STATUS, request.getId(), request.getStatus());
        }

        if (targetStatus.equals(ACCEPTED) && !currentStatus.equals(VERIFIED)) {
            throw new ApplicationException(REQUEST_NOT_ACCEPTED_WRONG_STATUS, request.getId(), request.getStatus());
        }

        if (targetStatus.equals(PUBLISHED) && !currentStatus.equals(ACCEPTED)) {
            throw new ApplicationException(REQUEST_NOT_ACCEPTED_WRONG_STATUS, request.getId(), request.getStatus());
        }
    }

}
