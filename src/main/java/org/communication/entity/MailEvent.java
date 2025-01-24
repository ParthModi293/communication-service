package org.communication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mail_event")
public class MailEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_name", length = 255, nullable = false)
    private String eventName;
}
