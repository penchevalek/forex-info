package com.penchevalek.forexinfo.controller;

import com.penchevalek.forexinfo.dto.XmlRequestDto;
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

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("${spring.application.name:forex_info}/xml_api")
@Log4j2
@RequiredArgsConstructor
public class ForexInfoXmlController {

    private final ForexInfoService forexInfoService;

    @PostMapping(value = "/command", produces = APPLICATION_XML_VALUE, consumes = APPLICATION_XML_VALUE)
    public ResponseEntity<List<ForexInfo>> getCurrent(@RequestBody XmlRequestDto xmlRequestDto) {
        log.info("Received xml request with id: {}", xmlRequestDto.getId());
        return ResponseEntity.ok(forexInfoService.handleXmlRequest(xmlRequestDto));
    }
}
