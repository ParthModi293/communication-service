package org.communication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.communication.common.Const;
import org.communication.common.Enum;


@Data
public class TemplateDetailsDto {

    private int id;
    private String subject;
    private String body;
    private int templateMastId;
    @Enum(enumClass = org.common.common.Enum.Y_N.class, message = "Enter valid status")
    private String isActive;

    @Pattern(regexp = Const.PatternCheck.EMAIL, message = "Enter valid Email")
    @NotBlank(message = "Enter Valid Email")
    private String fromEmailId;

}
