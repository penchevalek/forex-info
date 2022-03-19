package com.penchevalek.forexinfo.controller;

import com.penchevalek.forexinfo.dto.JsonRequestDto;
import com.penchevalek.forexinfo.model.ForexInfo;
import com.penchevalek.forexinfo.service.ForexInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${spring.application.name:forex_info}/json_api")
@Log4j2
@RequiredArgsConstructor
public class ForexInfoJsonController {

    private final ForexInfoService forexInfoService;

    @PostMapping("/current")
    public ResponseEntity<ForexInfo> getCurrent(@RequestBody JsonRequestDto jsonRequestDto) {
        log.info("Received json request for current data with id: {}", jsonRequestDto.getRequestId());
        return ResponseEntity.ok(forexInfoService.getCurrentInfo(jsonRequestDto));
    }

    @PostMapping("/history")
    public ResponseEntity<List<ForexInfo>> getHistory(@RequestBody JsonRequestDto jsonRequestDto) {
        log.info("Received json request for history data with id: {}", jsonRequestDto.getRequestId());
        return ResponseEntity.ok(forexInfoService.getHistoricalData(jsonRequestDto));
    }
}
