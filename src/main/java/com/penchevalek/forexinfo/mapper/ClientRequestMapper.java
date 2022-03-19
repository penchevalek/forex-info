package com.penchevalek.forexinfo.mapper;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.dto.XmlCurrentInfoDto;
import com.penchevalek.forexinfo.dto.XmlHistoryDto;
import com.penchevalek.forexinfo.dto.XmlRequestDto;
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

    public ClientRequest map(XmlRequestDto xmlRequestDto) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setRequestId(xmlRequestDto.getId());
        if (xmlRequestDto.getHistory() == null) {
            XmlCurrentInfoDto infoDto = xmlRequestDto.getGet();
            clientRequest.setClient(infoDto.getConsumer());
            clientRequest.setCurrency(infoDto.getCurrency());
        } else {
            XmlHistoryDto historyDto = xmlRequestDto.getHistory();
            clientRequest.setClient(historyDto.getConsumer());
            clientRequest.setCurrency(historyDto.getCurrency());
            clientRequest.setPeriod(historyDto.getPeriod());
        }
        return clientRequest;
    }
}
