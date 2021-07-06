package com.rozsa.business.impl;

import com.rozsa.business.ResourceBusiness;
import com.rozsa.dao.ResourceDao;
import com.rozsa.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonValue;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class ResourceBusinessImpl extends BaseBusinessImpl<Resource, ResourceDao> implements ResourceBusiness {

    public ResourceBusinessImpl(ResourceDao dao) {
        super(dao);
    }

    @Override
    public List<Resource> findAll(ObjectId projectId, int offset, int limit) {
        return dao.findAllByProjectId(projectId, offset, limit);
    }

    @Override
    public long count(ObjectId projectId) {
        return dao.countByProjectId(projectId);
    }

    @Override
    public void saveValues(List<Resource> resources) {
        resources.forEach(res -> {
            dao.update(res, "data.value", getParsedValue(res));
        });
    }

    public Object getParsedValue(Resource resource) {
        BsonDocument bsonDocument = resource.getData().toBsonDocument();

        String componentType = bsonDocument.get("componentType").asString().getValue();
        BsonValue value = bsonDocument.get("value");

        if (value == null || value instanceof BsonNull) {
            return null;
        }

        try {
        switch(componentType) {
            case "text":
            case "textarea":
                return value.asString();
            case "number":
                return value.asNumber();
            case "toggle":
                return value.asBoolean();
            case "list":
            case "template":
                return value.asArray();
            default:
                log.error("Unknown component type! {}", componentType);
                return null;
        }

        }catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
