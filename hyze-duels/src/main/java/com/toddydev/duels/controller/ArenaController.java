package com.toddydev.duels.controller;

import com.toddydev.duels.arena.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public List<Arena> getArenas() {
        List<Arena> a = new ArrayList<>();
        for (Arena arena : arenas.values()) {
            a.add(arena);
        }
        return a;
    }

}
