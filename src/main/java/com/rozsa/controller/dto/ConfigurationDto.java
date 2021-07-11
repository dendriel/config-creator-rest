package com.rozsa.controller.dto;

import com.rozsa.model.Configuration;
import lombok.Data;

import java.util.Date;

@Data
public class ConfigurationDto {
    private String id;
    private Date createdAt;
    private Date requestedAt;
    private long createdBy;
    private Configuration.State state;
}
