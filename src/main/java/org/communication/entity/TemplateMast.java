package org.communication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "template_mast")
public class TemplateMast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_id", nullable = false)
    private int eventId;

    @Column(name = "template_name", length = 255, nullable = false)
    private String templateName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "priority", length = 45)
    private String priority;
}
