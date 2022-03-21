package com.penchevalek.forexinfo.config.rediscustomconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penchevalek.forexinfo.model.ForexInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ReadingConverter
@Log4j2
public class RedisReadingStringConverter implements Converter<String, ForexInfo.ForexInfoId> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ForexInfo.ForexInfoId convert(String source) {
        try {
            return objectMapper.readValue(source, ForexInfo.ForexInfoId.class);
        } catch (IOException e) {
            log.warn("Error while converting to ObjectId.", e);
            throw new IllegalArgumentException("Can not convert to ObjectId");
        }
    }
}
