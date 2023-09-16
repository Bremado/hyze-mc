package com.toddydev.lobby.listeners.scoreboard;

import com.toddydev.hyze.bukkit.api.scoreboard.Scoreboard;
import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.bukkit.platform.Platform;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

public class ScoreListener implements Listener {

    private static HashMap<UUID, Scoreboard> scoreboardMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), new Runnable() {
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
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Properties properties = PropertiesCache.getProperties(corePlayer.getLanguage().getCode() + ".properties");
        Scoreboard scoreboard = new Scoreboard("§a§lHYZE");
        scoreboard.add(10, "§7" +new SimpleDateFormat(properties.getProperty("lang-date")).format(System.currentTimeMillis()) + " §8LB" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(9, "");
        scoreboard.add(8, "§fRank: " + corePlayer.getGroup().getRank().getColor() + corePlayer.getGroup().getRank().getName());
        scoreboard.add(7, "§fCash: §60");
        scoreboard.add(6, "");
        scoreboard.add(5, "§fHub: §a#" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(4, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
        scoreboard.add(2, "");
        scoreboard.add(1, "§ehyzemc.com.br");
        player.setScoreboard(scoreboard.getScoreboard());
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(onlinePlayer.getUniqueId());
            if (!hyzePlayer.getFake().getName().equalsIgnoreCase(player.getName())) {
                Platform.getFakeAPI().changeFake(onlinePlayer, hyzePlayer.getFake());
                TagManager.setTag(onlinePlayer, hyzePlayer.getFake().getTag());
            } else {
                TagManager.setTag(onlinePlayer, hyzePlayer.getGroup().getTag());
            }
        });
        scoreboardMap.put(player.getUniqueId(), scoreboard);
    }

    @EventHandler
    public void onTimeSecond(UpdateEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Core.getServerInfo().setPlayers(Lobby.getInstance().getServer().getOnlinePlayers().size());
        if (scoreboard == null)
            return;

        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§f§lH§a§lYZE");
        scoreboard.setDisplayName("§f§lH§a§lYZE");
        scoreboard.setDisplayName("§f§lH§a§lYZE");
        scoreboard.setDisplayName("§f§lH§a§lYZE");
        scoreboard.setDisplayName("§f§lH§a§lYZE");
        scoreboard.setDisplayName("§f§lH§a§lYZE");
        scoreboard.setDisplayName("§f§lHY§a§lZE");
        scoreboard.setDisplayName("§f§lHY§a§lZE");
        scoreboard.setDisplayName("§f§lHY§a§lZE");
        scoreboard.setDisplayName("§f§lHY§a§lZE");
        scoreboard.setDisplayName("§f§lHY§a§lZE");
        scoreboard.setDisplayName("§f§lHY§a§lZE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZ§a§lE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§f§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");
        scoreboard.setDisplayName("§a§lHYZE");

        Properties properties = PropertiesCache.getProperties(corePlayer.getLanguage().getCode() + ".properties");
        scoreboard.add(10, "§7" +new SimpleDateFormat(properties.getProperty("lang-date")).format(System.currentTimeMillis()) + " §8LB" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(9, "");
        scoreboard.add(8, "§fRank: " + corePlayer.getGroup().getRank().getColor() + corePlayer.getGroup().getRank().getName());
        scoreboard.add(7, "§fCash: §60");
        scoreboard.add(6, "");
        scoreboard.add(5, "§fHub: §a#" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(4, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
        scoreboard.add(2, "");
        scoreboard.add(1, "§ehyzemc.com.br");

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        scoreboardMap.remove(event.getPlayer().getUniqueId());
    }
}
