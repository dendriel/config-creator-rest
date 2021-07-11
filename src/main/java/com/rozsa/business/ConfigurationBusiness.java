package com.rozsa.business;

import com.rozsa.model.Configuration;
import org.bson.types.ObjectId;

public interface ConfigurationBusiness extends ProjectDependentBusiness<Configuration> {

    void export(ObjectId projectId, ObjectId requesterId);
}
