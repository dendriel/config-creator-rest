package com.rozsa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rozsa.dao.Identifiable;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


@NoArgsConstructor
public abstract class BaseModel implements Identifiable<ObjectId> {
    private ObjectId id;

    public BaseModel(ObjectId id) {
        this.id = id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @JsonIgnore
    public ObjectId getId() {
        return id;
    }
}
