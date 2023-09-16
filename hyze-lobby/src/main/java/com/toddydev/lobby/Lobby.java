package com.toddydev.lobby;

import com.toddydev.lobby.commands.BuildCommand;
import com.toddydev.lobby.commands.SetarCommand;
import com.toddydev.lobby.listeners.Listeners;
import com.toddydev.lobby.listeners.chat.ChatListener;
import com.toddydev.lobby.listeners.scoreboard.ScoreListener;
import com.toddydev.lobby.npc.NPCManager;
import com.toddydev.lobby.npc.listener.NPCListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    @Getter
    private static Lobby instance;

    @Getter @Setter
    private static NPCManager npcManager;

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
        setNpcManager(new NPCManager());
        getCommand("setar").setExecutor(new SetarCommand());
        getCommand("build").setExecutor(new BuildCommand());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreListener(), this);
        Bukkit.getPluginManager().registerEvents(new NPCListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
