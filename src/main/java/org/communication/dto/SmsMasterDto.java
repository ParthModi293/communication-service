package org.communication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsMasterDto {

    @NotNull(message = "Template name is required")
    @Size(max = 45, message = "Template name must be at most 45 characters")
    private String templateName;

    @NotNull(message = "Event ID is required")
    private Integer eventId;

    @Size(max = 255, message = "Created by must be at most 255 characters")
    private String createdBy;

    @Size(max = 255, message = "Updated by must be at most 255 characters")
    private String updatedBy;
}
