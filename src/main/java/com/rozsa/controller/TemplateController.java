package com.rozsa.controller;

import com.rozsa.business.TemplateBusiness;
import com.rozsa.controller.dto.BaseDto;
import com.rozsa.controller.mapper.TemplateMapper;
import com.rozsa.model.Template;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController<Template, BaseDto, TemplateBusiness, TemplateMapper> {
    public TemplateController(TemplateBusiness business, TemplateMapper mapper) {
        super(business, mapper);
    }
}
