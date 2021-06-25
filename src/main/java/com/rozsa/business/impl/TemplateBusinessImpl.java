package com.rozsa.business.impl;

import com.rozsa.business.TemplateBusiness;
import com.rozsa.dao.TemplateDao;
import com.rozsa.model.Template;
import org.springframework.stereotype.Component;

@Component
public class TemplateBusinessImpl extends BaseBusinessImpl<Template, TemplateDao> implements TemplateBusiness {
    public TemplateBusinessImpl(TemplateDao dao) {
        super(dao);
    }
}
