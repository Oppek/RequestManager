package com.oposdevelopment.requestsmanager.domain.history.mapper;

import com.oposdevelopment.requestsmanager.domain.history.History;
import com.oposdevelopment.requestsmanager.domain.history.dto.HistoryRS;
import com.oposdevelopment.requestsmanager.domain.request.event.CreateHistoryEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(target = "id", ignore = true)
    History map(CreateHistoryEvent event);

    HistoryRS map(History history);
}
