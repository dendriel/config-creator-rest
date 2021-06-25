package com.rozsa.controller;

import com.rozsa.business.TemplateBusiness;
import com.rozsa.controller.dto.BaseDto;
import com.rozsa.controller.mapper.TemplateMapper;
import com.rozsa.model.Template;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateBusiness templateBusiness;
    private final TemplateMapper templateMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto> get(@PathVariable("id") ObjectId id) {
        Template template = templateBusiness.find(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }

        BaseDto dto = templateMapper.toDto(template);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/all")
    public List<BaseDto> getAll(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        List<Template> templates = templateBusiness.findAll(offset, limit);
        return templateMapper.toDtos(templates);
    }

    @GetMapping("/count")
    public long count() {
        return templateBusiness.count();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody BaseDto baseDto) {
        Template template = templateMapper.fromDto(baseDto);
        template = templateBusiness.create(template);

        return ResponseEntity.status(HttpStatus.CREATED).body(template.getRawId());
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody BaseDto baseDto) {
        Template template = templateMapper.fromDto(baseDto);

        if (templateBusiness.update(template) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") ObjectId id) {
        if (templateBusiness.remove(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
