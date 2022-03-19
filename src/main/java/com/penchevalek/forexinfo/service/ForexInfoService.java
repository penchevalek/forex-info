package com.penchevalek.forexinfo.service;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.exceptions.CurrencyNotSupportedException;
import com.penchevalek.forexinfo.exceptions.DuplicatedRequestException;
import com.penchevalek.forexinfo.mapper.ClientRequestMapper;
import com.penchevalek.forexinfo.model.ForexInfo;
import com.penchevalek.forexinfo.repository.ClientRequestRepository;
import com.penchevalek.forexinfo.repository.ForexInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.String.format;

@Service
@Log4j2
@RequiredArgsConstructor
public class ForexInfoService {

    private static final String DUPLICATED_REQUEST_EXCEPTION_MESSAGE = "Request with id: %s already exists.";
    private static final String CURRENCY_NOT_SUPPORTED_EXCEPTION_MESSAGE = "Currency: %s is not supported currently.";
    private static final String MISSING_PERIOD_EXCEPTION_MESSAGE = "Period cannot be null in request for historical data";

    private final ClientRequestRepository clientRequestRepository;
    private final ForexInfoRepository forexInfoRepository;
    private final ClientRequestMapper clientRequestMapper;

    public ForexInfo getCurrentInfo(JsonRequestDto jsonRequestDto) {
        validateRequest(jsonRequestDto.getRequestId());
        clientRequestRepository.save(clientRequestMapper.map(jsonRequestDto));
        return getLatestForexInfo(jsonRequestDto.getCurrency());
    }

    public List<ForexInfo> getHistoricalData(JsonRequestDto jsonRequestDto) {
        validateRequest(jsonRequestDto.getRequestId());
        clientRequestRepository.save(clientRequestMapper.map(jsonRequestDto));
        return forexInfoRepository.findAllByForexInfoIdGreaterThanEqual(getForexInfoId(jsonRequestDto));
    }

    @Cacheable(value = "forexInfoCurrent")
    public ForexInfo getLatestForexInfo(String base) {
        return forexInfoRepository.findLatestBy(base)
                .orElseThrow(() -> {
                    log.warn(format(CURRENCY_NOT_SUPPORTED_EXCEPTION_MESSAGE, base));
                    throw new CurrencyNotSupportedException(format(CURRENCY_NOT_SUPPORTED_EXCEPTION_MESSAGE, base));
                });
    }

    private void validateRequest(String requestId) {
        clientRequestRepository.findById(requestId)
                .ifPresent(clientRequest -> {
                    log.warn(format(DUPLICATED_REQUEST_EXCEPTION_MESSAGE, requestId));
                    throw new DuplicatedRequestException(format(DUPLICATED_REQUEST_EXCEPTION_MESSAGE, requestId));
                });
    }

    private ForexInfo.ForexInfoId getForexInfoId(JsonRequestDto jsonRequestDto) {
        if (jsonRequestDto.getPeriod() == null) {
            throw new IllegalArgumentException(MISSING_PERIOD_EXCEPTION_MESSAGE);
        }
        Instant start = Instant.now().minus(jsonRequestDto.getPeriod(), ChronoUnit.HOURS);
        ForexInfo.ForexInfoId forexInfoId = new ForexInfo.ForexInfoId();
        forexInfoId.setBase(jsonRequestDto.getCurrency());
        forexInfoId.setTimestamp(start.getEpochSecond());
        return forexInfoId;
    }
}
