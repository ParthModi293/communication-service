package org.communication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.communication.common.Const;
import org.hibernate.validator.constraints.Length;

@Data
public class SmsProviderMasterDto {

    @NotNull(message = "Provider name is required")
    @Length(max = 255, message = "Provider name must be at most 255 characters")
    private String name;

    @NotNull(message = "API key is required")
    @Length(max = 255, message = "API key must be at most 255 characters")
    private String apiKey;

    @NotNull(message = "URL is required")
    @Length(max = 255, message = "URL must be at most 255 characters")
    @Pattern(regexp = Const.PatternCheck.URL, message = "Enter Valid url")
    private String url;

}
