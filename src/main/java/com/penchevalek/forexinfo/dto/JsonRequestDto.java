package com.penchevalek.forexinfo.dto;

import lombok.Data;

@Data
public class JsonRequestDto {

    private String requestId;
    private Integer timestamp;
    private String client;
    private String currency;
    private Integer period;
}
