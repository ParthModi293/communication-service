package com.clapcle.communication.dto;

import com.clapcle.core.common.EnumCore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SmsTemplateMasterDto {
    @NotNull(message = "Body is required")
    @Length(max = 1028, message = "Body must be at most 1028 characters")
    private String body;

    @NotNull(message = "Gov template code is required")
    @Length(max = 45, message = "Gov template code must be at most 45 characters")
    private String govTemplateCode;

    @Length(max = 45, message = "Service provider template code must be at most 45 characters")
    @NotNull(message = "Service provider template code is required")
    private String serviceProviderTemplateCode;

    @NotNull(message = "Sender ID is required")
    private Integer senderId;

    @NotNull(message = "SMS master ID is required")
    private Integer smsMasterId;

    @NotNull(message = "IsActive is required")
    @Length(max = 5, message = "IsActive must be at most 5 characters")
    @EnumCore(enumClass = EnumCore.Y_N.class, message = "Enter valid status")
    private String isActive;

    @NotNull(message = "Version type is required")
    @EnumCore(enumClass = EnumCore.MINOR_MAJOR.class, message = "Enter valid version type")
    private String versionType;

}
