package org.communication.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.common.common.Enum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Data
public class SmsSenderMasterDto {

    @NotNull(message = "Sender code is required")
    @Length(max = 45, message = "Sender code must be at most 45 characters")
    private String senderCode;

    @NotNull(message = "Service provider ID is required")
    private Integer serviceProviderId;

    @NotNull(message = "Active status is required")
    @Enum(enumClass = org.common.common.Enum.Y_N.class, message = "Enter valid status")
    private String isActive;
}
