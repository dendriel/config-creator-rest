package com.rozsa.controller;

import com.rozsa.business.ProjectBusiness;
import com.rozsa.controller.dto.BaseDto;
import com.rozsa.controller.mapper.ProjectMapper;
import com.rozsa.model.Project;
import com.rozsa.security.UserContext;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController<Project, BaseDto, ProjectBusiness, ProjectMapper> {
    public ProjectController(ProjectBusiness business, ProjectMapper mapper) {
        super(business, mapper);
    }

    @GetMapping
    public ResponseEntity<BaseDto> get() {
        ObjectId projectId = UserContext.getDefaultProjectId();
        Project data = business.find(projectId);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        BaseDto dto = mapper.toDto(data);
        return ResponseEntity.ok().body(dto);
    }
}
