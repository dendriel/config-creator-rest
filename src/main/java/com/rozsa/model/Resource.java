package com.rozsa.model;

import lombok.Data;
import org.bson.json.JsonObject;

@Data
public class Resource extends BaseModel {
    private JsonObject data;
}
