package com.penchevalek.forexinfo;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.dto.XmlCurrentInfoDto;
import com.penchevalek.forexinfo.dto.XmlHistoryDto;
import com.penchevalek.forexinfo.dto.XmlRequestDto;
import com.penchevalek.forexinfo.model.ForexInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class TestObjectsFactory {

    private static final String BASE = "EUR";

    public static JsonRequestDto createJsonRequestDto() {
        JsonRequestDto jsonRequestDto = new JsonRequestDto();
        jsonRequestDto.setRequestId("1");
        jsonRequestDto.setClient("Client-1");
        jsonRequestDto.setCurrency(BASE);
        jsonRequestDto.setTimestamp(2365478);
        jsonRequestDto.setPeriod(12);
        return jsonRequestDto;
    }

    public static ForexInfo createForexInfo() {
        ForexInfo forexInfo = new ForexInfo();
        ForexInfo.ForexInfoId forexInfoId = new ForexInfo.ForexInfoId();
        forexInfoId.setTimestamp(123456L);
        forexInfoId.setBase(BASE);
        forexInfo.setForexInfoId(forexInfoId);
        forexInfo.setDate(LocalDate.of(2022, 3, 19));
        HashMap<String, BigDecimal> rates = new HashMap<>();
        rates.put("STD", BigDecimal.valueOf(22897.252535));
        rates.put("LVL", BigDecimal.valueOf(0.669163));
        rates.put("SCR", BigDecimal.valueOf(15.939077));
        rates.put("CDF", BigDecimal.valueOf(2225.786195));
        rates.put("BBD", BigDecimal.valueOf(2.232999));
        rates.put("GTQ", BigDecimal.valueOf(8.521033));
        rates.put("CLP", BigDecimal.valueOf(888.887686));
        rates.put("HNL", BigDecimal.valueOf(26.970936));
        forexInfo.setRates(rates);
        return forexInfo;
    }

    public static XmlRequestDto createXmlRequestDtoForCurrentInfo() {
        XmlRequestDto xmlRequestDto = new XmlRequestDto();
        xmlRequestDto.setId("17");
        XmlCurrentInfoDto currentInfoDto = new XmlCurrentInfoDto();
        currentInfoDto.setCurrency(BASE);
        currentInfoDto.setConsumer("Consumer");
        xmlRequestDto.setGet(currentInfoDto);
        return xmlRequestDto;
    }

    public static XmlRequestDto createXmlRequestDtoForHistoricalData() {
        XmlRequestDto xmlRequestDto = new XmlRequestDto();
        xmlRequestDto.setId("19");
        XmlHistoryDto historyDto = new XmlHistoryDto();
        historyDto.setCurrency(BASE);
        historyDto.setConsumer("Consumer-2");
        historyDto.setPeriod(24);
        xmlRequestDto.setHistory(historyDto);
        return xmlRequestDto;
    }
}
