package com.toddydev.duels.controller;

import com.toddydev.duels.gamer.GamePlayer;

import java.util.HashMap;
import java.util.UUID;

public class GameController {

    private HashMap<UUID, GamePlayer> players = new HashMap<>();

    public void load(GamePlayer gamePlayer) {
        players.put(gamePlayer.getUniqueId(), gamePlayer);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public GamePlayer getGamerPlayer(UUID uniqueId) {
        return players.get(uniqueId);
    }
}
