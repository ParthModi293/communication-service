package com.clapcle.communication.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsTemplateMasterFilterRequest {

    private String searchText;

    @Min(value = 1, message = "Page number minimum 1")
    private int page;

    @Min(value = 1, message = "Page Size minimum 1")
    private int size;
}
