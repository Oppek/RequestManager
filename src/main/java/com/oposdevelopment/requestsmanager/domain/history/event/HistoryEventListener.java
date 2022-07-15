package com.oposdevelopment.requestsmanager.domain.history.event;

import com.oposdevelopment.requestsmanager.domain.history.HistoryRepository;
import com.oposdevelopment.requestsmanager.domain.history.mapper.HistoryMapper;
import com.oposdevelopment.requestsmanager.domain.request.event.CreateHistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HistoryEventListener {

    private final HistoryRepository repository;
    private final HistoryMapper historyMapper;

    @EventListener
    public void handleCreateHistoryEvent(CreateHistoryEvent event) {
        repository.save(historyMapper.map(event));
    }
}
