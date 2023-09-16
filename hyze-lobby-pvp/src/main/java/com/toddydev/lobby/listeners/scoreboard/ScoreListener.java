package com.toddydev.lobby.listeners.scoreboard;

import com.toddydev.hyze.bukkit.api.scoreboard.Scoreboard;
import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.server.type.ServerType;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.npc.NPCManager;
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
        Scoreboard scoreboard = new Scoreboard("§a§lHYZE");
        int players = NPCManager.getPlayers(ServerType.ROOM_ARENA) + NPCManager.getPlayers(ServerType.ROOM_FPS) + NPCManager.getPlayers(ServerType.ROOM_LAVA);
        scoreboard.add(13, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8LBP" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(12, "");
        scoreboard.add(11, "§7Bem-vindo ao PvP");
        scoreboard.add(10, "§7Selecione um jogo!");
        scoreboard.add(9, "");
        scoreboard.add(8, "§fCoins: §60");
        scoreboard.add(7, "§fEm Jogo: §a" + players);
        scoreboard.add(6, "");
        scoreboard.add(5, "§fHub: §a#" + Core.getServerInfo().getDisplayName().split("#")[1]);
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

        int players = NPCManager.getPlayers(ServerType.ROOM_ARENA) + NPCManager.getPlayers(ServerType.ROOM_FPS) + NPCManager.getPlayers(ServerType.ROOM_LAVA);
        scoreboard.add(13, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8LBP" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(12, "");
        scoreboard.add(11, "§7Bem-vindo ao PvP");
        scoreboard.add(10, "§7Selecione um jogo!");
        scoreboard.add(9, "");
        scoreboard.add(8, "§fCoins: §60");
        scoreboard.add(7, "§fEm Jogo: §a" + players);
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
