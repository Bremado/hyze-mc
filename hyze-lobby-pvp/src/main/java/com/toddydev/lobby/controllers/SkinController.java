package com.toddydev.lobby.controllers;

import com.toddydev.lobby.player.GameFPSPlayer;
import net.jitse.npclib.api.skin.Skin;

import java.util.HashMap;
import java.util.UUID;

public class SkinController {

    private HashMap<UUID, Skin> players = new HashMap<>();

    public void load(UUID uniqueId, Skin skin) {
        players.put(uniqueId, skin);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public Skin getSkin(UUID uniqueId) {
        return players.get(uniqueId);
    }
}
