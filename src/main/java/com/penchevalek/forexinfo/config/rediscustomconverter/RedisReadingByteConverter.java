package com.penchevalek.forexinfo.config.rediscustomconverter;

import com.penchevalek.forexinfo.model.ForexInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class RedisReadingByteConverter implements Converter<byte[], ForexInfo.ForexInfoId> {

    Jackson2JsonRedisSerializer<ForexInfo.ForexInfoId> jackson2JsonRedisSerializer =
            new Jackson2JsonRedisSerializer(ForexInfo.ForexInfoId.class);

    @Override
    public ForexInfo.ForexInfoId convert(byte[] source) {
        return jackson2JsonRedisSerializer.deserialize(source);
    }
}
