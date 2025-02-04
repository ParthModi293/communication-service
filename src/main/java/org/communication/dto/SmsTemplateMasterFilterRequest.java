package org.communication.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SmsTemplateMasterFilterRequest {


    private String searchText;

    @Min(value = 1, message = "Page number minimum 1")
    private int page;

    @Min(value = 1, message = "Page Size minimum 1")
    private int Size;
}
