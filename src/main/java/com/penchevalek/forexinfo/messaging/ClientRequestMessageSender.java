package com.penchevalek.forexinfo.messaging;

import com.penchevalek.forexinfo.model.ClientRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClientRequestMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(ClientRequest clientRequest) {
        log.info("Sending client request with id: {} through RabbitMQ", clientRequest.getRequestId());
        rabbitTemplate.convertAndSend(clientRequest);
    }
}
