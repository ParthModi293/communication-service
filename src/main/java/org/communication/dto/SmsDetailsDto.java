package org.communication.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link org.communication.entity.SmsMaster}
 */
@Data
public class SmsDetailsDto implements Serializable {
    private String priority;
    private int id;
    private String version;
    private String govTemplateCode;
    private String serviceProviderTemplateCode;
    private String senderCode;
    private String apiKey;
    private String url;

    public SmsDetailsDto(String priority, int id, String version, String govTemplateCode, String serviceProviderTemplateCode, String senderCode, String apiKey, String url) {
        this.priority = priority;
        this.id = id;
        this.version = version;
        this.govTemplateCode = govTemplateCode;
        this.serviceProviderTemplateCode = serviceProviderTemplateCode;
        this.senderCode = senderCode;
        this.apiKey = apiKey;
        this.url = url;
    }
}