package com.toddydev.duels.controller;

import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.creator.ArenaCreator;

import java.util.HashMap;
import java.util.UUID;

public class ArenaCreatorController {

    private HashMap<UUID, ArenaCreator> arenas = new HashMap<>();

    public void load(ArenaCreator arena) {
        arenas.put(arena.getPlayer().getUniqueId(), arena);
    }

    public void unload(UUID name) {
        arenas.remove(name);
    }

    public ArenaCreator getArenaCreator(UUID name) {
        return arenas.get(name);
    }
}
