package com.clapcle.communication.dto;

import com.clapcle.communication.common.ConstCommunication;
import com.clapcle.communication.common.EnumCommunication;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SmsMasterDto {

    private Integer id;

    @NotNull(message = "Template name is required")
    @Length(max = 45, message = "Template name must be at most 45 characters")
    @Pattern(regexp = ConstCommunication.PatternCheck.SIZE45, message = "Template name must be at most 45 characters")
    private String templateName;

    @NotNull(message = "Event ID is required")
    private Integer eventId;

    @NotNull(message = "Priority is required")
    private EnumCommunication.PRIORITY priority;


}
