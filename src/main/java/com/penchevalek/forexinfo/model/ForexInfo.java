package com.penchevalek.forexinfo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Data
@Entity
public class ForexInfo implements Serializable {

    @EmbeddedId
    private ForexInfoId forexInfoId;
    private LocalDate date;
    private HashMap<String, BigDecimal> rates;

    @Embeddable
    @Data
    public static class ForexInfoId implements Serializable {
        private Long timestamp;
        private String base;
    }
}
