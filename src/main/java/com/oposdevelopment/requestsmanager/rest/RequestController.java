package com.oposdevelopment.requestsmanager.rest;

import com.oposdevelopment.requestsmanager.domain.request.dto.CreateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.dto.DeleteRejectRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.dto.RequestRS;
import com.oposdevelopment.requestsmanager.domain.request.dto.UpdateRequestRQ;
import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import com.oposdevelopment.requestsmanager.domain.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<RequestRS> getRequest(@PathVariable @Min(1) @NotNull Long id) {
        return new ResponseEntity<>(requestService.getRequestById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createRequest(@RequestBody @Valid CreateRequestRQ createRequestRQ) {
        return new ResponseEntity<>(requestService.create(createRequestRQ), HttpStatus.OK);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<RequestRS> updateRequest(@RequestBody @Valid UpdateRequestRQ updateRequestRQ, @PathVariable @Min(1) @NotNull Long id) {
        return new ResponseEntity<>(requestService.updateRequestContent(id, updateRequestRQ), HttpStatus.OK);
    }


    @PostMapping("/{id:\\d+}/reject")
    public ResponseEntity<Void> rejectRequest(@RequestBody @Valid DeleteRejectRequestRQ requestRQ, @PathVariable @Min(1) @NotNull Long id) {
        requestService.changeRequestStatus(id, REJECTED, requestRQ.reason());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id:\\d+}/delete")
    public ResponseEntity<Void> deleteRequest(@RequestBody @Valid DeleteRejectRequestRQ requestRQ, @PathVariable @Min(1) @NotNull Long id) {
        requestService.changeRequestStatus(id, DELETED, requestRQ.reason());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id:\\d+}/verify")
    public ResponseEntity<Void> verifyRequest(@PathVariable @Min(1) @NotNull Long id) {
        requestService.changeRequestStatus(id, VERIFIED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id:\\d+}/accept")
    public ResponseEntity<Void> acceptRequest(@PathVariable @Min(1) @NotNull Long id) {
        requestService.changeRequestStatus(id, ACCEPTED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id:\\d+}/publish")
    public ResponseEntity<Void> publishRequest(@PathVariable @Min(1) @NotNull Long id) {
        requestService.changeRequestStatus(id, PUBLISHED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RequestRS>> getAllRequests(@RequestParam String title,
                                                          @RequestParam RequestStatus status,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<RequestRS> requestRSPage = requestService.findAllBy(title, status, PageRequest.of(page, size));
        return new ResponseEntity<>(requestRSPage, HttpStatus.OK);
    }
}
