package com.rozsa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.json.JsonObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class Resource extends BaseModel {
    private JsonObject data;
}
