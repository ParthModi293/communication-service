package com.clapcle.communication.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SmsDto {

    private String template_id;
    private String short_url;
    private String short_url_expiry;
    private String realTimeResponse;
    private List<Map<String, Object>> recipients;

}
