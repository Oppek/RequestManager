package com.oposdevelopment.requestsmanager.domain.request.service;

import com.oposdevelopment.requestsmanager.domain.request.Request;
import com.oposdevelopment.requestsmanager.domain.request.RequestRepository;
import com.oposdevelopment.requestsmanager.domain.request.dto.CreateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.dto.RequestRS;
import com.oposdevelopment.requestsmanager.domain.request.dto.UpdateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.event.CreateHistoryEvent;
import com.oposdevelopment.requestsmanager.domain.request.mapper.RequestMapper;
import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import com.oposdevelopment.requestsmanager.domain.request.validation.RequestValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @Mock
    RequestValidator requestValidator;
    @Mock
    RequestRepository requestRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;

    RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);

    RequestService requestService;

    @BeforeEach
    void init() {
        requestService = new RequestService(requestRepository, requestMapper, requestValidator, eventPublisher);
    }

    @Test
    void shouldProperlyCreateRequest() {
        // given
        CreateRequestRQ createRequestRQ = new CreateRequestRQ("Title", "Very long valid content of this request");
        when(requestRepository.save(any())).thenReturn(buildRequest());

        // when
        Long createdRequestId = requestService.create(createRequestRQ);

        // then
        Assertions.assertThat(createdRequestId).isEqualTo(1L);
        verify(eventPublisher, times(1)).publishEvent(any(CreateHistoryEvent.class));
    }

    @Test
    void shouldReturnProperRequest() {
        // given
        Long requestId = 1L;
        Request request = buildRequest();
        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        // when
        RequestRS foundRequestRS = requestService.getRequestById(requestId);

        // then
        Assertions.assertThat(foundRequestRS.id()).isEqualTo(request.getId());
        Assertions.assertThat(foundRequestRS.title()).isEqualTo(request.getTitle());
        Assertions.assertThat(foundRequestRS.content()).isEqualTo(request.getContent());
        Assertions.assertThat(foundRequestRS.status()).isEqualTo(request.getStatus());
    }

    @Test
    void shouldUpdateRequestContent() {
        // given
        Long requestId = 1L;
        UpdateRequestRQ updateRequestRQ = new UpdateRequestRQ("[UPDATED] Very long valid content of this request");
        Request request = buildRequest();
        request.setContent("[UPDATED] Very long valid content of this request");
        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        // when
        RequestRS foundRequestRS = requestService.updateRequestContent(requestId, updateRequestRQ);

        // then
        Assertions.assertThat(foundRequestRS.id()).isEqualTo(request.getId());
        Assertions.assertThat(foundRequestRS.title()).isEqualTo(request.getTitle());
        Assertions.assertThat(foundRequestRS.content()).isEqualTo(request.getContent());
        Assertions.assertThat(foundRequestRS.status()).isEqualTo(request.getStatus());
    }

    @Test
    void shouldThrowExceptionForNonExistingRequestId() {
        // given
        Long requestId = 1L;
        when(requestRepository.findById(requestId)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> requestService.getRequestById(requestId));

        // then
        Assertions.assertThat(throwable)
                .hasMessage("Can not find a request with a given id 1.");
    }

    @Test
    void shouldThrowExceptionForNonExistingRequestIdWhenUpdatingContent() {
        // given
        Long requestId = 1L;
        UpdateRequestRQ updateRequestRQ = new UpdateRequestRQ("[UPDATED] Very long valid content of this request");
        when(requestRepository.findById(requestId)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> requestService.updateRequestContent(requestId, updateRequestRQ));

        // then
        Assertions.assertThat(throwable)
                .hasMessage("Can not find a request with a given id 1.");
    }

    private Request buildRequest(){
        Request request = new Request();
        request.setId(1L);
        request.setTitle("Title");
        request.setContent("Very long valid content of this request");
        request.setStatus(RequestStatus.CREATED);
        return request;
    }


}