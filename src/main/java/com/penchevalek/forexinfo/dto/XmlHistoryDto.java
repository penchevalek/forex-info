package com.penchevalek.forexinfo.dto;

import lombok.Data;

@Data
public class XmlHistoryDto {

    private String consumer;
    private String currency;
    private Integer period;
}
