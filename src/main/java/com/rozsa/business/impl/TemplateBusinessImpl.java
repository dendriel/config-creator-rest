package com.rozsa.business.impl;

import com.rozsa.business.TemplateBusiness;
import com.rozsa.dao.TemplateDao;
import com.rozsa.model.Template;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class TemplateBusinessImpl implements TemplateBusiness {

    private final TemplateDao templateDao;

    @Override
    public Template create(Template template) {
        return templateDao.create(template);
    }

    @Override
    public Template find(ObjectId id) {
        return templateDao.findById(id);
    }

    @Override
    public List<Template> findAll(int offset, int limit) {
        return templateDao.findAll(offset, limit);
    }

    @Override
    public boolean remove(ObjectId id) {
        return templateDao.deleteById(id);
    }

    @Override
    public Template update(Template template) {
        return templateDao.update(template);
    }

    @Override
    public long count() {
        return templateDao.count();
    }
}
