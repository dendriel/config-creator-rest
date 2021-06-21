package com.rozsa.business;

import com.rozsa.model.Template;
import org.bson.types.ObjectId;

import java.util.List;

public interface TemplateBusiness {
    Template create(Template template);

    Template update(Template template);

    Template find(ObjectId id);

    List<Template> findAll(int offset, int limit);

    boolean remove(ObjectId id);

    long count();
}
