package org.communication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsTemplateMasterDto {
    @NotNull(message = "Body is required")
    @Size(max = 1028, message = "Body must be at most 1028 characters")
    private String body;

    @NotNull(message = "Gov template code is required")
    @Size(max = 45, message = "Gov template code must be at most 45 characters")
    private String govTemplateCode;

    @Size(max = 45, message = "Service provider template code must be at most 45 characters")
    private String serviceProviderTemplateCode;

    @NotNull(message = "Sender ID is required")
    private Integer senderId;

    @NotNull(message = "SMS master ID is required")
    private Integer smsMasterId;

    @NotNull(message = "IsActive is required")
    @Size(max = 5, message = "IsActive must be at most 5 characters")
    private String isActive;

    @NotNull(message = "Version type is required")
    private String versionType;

    @Size(max = 255, message = "Created by must be at most 255 characters")
    private String createdBy;

    @Size(max = 255, message = "Updated by must be at most 255 characters")
    private String updatedBy;
}
