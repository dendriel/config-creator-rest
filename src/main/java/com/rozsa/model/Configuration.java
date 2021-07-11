package com.rozsa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Configuration extends BaseModel {
    private ObjectId id;
    private ObjectId projectId;
    private Date createdAt;
    private Date requestedAt;
    private long createdBy;
    private State state;


    public enum State {
        REQUESTED,
        IN_PROGRESS,
        READY,
        FAILED
    }
}
