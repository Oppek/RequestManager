package com.oposdevelopment.requestsmanager.exception.mapper;

import com.oposdevelopment.requestsmanager.exception.ApplicationException;
import com.oposdevelopment.requestsmanager.exception.dto.ApplicationExceptionRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class)
public interface ApplicationExceptionMapper {

    @Mapping(target = "timestamp", expression = "java(ZonedDateTime.now())")
    ApplicationExceptionRS map(ApplicationException exception);
}
