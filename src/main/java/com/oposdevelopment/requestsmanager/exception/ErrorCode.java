package com.oposdevelopment.requestsmanager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {

    REQUEST_NOT_FOUND("Can not find a request with a given id {0}."),
    WRONG_REQUEST_STATUS("Can not update a content of request in {0} status."),
    REQUEST_NOT_DELETED_WRONG_STATUS("Can not delete a request with id {0}. Current status {1} not allow to make this action."),
    REQUEST_NOT_REJECTED_WRONG_STATUS("Can not reject a request with id {0}. Current status {1} not allow to make this action."),
    REQUEST_NOT_VERIFIED_WRONG_STATUS("Can not verify a request with id {0}. Current status {1} not allow to make this action."),
    REQUEST_NOT_ACCEPTED_WRONG_STATUS("Can not accept a request with id {0}. Current status {1} not allow to make this action."),
    REQUEST_NOT_PUBLISHED_WRONG_STATUS("Can not publish a request with id {0}. Current status {1} not allow to make this action.");


    @Getter
    private final String message;
}
