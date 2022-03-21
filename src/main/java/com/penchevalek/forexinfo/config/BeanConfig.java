package com.penchevalek.forexinfo.config;

import com.penchevalek.forexinfo.config.rediscustomconverter.RedisReadingByteConverter;
import com.penchevalek.forexinfo.config.rediscustomconverter.RedisReadingStringConverter;
import com.penchevalek.forexinfo.config.rediscustomconverter.RedisWritingByteConverter;
import com.penchevalek.forexinfo.config.rediscustomconverter.RedisWritingStringConverter;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class BeanConfig {

    @Value("${spring.rabbitmq.template.exchange:client-request-exchange}")
    private String exchangeName;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Exchange exchange(){
        return new TopicExchange(exchangeName);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(RedisReadingByteConverter readingConverter,
                                                         RedisWritingByteConverter redisWritingConverter,
                                                         RedisWritingStringConverter redisWritingStringConverter,
                                                         RedisReadingStringConverter redisReadingStringConverter) {
        return new RedisCustomConversions(Arrays.asList(
                readingConverter, redisWritingConverter, redisWritingStringConverter, redisReadingStringConverter));
    }
}
