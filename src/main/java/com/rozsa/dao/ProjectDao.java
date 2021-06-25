package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(DatabaseConnection db) {
        super(db, Project.class, "project");
    }
}
