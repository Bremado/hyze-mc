package com.toddydev.skywars.controllers;

import com.toddydev.skywars.player.GamePlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerController {

    private HashMap<UUID, GamePlayer> players = new HashMap<>();

    public void load(GamePlayer player) {
        players.put(player.getUniqueId(), player);
    }

    public void unload(UUID uuid) {
        players.remove(uuid);
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        return players.get(uuid);
    }
}
