package com.toddydev.lobby.controllers;

import com.toddydev.lobby.player.GameFPSPlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerController {

    private HashMap<UUID, GameFPSPlayer> players = new HashMap<>();

    public void load(GameFPSPlayer gamePlayer) {
        players.put(gamePlayer.getUniqueId(), gamePlayer);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public GameFPSPlayer getGamePlayer(UUID uniqueId) {
        return players.get(uniqueId);
    }
}
