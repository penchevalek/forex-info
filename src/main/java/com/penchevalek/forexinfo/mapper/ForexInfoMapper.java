package com.penchevalek.forexinfo.mapper;

import com.penchevalek.forexinfo.dto.ForexInfoDto;
import com.penchevalek.forexinfo.model.ForexInfo;
import org.springframework.stereotype.Component;

@Component
public class ForexInfoMapper {

    public ForexInfo map(ForexInfoDto forexInfoDto) {
        ForexInfo forexInfo = new ForexInfo();
        ForexInfo.ForexInfoId forexInfoId = new ForexInfo.ForexInfoId();
        forexInfoId.setBase(forexInfoDto.getBase());
        forexInfoId.setTimestamp(forexInfoDto.getTimestamp());
        forexInfo.setId(forexInfoId);
        forexInfo.setDate(forexInfoDto.getDate());
        forexInfo.setRates(forexInfoDto.getRates());
        return forexInfo;
    }
}
