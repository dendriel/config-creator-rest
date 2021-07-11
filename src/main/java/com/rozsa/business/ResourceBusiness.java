package com.rozsa.business;

import com.rozsa.model.Resource;

import java.util.List;

public interface ResourceBusiness extends ProjectDependentBusiness<Resource> {
    void saveValues(List<Resource> resources);
}
