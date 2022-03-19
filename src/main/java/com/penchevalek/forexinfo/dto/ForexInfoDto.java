package com.penchevalek.forexinfo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Data
public class ForexInfoDto {

    private Long timestamp;
    private String base;
    private boolean success;
    private LocalDate date;
    private HashMap<String, BigDecimal> rates;
}
