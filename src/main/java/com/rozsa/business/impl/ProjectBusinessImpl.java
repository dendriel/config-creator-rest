package com.rozsa.business.impl;

import com.rozsa.business.ProjectBusiness;
import com.rozsa.dao.ProjectDao;
import com.rozsa.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectBusinessImpl extends BaseBusinessImpl<Project, ProjectDao> implements ProjectBusiness {
    public ProjectBusinessImpl(ProjectDao dao) {
        super(dao);
    }
}
