package com.toddydev.skywars;

import com.toddydev.hyze.core.Core;
import com.toddydev.skywars.arena.Arena;
import com.toddydev.skywars.arena.type.ArenaSubType;
import com.toddydev.skywars.arena.type.ArenaType;
import com.toddydev.skywars.platform.Platform;
import org.bukkit.Location;

import java.awt.geom.Area;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Matches {

    public void loadAll() {
        for (String name : Instance.getInstance().getConfig().getConfigurationSection("arenas").getKeys(false)) {
            Arena a = new Arena(name, Platform.getSerializer().deserialize(
                    Instance.getInstance().getConfig().getString("arenas." + name + ".lobby")
            ));

            a.setType(ArenaType.getByName(Instance.getInstance().getConfig().getString("arenas." + name + ".type")));
            a.setSubType(ArenaSubType.getByName(Instance.getInstance().getConfig().getString("arenas." + name + "subtype")));

            for (String s : Instance.getInstance().getConfig().getStringList("arenas." + name + ".spawns")) {
                a.addSpawn(Platform.getSerializer().deserialize(s));
            }

            a.setMaxPlayers(Instance.getInstance().getConfig().getInt("arenas." + name + ".max"));
            a.setMinPlayers(Instance.getInstance().getConfig().getInt("arenas." + name + ".min"));

            a.start();
            Platform.getArenaController().load(a);
            Core.getLogger().info("Loaded arena " + name + "!");
        }
    }
    
    public void create(Arena arena) {
        List<String> spawns = new ArrayList<>();
        for (Location loc : arena.getSpawns()) {
            spawns.add(Platform.getSerializer().serialize(loc));
        }
        Instance.getInstance().getConfig().set("arenas." + arena.getName() + ".lobby", Platform.getSerializer().serialize(arena.getLobby()));
        Instance.getInstance().getConfig().set("arenas." + arena.getName() + ".type", arena.getType().name());
        Instance.getInstance().getConfig().set("arenas." + arena.getName() + ".subtype", arena.getSubType().name());
        Instance.getInstance().getConfig().set("arenas." + arena.getName() + ".spawns", spawns);
        Instance.getInstance().getConfig().set("arenas." + arena.getName() + ".max", arena.getMaxPlayers());
        Instance.getInstance().getConfig().set("arenas." + arena.getName() + ".min", arena.getMinPlayers());
        Instance.getInstance().saveConfig();
        Platform.getArenaController().load(arena);
    }

    public void delete(String name) {
        Instance.getInstance().getConfig().set("arenas." + name, null);
        Instance.getInstance().saveConfig();
    }
}
