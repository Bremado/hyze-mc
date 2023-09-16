package com.toddydev.fps.controllers;

import com.toddydev.fps.player.GamePlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerController {

    private HashMap<UUID, GamePlayer> players = new HashMap<>();

    public void load(GamePlayer gamePlayer) {
        players.put(gamePlayer.getUniqueId(), gamePlayer);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public GamePlayer getGamePlayer(UUID uniqueId) {
        return players.get(uniqueId);
    }
}
