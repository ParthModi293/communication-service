package com.clapcle.communication.model;

import lombok.Data;

import java.util.List;

@Data
public class SMSBean {
    private int smsMasterId;
    private List<Recipients> recipients;
}
