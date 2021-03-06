package com.rozsa.controller.dto;

import com.rozsa.model.Configuration;
import lombok.Data;

import java.util.Date;

@Data
public class ConfigurationDto {
    private String id;
    private String projectId;
    private String requestedBy;
    private Long resourceId;
    private Date requestedAt;
    private Date createdAt;
    private Configuration.State state;
}
