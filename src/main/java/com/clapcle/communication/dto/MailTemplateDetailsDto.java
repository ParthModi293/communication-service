package com.clapcle.communication.dto;

import com.clapcle.communication.common.ConstCommunication;
import com.clapcle.core.common.EnumCore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class MailTemplateDetailsDto {

    private int id;
    private String subject;
    private String body;
    private int templateMastId;
    @EnumCore(enumClass = EnumCore.Y_N.class, message = "Enter valid status")
    private String isActive;

    @Pattern(regexp = ConstCommunication.PatternCheck.EMAIL, message = "Enter valid Email")
    @NotBlank(message = "Enter Valid Email")
    private String fromEmailId;

}
