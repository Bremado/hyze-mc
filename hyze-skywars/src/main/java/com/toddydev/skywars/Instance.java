package com.toddydev.skywars;

import com.toddydev.hyze.bukkit.commands.loader.BukkitCommandLoader;
import com.toddydev.skywars.listeners.register.Listeners;
import com.toddydev.skywars.platform.Platform;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public class Instance extends JavaPlugin {

    @Getter @Setter
    private static Instance instance;

    @Getter @Setter
    private static boolean started;

    @Override
    public void onLoad() {
        setInstance(this);
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        Listeners.register();
        Platform.getMatches().loadAll();

        BukkitCommandLoader.loadCommands(this, "com.toddydev.skywars");

        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                setStarted(true);
            }
        }, 40);
    }

    @Override
    public void onDisable() {

    }


}
