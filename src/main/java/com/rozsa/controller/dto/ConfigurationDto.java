package com.rozsa.controller.dto;

import com.rozsa.model.Configuration;
import lombok.Data;

import java.util.Date;

@Data
public class ConfigurationDto {
    private String id;
    private String requestedBy;
    private Date requestedAt;
    private Date createdAt;
    private Configuration.State state;
}
