package com.toddydev.fps.listeners.scoreboard;

import com.toddydev.fps.Main;
import com.toddydev.fps.platform.Platform;
import com.toddydev.fps.player.GamePlayer;
import com.toddydev.hyze.bukkit.api.scoreboard.Scoreboard;
import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

public class ScoreListener implements Listener {

    private static HashMap<UUID, Scoreboard> scoreboardMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    handleScoreboard(event.getPlayer());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, 5L);
    }

    private void handleScoreboard(Player player) {
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        Scoreboard scoreboard = new Scoreboard("§a§lHYZE");
        scoreboard.add(10, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8PFS" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(9, "");
        scoreboard.add(8, "§fKills: §7" + gamePlayer.getKills());
        scoreboard.add(7, "§fDeaths: §7" + gamePlayer.getDeaths());
        scoreboard.add(6, "");
        scoreboard.add(5, "§fKillstreak: §a" + gamePlayer.getKillstreak());
        scoreboard.add(4, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
        scoreboard.add(2, "");
        scoreboard.add(1, "§ehyzemc.com.br");
        player.setScoreboard(scoreboard.getScoreboard());
        Bukkit.getOnlinePlayers().forEach(TagManager::update);
        scoreboardMap.put(player.getUniqueId(), scoreboard);
    }

    @EventHandler
    public void onTimeSecond(UpdateEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        Core.getServerInfo().setPlayers(Main.getInstance().getServer().getOnlinePlayers().size());
        if (scoreboard == null)
            return;

        scoreboard.setDisplayName("§a§lHYZE");

        scoreboard.add(10, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8PFS" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(9, "");
        scoreboard.add(8, "§fKills: §7" + gamePlayer.getKills());
        scoreboard.add(7, "§fDeaths: §7" + gamePlayer.getDeaths());
        scoreboard.add(6, "");
        scoreboard.add(5, "§fKillstreak: §a" + gamePlayer.getKillstreak());
        scoreboard.add(4, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
        scoreboard.add(2, "");
        scoreboard.add(1, "§ehyzemc.com.br");
        TagManager.setTag(player, corePlayer.getGroup().getTag());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        scoreboardMap.remove(event.getPlayer().getUniqueId());
    }
}
