package com.clapcle.communication.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class MailTemplateMastFilterRequest {

    private String searchText;

    private int eventId;

    @Min(value = 1, message = "Page number minimum 1")
    private int page;

    @Min(value = 1, message = "Page size minimum 1")
    private int size;
}
