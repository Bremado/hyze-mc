package com.toddydev.duels.listeners.register;

import com.toddydev.duels.arena.creator.listener.ArenaCreatorListener;
import com.toddydev.duels.arena.creator.menus.SelectSTypeMenu;
import com.toddydev.duels.arena.creator.menus.SelectTypeMenu;
import com.toddydev.duels.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class RegisterListeners {

    public static void makeListeners(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), plugin);
        Bukkit.getPluginManager().registerEvents(new SelectTypeMenu(), plugin);
        Bukkit.getPluginManager().registerEvents(new SelectSTypeMenu(), plugin);
        Bukkit.getPluginManager().registerEvents(new SoupListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new OtherListeners(), plugin);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new UpdateListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new ArenaCreatorListener(), plugin);
    }
}
