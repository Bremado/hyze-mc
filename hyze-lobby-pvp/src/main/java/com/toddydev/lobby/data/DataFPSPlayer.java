package com.toddydev.lobby.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.toddydev.hyze.core.Core;
import com.toddydev.lobby.player.GameFPSPlayer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DataFPSPlayer {

    private MongoCollection<Document> collection = Core.getMongoStorage().getDatabase("fps").getCollection("stats");


    public void create(GameFPSPlayer hyzePlayer) {
        Document found = collection.find(Filters.eq("uniqueId", hyzePlayer.getUniqueId().toString())).first();
        if (found == null) {
            found = Document.parse(Core.getGson().toJson(hyzePlayer));
            collection.insertOne(found);
        }
    }
    public boolean exists(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        return found !=null;
    }

    public GameFPSPlayer getFpsPlayer(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if (found == null) {
            return null;
        }
        return Core.getGson().fromJson(Core.getGson().toJson(found), GameFPSPlayer.class);
    }

    public Collection<GameFPSPlayer> ranking(String fieldName) {
        List<GameFPSPlayer> list = new ArrayList<>();
        for (Document element : collection.find().sort(new Document(fieldName, -1)).limit(10)) {
            list.add(Core.getGson().fromJson(Core.getGson().toJson(element), GameFPSPlayer.class));
        }
        return list;
    }
}
