package com.oposdevelopment.requestsmanager.domain.request.service;

import com.oposdevelopment.requestsmanager.domain.history.dto.HistoryRS;
import com.oposdevelopment.requestsmanager.domain.request.Request;
import com.oposdevelopment.requestsmanager.domain.request.RequestRepository;
import com.oposdevelopment.requestsmanager.domain.request.dto.CreateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.dto.RequestRS;
import com.oposdevelopment.requestsmanager.domain.request.dto.UpdateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.event.CreateHistoryEvent;
import com.oposdevelopment.requestsmanager.domain.request.mapper.RequestMapper;
import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import com.oposdevelopment.requestsmanager.domain.request.validation.RequestValidator;
import com.oposdevelopment.requestsmanager.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus.PUBLISHED;
import static com.oposdevelopment.requestsmanager.exception.ErrorCode.REQUEST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository repository;
    private final RequestMapper requestMapper;
    private final RequestValidator requestValidator;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Long create(CreateRequestRQ createRequestRQ) {
        Request request = requestMapper.map(createRequestRQ);
        request.setStatus(RequestStatus.CREATED);

        Request createdRequest = repository.save(request);

        applicationEventPublisher.publishEvent(new CreateHistoryEvent(
                createdRequest.getId(), request.getStatus(), null, null, new Date()));

        return createdRequest.getId();
    }

    public RequestRS getRequestById(Long id) {
        return requestMapper.map(findRequest(id));
    }

    public RequestRS updateRequestContent(Long id, UpdateRequestRQ updateRequestRQ) {
        Request request = findRequest(id);
        requestValidator.validateRequestUpdate(request);

        requestMapper.map(request, updateRequestRQ);
        repository.save(request);
        return requestMapper.map(request);
    }

    public void changeRequestStatus(Long id, RequestStatus status) {
        changeRequestStatus(id, status, null);
    }

    public void changeRequestStatus(Long id, RequestStatus status, String reason) {
        Request request = findRequest(id);
        requestValidator.validateStatusChange(request, status);

        applicationEventPublisher.publishEvent(new CreateHistoryEvent(id, request.getStatus(), status, reason, new Date()));

        if (status.equals(PUBLISHED)) {
            request.setUniqueNumber(RandomStringUtils.randomNumeric(30));
        }
        request.setStatus(status);
        repository.save(request);
    }

    public Page<RequestRS> findAllBy(String title, RequestStatus status, Pageable pageable) {
        Page<Request> requests = repository.findAllByTitleAndStatus(title, status, pageable);
        return requests.map(requestMapper::map);
    }

    private Request findRequest(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationException(REQUEST_NOT_FOUND, id));
    }
}
