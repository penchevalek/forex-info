package com.penchevalek.forexinfo.services;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.exceptions.CurrencyNotSupportedException;
import com.penchevalek.forexinfo.exceptions.DuplicatedRequestException;
import com.penchevalek.forexinfo.mapper.ClientRequestMapper;
import com.penchevalek.forexinfo.model.ClientRequest;
import com.penchevalek.forexinfo.model.ForexInfo;
import com.penchevalek.forexinfo.repository.ClientRequestRepository;
import com.penchevalek.forexinfo.repository.ForexInfoRepository;
import com.penchevalek.forexinfo.service.ForexInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.penchevalek.forexinfo.TestObjectsFactory.createForexInfo;
import static com.penchevalek.forexinfo.TestObjectsFactory.createJsonRequestDto;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@Import(ForexInfoService.class)
@ExtendWith(SpringExtension.class)
public class ForexInfoServiceTest {

    private static final String BASE = "EUR";
    private static final JsonRequestDto JSON_REQUEST_DTO = createJsonRequestDto();
    private static final ForexInfo FOREX_INFO = createForexInfo();

    @Autowired
    private ForexInfoService forexInfoService;

    @MockBean
    private ClientRequestRepository clientRequestRepository;

    @MockBean
    private ForexInfoRepository forexInfoRepository;

    @MockBean
    private ClientRequestMapper clientRequestMapper;

    @Test
    public void getCurrentInfo_UniqueRequestSupportedCurrency_ForexInfoIsReturned() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.empty());
        given(forexInfoRepository.findLatestBy(BASE)).willReturn(Optional.of(FOREX_INFO));

        ForexInfo result = forexInfoService.getCurrentInfo(JSON_REQUEST_DTO);

        assertEquals(FOREX_INFO, result);
    }

    @Test
    public void getCurrentInfo_NotUniqueRequest_DuplicatedRequestExceptionIsThrown() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.of(new ClientRequest()));

        DuplicatedRequestException result = assertThrows(DuplicatedRequestException.class,
                () -> forexInfoService.getCurrentInfo(JSON_REQUEST_DTO));

        String expectedMessage = format("Request with id: %s already exists.", JSON_REQUEST_DTO.getRequestId());
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void getCurrentInfo_NotSupportedCurrency_CurrencyNotSupportedExceptionIsThrown() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.empty());
        given(forexInfoRepository.findLatestBy(BASE)).willReturn(Optional.empty());

        CurrencyNotSupportedException result = assertThrows(CurrencyNotSupportedException.class,
                () -> forexInfoService.getCurrentInfo(JSON_REQUEST_DTO));

        String expectedMessage = format("Currency: %s is not supported currently.", JSON_REQUEST_DTO.getCurrency());
        assertEquals(expectedMessage, result.getMessage());
    }
}
