package com.toddydev.skywars.controllers;

import com.toddydev.skywars.arena.Arena;

import java.util.Collection;
import java.util.HashMap;

public class ArenaController {

    private HashMap<String, Arena> arenas = new HashMap<>();

    public void load(Arena arena) {
        arenas.put(arena.getName(), arena);
    }

    public void unload(String name) {
        arenas.remove(name);
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public Collection<Arena> getArenas() {
        return arenas.values();
    }
}
