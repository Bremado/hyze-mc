package com.toddydev.skywars.controllers;

import com.toddydev.skywars.arena.Arena;
import com.toddydev.skywars.arena.creator.ArenaCreator;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class ArenaCreatorController {

    private HashMap<UUID, ArenaCreator> creator = new HashMap<>();

    public void load(ArenaCreator arenaCreator) {
        creator.put(arenaCreator.getPlayer().getUniqueId(), arenaCreator);
    }

    public void unload(UUID uniqueId) {
        creator.remove(uniqueId);
    }

    public ArenaCreator getArenaCreator(UUID uniqueId) {
        return creator.get(uniqueId);
    }
}
