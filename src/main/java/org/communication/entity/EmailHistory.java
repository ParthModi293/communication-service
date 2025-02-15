package org.communication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "to_addresses", nullable = false, length = Integer.MAX_VALUE)
    private String toAddresses;

    @Column(name = "cc_addresses", length = Integer.MAX_VALUE)
    private String ccAddresses;

    @Column(name = "bcc_addresses", length = Integer.MAX_VALUE)
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

    @Column(name = "error_message", length = Integer.MAX_VALUE)
    private String errorMessage;

    @Size(max = 50)
    @Column(name = "version", length = 50)
    private String version;

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "attachments", length = Integer.MAX_VALUE)
    private List<String> attachments;

}