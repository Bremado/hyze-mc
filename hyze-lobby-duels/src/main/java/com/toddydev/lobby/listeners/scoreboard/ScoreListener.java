package com.toddydev.lobby.listeners.scoreboard;

import com.toddydev.hyze.bukkit.api.scoreboard.Scoreboard;
import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.stats.StatsPlayer;
import com.toddydev.hyze.core.server.type.ServerType;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.npc.NPCManager;
import com.toddydev.lobby.platform.Platform;
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
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        StatsPlayer statsPlayer = Platform.getStatsController().getGamePlayer(player.getUniqueId());
        Scoreboard scoreboard = new Scoreboard("§a§lHYZE");
        int players = NPCManager.getPlayers(ServerType.ROOM_GLADIATOR) + NPCManager.getPlayers(ServerType.ROOM_SIMULATOR) + NPCManager.getPlayers(ServerType.ROOM_UHC) + NPCManager.getPlayers(ServerType.ROOM_SOUP);
        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        scoreboard.add(14, "§7" +new SimpleDateFormat(properties.getProperty("lang-date")).format(System.currentTimeMillis()) + " §8LB" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(13, "");
        scoreboard.add(12, "§f"+ properties.getProperty("scoreboard-playing") + players);
        scoreboard.add(11, "");
        scoreboard.add(10, "§eGladiador:");
        scoreboard.add(9, " §fKills:§b " + statsPlayer.getGladiator().getKills());
        scoreboard.add(8, " §fWinstreak:§b " + statsPlayer.getGladiator().getWinstreak());
        scoreboard.add(7, "§eSimulator:");
        scoreboard.add(6, " §fKills:§b " + statsPlayer.getSimulator().getKills());
        scoreboard.add(5, " §fWinstreak:§b " + statsPlayer.getSimulator().getWinstreak());
        scoreboard.add(4, "");
        scoreboard.add(3, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
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
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        StatsPlayer statsPlayer = Platform.getStatsController().getGamePlayer(player.getUniqueId());
        Core.getServerInfo().setPlayers(Lobby.getInstance().getServer().getOnlinePlayers().size());
        if (scoreboard == null)
            return;

        int players = NPCManager.getPlayers(ServerType.ROOM_GLADIATOR) + NPCManager.getPlayers(ServerType.ROOM_SIMULATOR) + NPCManager.getPlayers(ServerType.ROOM_UHC) + NPCManager.getPlayers(ServerType.ROOM_SOUP);

        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        scoreboard.add(14, "§7" +new SimpleDateFormat(properties.getProperty("lang-date")).format(System.currentTimeMillis()) + " §8LB" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(13, "");
        scoreboard.add(12, "§f"+ properties.getProperty("scoreboard-playing") + players);
        scoreboard.add(11, "");
        scoreboard.add(10, "§eGladiador:");
        scoreboard.add(9, " §fKills:§b " + statsPlayer.getGladiator().getKills());
        scoreboard.add(8, " §fWinstreak:§b " + statsPlayer.getGladiator().getWinstreak());
        scoreboard.add(7, "§eSimulator:");
        scoreboard.add(6, " §fKills:§b " + statsPlayer.getSimulator().getKills());
        scoreboard.add(5, " §fWinstreak:§b " + statsPlayer.getSimulator().getWinstreak());
        scoreboard.add(4, "");
        scoreboard.add(3, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
        scoreboard.add(2, "");
        scoreboard.add(1, "§ehyzemc.com.br");

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        scoreboardMap.remove(event.getPlayer().getUniqueId());
    }
}
