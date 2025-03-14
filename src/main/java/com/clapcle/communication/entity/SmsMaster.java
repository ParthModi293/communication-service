package com.clapcle.communication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sms_master", schema = "public", catalog = "public")
public class SmsMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "template_name", nullable = false, length = 45)
    private String templateName;

    @NotNull
    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;

    @Size(max = 10)
    @NotNull
    @Column(name = "priority", nullable = false, length = 10)
    private String priority;

}