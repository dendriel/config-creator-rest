package com.rozsa.business.impl;

import com.rozsa.business.ConfigurationBusiness;
import com.rozsa.dao.ConfigurationDao;
import com.rozsa.model.Configuration;
import org.springframework.stereotype.Component;


@Component
public class ConfigurationBusinessImpl extends ProjectDependentBusinessImpl<Configuration, ConfigurationDao> implements ConfigurationBusiness {
    public ConfigurationBusinessImpl(ConfigurationDao dao) {
        super(dao);
    }
}
