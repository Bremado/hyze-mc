package com.toddydev.lava;

import com.toddydev.hyze.bukkit.commands.loader.BukkitCommandLoader;
import com.toddydev.lava.listener.DamageListener;
import com.toddydev.lava.listener.OtherListeners;
import com.toddydev.lava.listener.PlayerListeners;
import com.toddydev.lava.listener.SoupListener;
import com.toddydev.lava.listener.scoreboard.ScoreListener;
import com.toddydev.lava.menus.ConfigMenu;
import com.toddydev.lava.recipe.Recipes;
import com.toddydev.lava.utils.LocationUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    @Setter
    private static Main instance;

    @Getter @Setter
    private LocationUtils location;

    @Override
    public void onLoad() {
        setInstance(this);
    }

    @Override
    public void onEnable() {
        Recipes.loadRecipes();
        setLocation(new LocationUtils());
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new SoupListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new OtherListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConfigMenu(), this);
        BukkitCommandLoader.loadCommands(this, "com.toddydev.lava");
        World world = Bukkit.getWorld("world");
        world.setAutoSave(false);

        world.setGameRuleValue("oDaylightCycle", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("randomTickSpeed", "0");

        location.setLobby(location.deserialize(getConfig().getString("config.locations.spawn")));
        WorldBorder border = world.getWorldBorder();
        border.setCenter(location.getLobby());
        border.setSize(500);
        world.getEntities().forEach(Entity::remove);

        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                world.getEntities().forEach(entity -> {
                    if (entity instanceof Item) {
                        entity.remove();
                    }
                });
            }
        }, 80, 80);
    }

    @Override
    public void onDisable() {

    }

}
