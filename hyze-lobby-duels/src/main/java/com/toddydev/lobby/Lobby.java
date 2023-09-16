package com.toddydev.lobby;

import com.toddydev.hyze.bukkit.commands.loader.BukkitCommandLoader;
import com.toddydev.hyze.bukkit.hologram.HologramListener;
import com.toddydev.lobby.commands.BuildCommand;
import com.toddydev.lobby.commands.SetarCommand;
import com.toddydev.lobby.listeners.InviteListener;
import com.toddydev.lobby.listeners.Listeners;
import com.toddydev.lobby.listeners.chat.ChatListener;
import com.toddydev.lobby.listeners.scoreboard.ScoreListener;
import com.toddydev.lobby.menus.InviteMenu;
import com.toddydev.lobby.menus.StatsMenu;
import com.toddydev.lobby.npc.NPCManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Lobby extends JavaPlugin {

    @Getter
    private static Lobby instance;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        World world = Bukkit.getWorld("world");
        world.setAutoSave(false);

        world.setGameRuleValue("oDaylightCycle", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("randomTickSpeed", "0");

        WorldBorder border = world.getWorldBorder();
        border.setCenter(world.getSpawnLocation());
        border.setSize(250);
        BukkitCommandLoader.loadCommands(this, "com.toddydev.lobby");
        getCommand("setar").setExecutor(new SetarCommand());
        getCommand("build").setExecutor(new BuildCommand());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new InviteListener(), this);
        Bukkit.getPluginManager().registerEvents(new InviteMenu(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreListener(), this);
        Bukkit.getPluginManager().registerEvents(new NPCManager(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new HologramListener(), this);
        Bukkit.getPluginManager().registerEvents(new StatsMenu(), this);
    }

    @Override
    public void onDisable() {

    }
}
