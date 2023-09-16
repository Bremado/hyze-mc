package com.toddydev.lobby;

import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.hologram.HologramAPI;
import com.toddydev.hyze.bukkit.hologram.HologramListener;
import com.toddydev.hyze.bukkit.hologram.HologramManager;
import com.toddydev.lobby.commands.BuildCommand;
import com.toddydev.lobby.commands.SetarCommand;
import com.toddydev.lobby.hologram.RankingHologram;
import com.toddydev.lobby.listeners.Listeners;
import com.toddydev.lobby.listeners.chat.ChatListener;
import com.toddydev.lobby.listeners.scoreboard.ScoreListener;
import com.toddydev.lobby.menus.StatsMenu;
import com.toddydev.lobby.npc.NPCManager;
import com.toddydev.lobby.platform.Platform;
import com.toddydev.lobby.utils.Utils;
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

    @Getter
    private static List<RankingHologram> ranks = new ArrayList<>();


    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        ranks();
        World world = Bukkit.getWorld("world");
        world.setAutoSave(false);

        world.setGameRuleValue("oDaylightCycle", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("randomTickSpeed", "0");

        WorldBorder border = world.getWorldBorder();
        border.setCenter(world.getSpawnLocation());
        border.setSize(250);
        getCommand("setar").setExecutor(new SetarCommand());
        getCommand("build").setExecutor(new BuildCommand());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreListener(), this);
        Bukkit.getPluginManager().registerEvents(new NPCManager(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new HologramListener(), this);
        Bukkit.getPluginManager().registerEvents(new StatsMenu(), this);
    }

    public void ranks() {
        RankingHologram topRanking = new RankingHologram(Utils.deserializeLocation(getConfig().getString("config.locations.ranking.fps.kills")), "KILLS", "kills");
        BukkitCore.getInstance().getHologramManager().getGlobalHolograms().add(topRanking);
        HologramAPI.spawnGlobal(topRanking);

        RankingHologram topKilLStreak = new RankingHologram(Utils.deserializeLocation(getConfig().getString("config.locations.ranking.fps.killstreak")), "KILLSTREAK", "killstreak");
        BukkitCore.getInstance().getHologramManager().getGlobalHolograms().add(topKilLStreak);
        HologramAPI.spawnGlobal(topKilLStreak);

        try {
            topRanking.update();
            topKilLStreak.update();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            try {
                topRanking.update();
                topKilLStreak.update();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }, 0L, 6000L);

        ranks.add(topRanking);
        ranks.add(topKilLStreak);
    }

    @Override
    public void onDisable() {

    }
}
