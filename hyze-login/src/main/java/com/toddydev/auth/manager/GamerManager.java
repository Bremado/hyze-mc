package com.toddydev.auth.manager;

import com.toddydev.auth.player.Gamer;

import java.util.HashMap;
import java.util.UUID;

public class GamerManager {

    private HashMap<UUID, Gamer> players = new HashMap<>();

    public void load(Gamer gamer) {
        players.put(gamer.getPlayer().getUniqueId(), gamer);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public Gamer getGamer(UUID uuid) {
        return players.get(uuid);
    }
}
