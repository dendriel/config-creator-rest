package com.rozsa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

@NoArgsConstructor
public class User extends BaseModel {
    private long userId;
    private ObjectId defaultProjectId;

    public User(long userId) {
        this.userId = userId;
    }

    public User(ObjectId id) {
        super(id);
    }

    public long getUserId() {
        return userId;
    }

    public void setRawDefaultProjectId(String rawId) {
        if (!StringUtils.isEmpty(rawId)) {
            defaultProjectId = new ObjectId(rawId);
        }
    }

    @BsonIgnore
    public String getRawDefaultProjectId() {
        return defaultProjectId.toString();
    }
    public void setDefaultProjectId(ObjectId id) {
        this.defaultProjectId = id;
    }

    @JsonIgnore
    public ObjectId getDefaultProjectId() {
        return defaultProjectId;
    }
}
