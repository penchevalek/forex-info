package com.penchevalek.forexinfo.config.rediscustomconverter;

import com.penchevalek.forexinfo.model.ForexInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class RedisWritingByteConverter implements Converter<ForexInfo.ForexInfoId, byte[]> {

    Jackson2JsonRedisSerializer<ForexInfo.ForexInfoId> jackson2JsonRedisSerializer =
            new Jackson2JsonRedisSerializer(ForexInfo.ForexInfoId.class);

    @Override
    public byte[] convert(ForexInfo.ForexInfoId source) {
        return jackson2JsonRedisSerializer.serialize(source);
    }
}
