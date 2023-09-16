package com.toddydev.arena.controllers;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.ability.kits.*;
import com.toddydev.hyze.bukkit.utils.ClassGetter;
import com.toddydev.hyze.core.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitsController {

    private static HashMap<String, Ability> abilities = new HashMap<>();

    public void loadAll() {
        Ability pvp = new PvP();
        Ability kangaroo = new Kangaroo();
        Ability anchor = new Anchor();
        Ability fisherman = new Fisherman();
        Ability grappler = new Grappler();
        Ability ninja = new Ninja();
        abilities.put(pvp.getName(), pvp);
        abilities.put(kangaroo.getName(), kangaroo);
        abilities.put(anchor.getName(), anchor);
        abilities.put(fisherman.getName(), fisherman);
        abilities.put(grappler.getName(), grappler);
        abilities.put(ninja.getName(), ninja);
    }

    public Ability getKit(String name) {
        for (Ability ability : abilities.values()) {
            if (ability.getName().equalsIgnoreCase(name)) {
                return ability;
            }
        }
        return null;
    }

    public List<Ability> getKits() {
        List<Ability> a = new ArrayList<>();
        for (Ability ability : abilities.values()) {
            a.add(ability);
        }
        return null;
    }

}
