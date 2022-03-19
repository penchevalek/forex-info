package com.penchevalek.forexinfo.dto;

import lombok.Data;

@Data
public class XmlRequestDto {

    private String id;
    private XmlCurrentInfoDto get;
    private XmlHistoryDto history;
}
