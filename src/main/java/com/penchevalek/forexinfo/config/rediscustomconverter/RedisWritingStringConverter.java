package com.penchevalek.forexinfo.config.rediscustomconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penchevalek.forexinfo.model.ForexInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
@Log4j2
public class RedisWritingStringConverter implements Converter<ForexInfo.ForexInfoId, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(ForexInfo.ForexInfoId source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.warn("Error while converting ObjectId to String.", e);
            throw new IllegalArgumentException("Can not convert ObjectId to String");
        }
    }
}
