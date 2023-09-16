package com.toddydev.auth.data.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.toddydev.auth.data.DataAuth;
import com.toddydev.auth.data.info.AuthInfo;
import com.toddydev.hyze.core.Core;
import org.bson.Document;

import java.util.UUID;

public class DataAuthImpl extends DataAuth {

    public MongoCollection<Document> collection = Core.getMongoStorage().getDatabase("auth").getCollection("auths");

    @Override
    public void create(AuthInfo authInfo) {
        Document found = collection.find(Filters.eq("uniqueId", authInfo.getUniqueId().toString())).first();
        if (found == null) {
            found = Document.parse(Core.getGson().toJson(authInfo));
            collection.insertOne(found);
        }
    }

    @Override
    public void save(AuthInfo authInfo) {
        if (!exists(authInfo.getUniqueId())) {
            return;
        }
        collection.updateOne(Filters.eq("uniqueId", authInfo.getUniqueId().toString()),
                new Document("$set", Document.parse(Core.getGson().toJson(authInfo))));
    }

    @Override
    public Boolean exists(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        return found !=null;
    }

    @Override
    public AuthInfo getAuthInfo(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if (found == null) {
            return null;
        }
        return Core.getGson().fromJson(Core.getGson().toJson(found), AuthInfo.class);
    }
}
