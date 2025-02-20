package com.clapcle.communication.dto;

import com.clapcle.communication.entity.MailTemplateDetail;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link MailTemplateDetail}
 */
@Data
public class MailTemplateDto implements Serializable {

    private String subject;
    private String body;
    private Double version;
    private String fromEmailId;
    private String priority;
    private String password;
    private int port;
    private String host;

    public MailTemplateDto(String subject, String body, Double version, String fromEmailId, String priority, String password, int port, String host) {
        this.subject = subject;
        this.body = body;
        this.version = version;
        this.fromEmailId = fromEmailId;
        this.priority = priority;
        this.password = password;
        this.port = port;
        this.host = host;
    }
}