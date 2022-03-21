package com.penchevalek.forexinfo.service.integration;

import com.penchevalek.forexinfo.dto.ForexInfoDto;
import com.penchevalek.forexinfo.mapper.ForexInfoMapper;
import com.penchevalek.forexinfo.repository.redis.ForexInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class ForexInfoFixerProvider {

    private static final String QUERY_PARAMETER_ACCESS_KEY = "?access_key=";

    private final WebClient webClient;
    private final String path;
    private final ForexInfoRepository forexInfoRepository;
    private final ForexInfoMapper forexInfoMapper;

    @Autowired
    public ForexInfoFixerProvider(WebClient webClient,
                                  @Value("${fixer.uri}") String uri,
                                  @Value("${fixer.accessKey}") String accessKey,
                                  ForexInfoRepository forexInfoRepository,
                                  ForexInfoMapper forexInfoMapper) {
        this.webClient = webClient;
        this.path = uri + QUERY_PARAMETER_ACCESS_KEY + accessKey;
        this.forexInfoRepository = forexInfoRepository;
        this.forexInfoMapper = forexInfoMapper;
    }

    @Scheduled(fixedRateString = "${fixer.fixedRate}")
    public void getAndStoreForexInfo() {
        log.info("Retrieving and storing forex info from fixer.io.");
        ForexInfoDto forexInfoDto = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(ForexInfoDto.class)
                .block();
        storeForexInfo(forexInfoDto);
    }

    @CachePut(value = "forexInfoCurrent")
    public void storeForexInfo(ForexInfoDto forexInfoDto) {
        forexInfoRepository.save(forexInfoMapper.map(forexInfoDto));
    }
}
