package com.rozsa.controller;

import com.rozsa.business.ResourceBusiness;
import com.rozsa.controller.dto.BaseDto;
import com.rozsa.controller.mapper.ResourceMapper;
import com.rozsa.model.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<Resource, BaseDto, ResourceBusiness, ResourceMapper> {
    public ResourceController(ResourceBusiness business, ResourceMapper mapper) {
        super(business, mapper);
    }

    @GetMapping("/all")
    public List<BaseDto> getAll(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        System.out.println("HERE");
        List<Resource> templates = business.findAll(offset, limit);
        return mapper.toDtos(templates);
    }
}
