package com.toddydev.skywars.listeners.register;

import com.toddydev.skywars.Instance;
import com.toddydev.skywars.arena.creator.listener.ArenaCreatorListener;
import com.toddydev.skywars.listeners.PlayerListeners;
import com.toddydev.skywars.menus.SelectModeMenu;
import org.bukkit.Bukkit;

public class Listeners {

    public static void register() {
        Bukkit.getPluginManager().registerEvents(new SelectModeMenu(), Instance.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), Instance.getInstance());
        Bukkit.getPluginManager().registerEvents(new ArenaCreatorListener(), Instance.getInstance());
    }
}
