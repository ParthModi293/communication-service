package org.communication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "template_mast")
public class TemplateMast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    @Size(max = 255)
    @NotNull
    @Column(name = "template_name", nullable = false)
    private String templateName;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private String  updatedAt;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Size(max = 10)
    @NotNull
    @Column(name = "priority", nullable = false, length = 10)
    private String priority;

}