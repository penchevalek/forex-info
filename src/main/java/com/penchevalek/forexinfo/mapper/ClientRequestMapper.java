package com.penchevalek.forexinfo.mapper;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.model.ClientRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientRequestMapper {

    public ClientRequest map(JsonRequestDto jsonRequestDto) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setRequestId(jsonRequestDto.getRequestId());
        clientRequest.setClient(jsonRequestDto.getClient());
        clientRequest.setCurrency(jsonRequestDto.getCurrency());
        clientRequest.setTimestamp(jsonRequestDto.getTimestamp());
        clientRequest.setPeriod(jsonRequestDto.getPeriod());
        return clientRequest;
    }
}
