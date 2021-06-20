package com.rozsa.controller;

import com.rozsa.dto.ListTemplateDto;
import com.rozsa.dto.TemplateDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/{id}")
    public TemplateDto get(@PathVariable("id") int id) {
        TemplateDto template = new TemplateDto();
        template.setData(id + "");

        return template;
    }

    @GetMapping("/all")
    public ListTemplateDto getAll() {
        ListTemplateDto templates = new ListTemplateDto();

        return templates;
    }

    @PostMapping
    public void create(TemplateDto templateDto) {
        // save.
        System.out.println("Create template");
    }

    @PutMapping
    public void update(TemplateDto templateDto) {
        // update.
        System.out.println("Update template");
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") int id) {
        System.out.println("Remove template " + id);
    }
}
