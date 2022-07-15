package com.oposdevelopment.requestsmanager.domain.request.validation;

import com.oposdevelopment.requestsmanager.domain.request.Request;
import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import com.oposdevelopment.requestsmanager.exception.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus.*;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class RequestValidatorTest {
    RequestValidator requestValidator;

    @BeforeEach
    void init() {
        requestValidator = new RequestValidator();
    }

    @ParameterizedTest
    @EnumSource(value = RequestStatus.class, names = {"CREATED", "VERIFIED"})
    void shouldPassUpdateValidation(RequestStatus requestStatus) {
        // given
        Request request = buildRequest(requestStatus);

        // when
        Throwable throwable = catchThrowable(() -> requestValidator.validateRequestUpdate(request));

        // then
        Assertions.assertThat(throwable).isNull();
    }

    @ParameterizedTest
    @EnumSource(value = RequestStatus.class, names = {"ACCEPTED", "DELETED", "PUBLISHED", "REJECTED"})
    void shouldThrowWhenValidationFails(RequestStatus requestStatus) {
        // given
        Request request = buildRequest(requestStatus);

        // when
        Throwable throwable = catchThrowable(() -> requestValidator.validateRequestUpdate(request));

        // then
        Assertions.assertThat(throwable)
                .hasMessage("Can not update a content of request in %s status.", requestStatus);
    }

    @ParameterizedTest
    @MethodSource("getStatusCombination")
    void validateStatusChange(Request request, RequestStatus targetStatus) {
        // when
        Throwable throwable = catchThrowable(() -> requestValidator.validateStatusChange(request, targetStatus));

        // then
        Assertions.assertThat(throwable).isInstanceOf(ApplicationException.class);
    }

    private static Request buildRequest(RequestStatus status) {
        Request request = new Request();
        request.setId(1L);
        request.setTitle("Title");
        request.setContent("Very long valid content of this request");
        request.setStatus(status);
        return request;
    }

    private static Stream<Arguments> getStatusCombination() {
        return Stream.of(
                Arguments.of(buildRequest(DELETED), DELETED),
                Arguments.of(buildRequest(REJECTED), DELETED),
                Arguments.of(buildRequest(VERIFIED), DELETED),
                Arguments.of(buildRequest(ACCEPTED), DELETED),
                Arguments.of(buildRequest(PUBLISHED), DELETED),
                Arguments.of(buildRequest(DELETED), VERIFIED),
                Arguments.of(buildRequest(REJECTED), VERIFIED),
                Arguments.of(buildRequest(VERIFIED), VERIFIED),
                Arguments.of(buildRequest(ACCEPTED), VERIFIED),
                Arguments.of(buildRequest(PUBLISHED), VERIFIED),
                Arguments.of(buildRequest(DELETED), REJECTED),
                Arguments.of(buildRequest(CREATED), REJECTED),
                Arguments.of(buildRequest(REJECTED), REJECTED),
                Arguments.of(buildRequest(PUBLISHED), REJECTED),
                Arguments.of(buildRequest(DELETED), ACCEPTED),
                Arguments.of(buildRequest(REJECTED), ACCEPTED),
                Arguments.of(buildRequest(CREATED), ACCEPTED),
                Arguments.of(buildRequest(ACCEPTED), ACCEPTED),
                Arguments.of(buildRequest(PUBLISHED), ACCEPTED),
                Arguments.of(buildRequest(DELETED), PUBLISHED),
                Arguments.of(buildRequest(REJECTED), PUBLISHED),
                Arguments.of(buildRequest(CREATED), PUBLISHED),
                Arguments.of(buildRequest(VERIFIED), PUBLISHED),
                Arguments.of(buildRequest(PUBLISHED), PUBLISHED)
        );
    }
}