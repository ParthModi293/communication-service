package com.clapcle.communication.dto;

import com.clapcle.core.common.EnumCore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SmsSenderMasterDto {

    @NotNull(message = "Sender code is required")
    @Length(max = 45, message = "Sender code must be at most 45 characters")
    private String senderCode;

    @NotNull(message = "Service provider ID is required")
    private Integer serviceProviderId;

    @NotNull(message = "Active status is required")
    @EnumCore(enumClass = EnumCore.Y_N.class, message = "Enter valid status")
    private String isActive;
}
