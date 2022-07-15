package com.oposdevelopment.requestsmanager.domain.request;

import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findAll(Pageable pageable);

    Page<Request> findAllByStatus(RequestStatus status, Pageable pageable);

    Page<Request> findAllByTitle(String title, Pageable pageable);

    Page<Request> findAllByTitleAndStatus(String title, RequestStatus status, Pageable pageable);
}
