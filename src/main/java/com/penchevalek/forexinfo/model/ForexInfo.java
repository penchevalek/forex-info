package com.penchevalek.forexinfo.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Data
@Entity
public class ForexInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean success;
    private Integer timestamp;
    private String base;
    private LocalDate date;
    private HashMap<String, BigDecimal> rates;
}
