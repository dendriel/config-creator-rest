package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.Template;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateDao extends AbstractDao<Template> {

    public TemplateDao(DatabaseConnection db) {
        super(db, Template.class, "template");
    }
}
