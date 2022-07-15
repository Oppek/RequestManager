package com.oposdevelopment.requestsmanager.domain.history;

import com.oposdevelopment.requestsmanager.domain.request.model.RequestStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long requestId;

    @Column
    @Enumerated
    private RequestStatus previousStatus;

    @Column
    @Enumerated
    private RequestStatus currentStatus;

    @Column
    private String changeReason;

    @Column
    private Date modificationDate;
}
