package com.clapcle.communication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sms_sender_master",schema = "public", catalog = "public")
public class SmsSenderMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "sender_code", nullable = false, length = 45)
    private String senderCode;

    @NotNull
    @Column(name = "service_provider_id", nullable = false)
    private Integer serviceProviderId;

    @Size(max = 45)
    @NotNull
    @Column(name = "is_active", nullable = false, length = 45)
    private String isActive;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;

}