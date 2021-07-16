package com.rozsa.controller;

import com.rozsa.business.ResourceBusiness;
import com.rozsa.controller.dto.BaseDto;
import com.rozsa.controller.mapper.ResourceMapper;
import com.rozsa.model.Resource;
import com.rozsa.security.UserContext;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<Resource, BaseDto, ResourceBusiness, ResourceMapper> {
    public ResourceController(ResourceBusiness business, ResourceMapper mapper) {
        super(business, mapper);
    }

    @Override
    @GetMapping("/all")
    public List<BaseDto> getAll(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        ObjectId projectId = UserContext.getDefaultProjectId();
        List<Resource> templates = business.findAll(projectId, offset, limit);
        return mapper.toDtos(templates);
    }

    @Override
    @GetMapping("/count")
    public long count() {
        ObjectId projectId = UserContext.getDefaultProjectId();
        return business.count(projectId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/value")
    public void saveValues(@RequestBody List<BaseDto> resourcesDtos) {
        List<Resource> resources = mapper.fromDtos(resourcesDtos);
        business.saveValues(resources);
    }

    // TODO: add authorization
    @GetMapping("/all/{projectId}")
    public List<BaseDto> getAllByProjectId(@PathVariable("projectId") ObjectId projectId) {
        List<Resource> templates = business.findAll(projectId);
        return mapper.toDtos(templates);
    }
}
