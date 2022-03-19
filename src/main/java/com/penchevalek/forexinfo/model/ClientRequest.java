package com.penchevalek.forexinfo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ClientRequest {

    @Id
    private String requestId;
    private Integer timestamp;
    private String client;
    private String currency;
    private Integer period;
}
