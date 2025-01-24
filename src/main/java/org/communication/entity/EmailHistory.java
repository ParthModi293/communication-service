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
@Table(name = "email_history")
public class EmailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "from_address", nullable = false)
    private String fromAddress;

    @NotNull
    @Lob
    @Column(name = "to_addresses", nullable = false)
    private String toAddresses;

    @Lob
    @Column(name = "cc_addresses")
    private String ccAddresses;

    @Lob
    @Column(name = "bcc_addresses")
    private String bccAddresses;

    @Size(max = 255)
    @Column(name = "subject")
    private String subject;

    @Size(max = 1028)
    @Column(name = "body", length = 1028)
    private String body;

    @Size(max = 50)
    @NotNull
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Lob
    @Column(name = "error_message")
    private String errorMessage;

    @Size(max = 50)
    @Column(name = "version", length = 50)
    private String version;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Lob
    @Column(name = "attachments")
    private String attachments;

}