package com.rozsa.service.storage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "storage", url = "${service.url}", configuration = StorageFeignClientConfiguration.class)
public interface StorageService {
    @RequestMapping(method = RequestMethod.DELETE, value = "/storage/resource/{id}")
    void deleteResource(@PathVariable Long id);
}
