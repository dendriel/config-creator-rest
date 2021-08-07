package com.rozsa.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Configuration extends BaseModel {
    private ObjectId id;
    private ObjectId projectId;
    private ObjectId requestedBy;
    private Long resourceId;
    private Date requestedAt;
    private Date createdAt;
    private State state;


    public enum State {
        REQUESTED,
        IN_PROGRESS,
        READY,
        FAILED
    }
}
