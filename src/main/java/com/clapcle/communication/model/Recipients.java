package com.clapcle.communication.model;

import lombok.Data;

import java.util.Map;

@Data
public class Recipients {
    private String mobile;
    private Map<String, Object> placeHolders;
}
