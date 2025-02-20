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
@Table(name = "sms_template_master")
public class SmsTemplateMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 1028)
    @NotNull
    @Column(name = "body", nullable = false, length = 1028)
    private String body;

    @Size(max = 45)
    @Column(name = "version", length = 45)
    private String version;

    @Size(max = 45)
    @NotNull
    @Column(name = "gov_template_code", nullable = false, length = 45)
    private String govTemplateCode;

    @Size(max = 45)
    @Column(name = "service_provider_template_code", length = 45)
    private String serviceProviderTemplateCode;

    @NotNull
    @Column(name = "sender_id", nullable = false)
    private Integer senderId;

    @Size(max = 5)
    @NotNull
    @Column(name = "is_active", nullable = false, length = 5)
    private String isActive;

    @NotNull
    @Column(name = "sms_master_id", nullable = false)
    private Integer smsMasterId;

    @Size(max = 45)
    @Column(name = "created_by", length = 45)
    private String createdBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 45)
    @Column(name = "updated_by", length = 45)
    private String updatedBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

}