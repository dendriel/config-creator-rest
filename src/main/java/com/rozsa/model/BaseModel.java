package com.rozsa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rozsa.dao.Identifiable;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;


@NoArgsConstructor
public abstract class BaseModel implements Identifiable<ObjectId> {
    private ObjectId id;

    public BaseModel(ObjectId id) {
        this.id = id;
    }

    public void setRawId(String rawId) {
        if (!StringUtils.isEmpty(rawId)) {
            id = new ObjectId(rawId);
        }
    }

    @BsonIgnore
    public String getRawId() {
        return id.toString();
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    @JsonIgnore
    public ObjectId getId() {
        return id;
    }
}
