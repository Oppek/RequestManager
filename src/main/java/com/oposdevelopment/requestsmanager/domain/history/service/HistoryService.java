package com.oposdevelopment.requestsmanager.domain.history.service;

import com.oposdevelopment.requestsmanager.domain.history.History;
import com.oposdevelopment.requestsmanager.domain.history.HistoryRepository;
import com.oposdevelopment.requestsmanager.domain.history.dto.HistoryRS;
import com.oposdevelopment.requestsmanager.domain.history.mapper.HistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public List<HistoryRS> findAllRequestHistory(Long requestId) {
        List<History> histories = historyRepository.findAllByRequestId(requestId);

        return histories.stream()
                .map(historyMapper::map)
                .collect(Collectors.toList());
    }
}
