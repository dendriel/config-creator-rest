package com.rozsa.controller;

import com.rozsa.business.ConfigurationBusiness;
import com.rozsa.controller.dto.ConfigurationDto;
import com.rozsa.controller.mapper.ConfigurationMapper;
import com.rozsa.model.Configuration;
import com.rozsa.security.UserContext;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private final ConfigurationMapper mapper;

    private final ConfigurationBusiness business;

    @GetMapping("/all")
    public List<ConfigurationDto> getAll(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        ObjectId projectId = UserContext.getDefaultProjectId();
        List<Configuration> templates = business.findAll(projectId, offset, limit);
        return mapper.toDtos(templates);
    }

    @GetMapping("/count")
    public long count() {
        ObjectId projectId = UserContext.getDefaultProjectId();
        return business.count(projectId);
    }
}
