package com.penchevalek.forexinfo.service.integration;

import com.penchevalek.forexinfo.model.ForexInfo;
import com.penchevalek.forexinfo.repository.ForexInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ForexInfoFixerProvider {

    private static final String QUERY_PARAMETER_ACCESS_KEY = "?access_key=";

    private final WebClient webClient;
    private final String path;
    private final ForexInfoRepository forexInfoRepository;

    @Autowired
    public ForexInfoFixerProvider(WebClient webClient,
                                  @Value("${fixer.uri}") String uri,
                                  @Value("${fixer.accessKey}") String accessKey,
                                  ForexInfoRepository forexInfoRepository) {
        this.webClient = webClient;
        this.path = uri + QUERY_PARAMETER_ACCESS_KEY + accessKey;
        this.forexInfoRepository = forexInfoRepository;
    }

    @Scheduled(fixedRateString = "${fixer.fixedRate}")
    public void getAndStoreForexInfo() {
        ForexInfo forexInfo = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(ForexInfo.class)
                .block();
        storeForexInfo(forexInfo);
    }

    private void storeForexInfo(ForexInfo forexInfo) {
        forexInfoRepository.save(forexInfo);
    }
}
