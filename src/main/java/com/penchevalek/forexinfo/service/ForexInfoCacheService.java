package com.penchevalek.forexinfo.service;

import com.penchevalek.forexinfo.model.ForexInfo;
import com.penchevalek.forexinfo.repository.ForexInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class ForexInfoCacheService {

    private final ForexInfoRepository forexInfoRepository;

    @Autowired
    public ForexInfoCacheService(ForexInfoRepository forexInfoRepository) {
        this.forexInfoRepository = forexInfoRepository;
    }

    @CachePut(value = "forexInfoCurrent")
    public void storeForexInfo(ForexInfo forexInfo) {
        forexInfoRepository.save(forexInfo);
    }

//    @Cacheable(value = "forexInfoCurrent")
//    public ForexInfo getCurrentForexInfo(String base) {
//        return forexInfoRepository.findByBaseOrderByTimestampDesc(base).orElseThrow(CurrencyNotSupportedException::new);
//    }
}
