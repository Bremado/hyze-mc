package com.toddydev.lobby.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.stats.StatsPlayer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DataPlayer {

    private MongoCollection<Document> collection = Core.getMongoStorage().getDatabase("duels").getCollection("stats");

    public boolean exists(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        return found !=null;
    }

    public void create(StatsPlayer hyzePlayer) {
        Document found = collection.find(Filters.eq("uniqueId", hyzePlayer.getUniqueId().toString())).first();
        if (found == null) {
            found = Document.parse(Core.getGson().toJson(hyzePlayer));
            collection.insertOne(found);
        }
    }

    public void save(StatsPlayer hyzePlayer) {
        if (!exists(hyzePlayer.getUniqueId())) {
            return;
        }
        collection.updateOne(Filters.eq("uniqueId", hyzePlayer.getUniqueId().toString()),
                new Document("$set", Document.parse(Core.getGson().toJson(hyzePlayer))));
    }

    public StatsPlayer getHyzePlayer(UUID uniqueId) {
        Document found = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if (found == null) {
            return null;
        }
        return Core.getGson().fromJson(Core.getGson().toJson(found), StatsPlayer.class);
    }

    public Collection<StatsPlayer> ranking(String fieldName) {
        List<StatsPlayer> list = new ArrayList<>();
        for (Document element : collection.find().sort(new Document(fieldName, -1)).limit(10)) {
            list.add(Core.getGson().fromJson(Core.getGson().toJson(element), StatsPlayer.class));
        }
        return list;
    }

}
