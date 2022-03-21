package com.penchevalek.forexinfo.services;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.dto.XmlHistoryDto;
import com.penchevalek.forexinfo.dto.XmlRequestDto;
import com.penchevalek.forexinfo.exceptions.CurrencyNotSupportedException;
import com.penchevalek.forexinfo.exceptions.DuplicatedRequestException;
import com.penchevalek.forexinfo.mapper.ClientRequestMapper;
import com.penchevalek.forexinfo.model.ClientRequest;
import com.penchevalek.forexinfo.model.ForexInfo;
import com.penchevalek.forexinfo.repository.jpa.ClientRequestRepository;
import com.penchevalek.forexinfo.repository.redis.ForexInfoRepository;
import com.penchevalek.forexinfo.service.ForexInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.penchevalek.forexinfo.TestObjectsFactory.*;
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
    private static final XmlRequestDto XML_REQUEST_DTO_FOR_CURRENT_INFO = createXmlRequestDtoForCurrentInfo();
    private static final XmlRequestDto XML_REQUEST_DTO_FOR_HISTORICAL_DATA = createXmlRequestDtoForHistoricalData();

    @Autowired
    private ForexInfoService forexInfoService;

    @MockBean
    private ClientRequestRepository clientRequestRepository;

    @MockBean
    private ForexInfoRepository forexInfoRepository;

    @MockBean
    private ClientRequestMapper clientRequestMapper;

    @Test
    public void getCurrentInfo_UniqueRequestIdSupportedCurrency_ForexInfoIsReturned() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.empty());
        given(forexInfoRepository.findLatestBy(BASE)).willReturn(Optional.of(FOREX_INFO));

        ForexInfo result = forexInfoService.getCurrentInfo(JSON_REQUEST_DTO);

        assertEquals(FOREX_INFO, result);
    }

    @Test
    public void getCurrentInfo_NotUniqueRequestId_DuplicatedRequestExceptionIsThrown() {
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

    @Test
    public void getHistoricalData_UniqueRequestIdPeriodIsNotNull_ListOfForexInfoIsReturned() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.empty());
        Instant start = Instant.now().minus(JSON_REQUEST_DTO.getPeriod(), ChronoUnit.HOURS);
        ForexInfo.ForexInfoId forexInfoId = new ForexInfo.ForexInfoId();
        forexInfoId.setBase(JSON_REQUEST_DTO.getCurrency());
        forexInfoId.setTimestamp(start.getEpochSecond());
        given(forexInfoRepository.findAllByForexInfoIdGreaterThanEqual(forexInfoId))
                .willReturn(List.of(FOREX_INFO));

        List<ForexInfo> result = forexInfoService.getHistoricalData(JSON_REQUEST_DTO);

        assertEquals(List.of(FOREX_INFO), result);
    }

    @Test
    public void getHistoricalData_NotUniqueRequestId_DuplicatedRequestExceptionIsThrown() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.of(new ClientRequest()));

        DuplicatedRequestException result = assertThrows(DuplicatedRequestException.class,
                () -> forexInfoService.getHistoricalData(JSON_REQUEST_DTO));

        String expectedMessage = format("Request with id: %s already exists.", JSON_REQUEST_DTO.getRequestId());
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void getHistoricalData_PeriodIsNull_IllegalArgumentExceptionIsThrown() {
        given(clientRequestRepository.findById(JSON_REQUEST_DTO.getRequestId()))
                .willReturn(Optional.empty());
        JsonRequestDto requestDto = new JsonRequestDto();
        requestDto.setRequestId(JSON_REQUEST_DTO.getRequestId());

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> forexInfoService.getHistoricalData(requestDto));

        String expectedMessage = "Period cannot be null in request for historical data";
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void handleXmlRequest_UniqueRequestIdRequestForCurrentInfo_ListOfForexInfoIsReturned() {
        given(clientRequestRepository.findById(XML_REQUEST_DTO_FOR_CURRENT_INFO.getId()))
                .willReturn(Optional.empty());
        given(forexInfoRepository.findLatestBy(BASE)).willReturn(Optional.of(FOREX_INFO));

        List<ForexInfo> result = forexInfoService.handleXmlRequest(XML_REQUEST_DTO_FOR_CURRENT_INFO);

        assertEquals(List.of(FOREX_INFO), result);
    }

    @Test
    public void handleXmlRequest_NotUniqueRequestId_DuplicatedRequestExceptionIsThrown() {
        given(clientRequestRepository.findById(XML_REQUEST_DTO_FOR_CURRENT_INFO.getId()))
                .willReturn(Optional.of(new ClientRequest()));

        DuplicatedRequestException result = assertThrows(DuplicatedRequestException.class,
                () -> forexInfoService.handleXmlRequest(XML_REQUEST_DTO_FOR_CURRENT_INFO));

        String expectedMessage = format("Request with id: %s already exists.", XML_REQUEST_DTO_FOR_CURRENT_INFO.getId());
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void handleXmlRequest_UniqueRequestIdRequestForHistoricalData_ListOfForexInfoIsReturned() {
        given(clientRequestRepository.findById(XML_REQUEST_DTO_FOR_HISTORICAL_DATA.getId()))
                .willReturn(Optional.empty());
        Instant start = Instant.now().minus(XML_REQUEST_DTO_FOR_HISTORICAL_DATA.getHistory().getPeriod(), ChronoUnit.HOURS);
        ForexInfo.ForexInfoId forexInfoId = new ForexInfo.ForexInfoId();
        forexInfoId.setBase(XML_REQUEST_DTO_FOR_HISTORICAL_DATA.getHistory().getCurrency());
        forexInfoId.setTimestamp(start.getEpochSecond());
        given(forexInfoRepository.findAllByForexInfoIdGreaterThanEqual(forexInfoId))
                .willReturn(List.of(FOREX_INFO));

        List<ForexInfo> result = forexInfoService.handleXmlRequest(XML_REQUEST_DTO_FOR_HISTORICAL_DATA);

        assertEquals(List.of(FOREX_INFO), result);
    }

    @Test
    public void handleXmlRequest_PeriodIsNullRequestForHistoricalData_IllegalArgumentExceptionIsThrown() {
        given(clientRequestRepository.findById(XML_REQUEST_DTO_FOR_HISTORICAL_DATA.getId()))
                .willReturn(Optional.empty());
        XmlRequestDto xmlRequestDto = new XmlRequestDto();
        XmlHistoryDto historyDto = new XmlHistoryDto();
        xmlRequestDto.setHistory(historyDto);
        xmlRequestDto.setId(XML_REQUEST_DTO_FOR_HISTORICAL_DATA.getId());

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> forexInfoService.handleXmlRequest(xmlRequestDto));

        String expectedMessage = "Period cannot be null in request for historical data";
        assertEquals(expectedMessage, result.getMessage());
    }
}
