package com.oposdevelopment.requestsmanager.domain.request;

import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @Enumerated
    private RequestStatus status;

    @Column
    private String uniqueNumber;
}
