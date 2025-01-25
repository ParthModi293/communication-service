package org.communication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "template_details")
public class TemplateDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @Size(max = 1028)
    @NotNull
    @Column(name = "body", nullable = false, length = 1028)
    private String body;

    @NotNull
    @Column(name = "template_mast_id", nullable = false)
    private Integer templateMastId;

    @NotNull
    @Column(name = "version", nullable = false)
    private Double version;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @NotNull
    @Column(name = "is_active", nullable = false, length = Integer.MAX_VALUE)
    private String isActive;

    @Size(max = 255)
    @NotNull
    @Column(name = "from_email_id", nullable = false)
    private String fromEmailId;


    public TemplateDetail(String subject, String body, int templateMastId, Double version, LocalDateTime createdAt, LocalDateTime updatedAt, String isActive, String fromEmailId) {
        this.subject = subject;
        this.body = body;
        this.templateMastId = templateMastId;
        this.version = version;
        this.createdAt = createdAt.toString();
        this.updatedAt = updatedAt.toString();
        this.isActive = isActive;
        this.fromEmailId = fromEmailId;
    }
}