package com.oposdevelopment.requestsmanager.rest;

import com.oposdevelopment.requestsmanager.domain.history.dto.HistoryRS;
import com.oposdevelopment.requestsmanager.domain.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{requestId:\\d+}")
    public ResponseEntity<List<HistoryRS>> deleteRequest(@PathVariable @Min(1) @NotNull Long requestId) {
        return new ResponseEntity<>(historyService.findAllRequestHistory(requestId), HttpStatus.OK);
    }
}
