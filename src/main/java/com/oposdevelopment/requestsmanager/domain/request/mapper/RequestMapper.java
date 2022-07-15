package com.oposdevelopment.requestsmanager.domain.request.mapper;

import com.oposdevelopment.requestsmanager.domain.request.Request;
import com.oposdevelopment.requestsmanager.domain.request.dto.CreateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.dto.RequestRS;
import com.oposdevelopment.requestsmanager.domain.request.dto.UpdateRequestRQ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "uniqueNumber", ignore = true)
    Request map(CreateRequestRQ createRequestRQ);

    RequestRS map(Request request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "uniqueNumber", ignore = true)
    void map(@MappingTarget Request request, UpdateRequestRQ updateRequestRQ);
}
