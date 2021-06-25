package com.rozsa.controller;

import com.rozsa.business.ProjectBusiness;
import com.rozsa.controller.dto.BaseDto;
import com.rozsa.controller.mapper.ProjectMapper;
import com.rozsa.model.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController<Project, BaseDto, ProjectBusiness, ProjectMapper> {
    public ProjectController(ProjectBusiness business, ProjectMapper mapper) {
        super(business, mapper);
    }
}
