package com.clapcle.communication.dto;

import com.clapcle.communication.common.ConstCommunication;
import com.clapcle.communication.common.EnumCommunication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MailTemplateMastDto {

    private Integer id;

    @NotNull(message = "Event id can not be null or empty")
    private Integer eventId;

    @Pattern(regexp = ConstCommunication.PatternCheck.AlphaNumericSpace, message = "Enter valid template name")
    @Length(max = 255, message = "template name length up to 255 characters")
    @NotBlank(message = "template name cannot be null or empty")
    private String templateName;

    @Pattern(regexp = ConstCommunication.PatternCheck.REMARK, message = "Enter valid description")
    private String description;

    private EnumCommunication.PRIORITY priority;
}
