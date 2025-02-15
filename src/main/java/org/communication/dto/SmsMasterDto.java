package org.communication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.communication.common.Const;
import org.communication.common.Enum;
import org.hibernate.validator.constraints.Length;

@Data
public class SmsMasterDto {

    private Integer id;

    @NotNull(message = "Template name is required")
    @Length(max = 45, message = "Template name must be at most 45 characters")
    @Pattern(regexp = Const.PatternCheck.SIZE45, message = "Template name must be at most 45 characters")
    private String templateName;

    @NotNull(message = "Event ID is required")
    private Integer eventId;

    @NotNull(message = "Priority is required")
    private Enum.PRIORITY priority;


}
