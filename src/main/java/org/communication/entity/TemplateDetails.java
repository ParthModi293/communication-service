package org.communication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "template_details")
public class TemplateDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", length = 1028)
    private String body;

    @Column(name = "template_mast_id")
    private int templateMastId;

    @Column(name = "version")
    private Double version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "from_email_id")
    private String fromEmailId;

}
