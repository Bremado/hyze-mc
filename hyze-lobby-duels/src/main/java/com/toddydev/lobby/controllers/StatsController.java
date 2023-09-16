package com.toddydev.lobby.controllers;

import com.toddydev.hyze.core.player.stats.StatsPlayer;

import java.util.HashMap;
import java.util.UUID;

public class StatsController {

    private HashMap<UUID, StatsPlayer> players = new HashMap<>();

    public void load(StatsPlayer gamePlayer) {
        players.put(gamePlayer.getUniqueId(), gamePlayer);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public StatsPlayer getGamePlayer(UUID uniqueId) {
        return players.get(uniqueId);
    }
}
