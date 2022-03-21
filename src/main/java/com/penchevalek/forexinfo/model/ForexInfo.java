package com.penchevalek.forexinfo.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Data
@Entity
@RedisHash(value = "forexInfoCurrent", timeToLive = 360000)
public class ForexInfo implements Serializable {

    @EmbeddedId
    private ForexInfoId id;
    private LocalDate date;
    private HashMap<String, BigDecimal> rates;

    @Embeddable
    @Data
    public static class ForexInfoId implements Serializable {
        private Long timestamp;
        private String base;

        public ForexInfoId() {
        }
        public ForexInfoId(Long timestamp, String base) {
            this.timestamp = timestamp;
            this.base = base;
        }
    }
}
