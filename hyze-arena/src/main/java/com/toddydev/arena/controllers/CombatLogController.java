package com.toddydev.arena.controllers;

import com.toddydev.arena.combat.Combat;

import java.util.HashMap;
import java.util.UUID;

public class CombatLogController {

    private HashMap<UUID, Combat> combat = new HashMap<>();

    public void load(Combat c) {
        combat.put(c.getPlayer().getUniqueId(), c);
        combat.put(c.getKiller().getUniqueId(), c);
    }

    public void unload(UUID uniqueId) {
        combat.remove(uniqueId);
    }

    public void replace(Combat c) {
        combat.replace(c.getPlayer().getUniqueId(), c);
        combat.replace(c.getKiller().getUniqueId(), c);
    }

    public boolean exists(UUID uniqueId) {
        return combat.get(uniqueId) != null;
    }

    public Combat getCombat(UUID uniqueId) {
        return combat.get(uniqueId);
    }
}
