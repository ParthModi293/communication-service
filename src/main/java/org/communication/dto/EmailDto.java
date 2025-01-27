package org.communication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class EmailDto {
    private List<String> to;
    private String from;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String body;
    private Map<String, String> placeholders;
    private List<String> attachments;
    private String version;
    private String host;
    private int port;
    private String password;
    private int priority;
}
