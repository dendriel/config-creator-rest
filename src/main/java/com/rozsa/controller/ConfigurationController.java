package com.rozsa.controller;

import com.rozsa.business.ConfigurationBusiness;
import com.rozsa.controller.dto.ConfigurationDto;
import com.rozsa.controller.mapper.ConfigurationMapper;
import com.rozsa.model.Configuration;
import com.rozsa.security.UserContext;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private final ConfigurationMapper mapper;

    private final ConfigurationBusiness business;

    // TODO: internal usage only by exporter-service
    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationDto> get(@PathVariable("id") ObjectId id) {
        Configuration data = business.find(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        ConfigurationDto dto = mapper.toDto(data);
        return ResponseEntity.ok().body(dto);
    }

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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/export")
    public void export() {
        ObjectId projectId = UserContext.getDefaultProjectId();
        ObjectId id = UserContext.getId();

        business.export(projectId, id);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody ConfigurationDto dto) {
        Configuration configuration = mapper.fromDto(dto);

        Configuration saved = business.update(configuration);
        if (saved == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") ObjectId id) {
        if (business.remove(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
