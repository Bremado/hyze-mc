package com.toddydev.lava.listener.scoreboard;

import com.toddydev.hyze.bukkit.api.scoreboard.Scoreboard;
import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.lava.Main;
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
        Scoreboard scoreboard = new Scoreboard("§a§lHYZE");
        scoreboard.add(6, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8PLV" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(5, "");
        scoreboard.add(4, "§fCoins: §60");
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
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        if (scoreboard == null)
            return;

        scoreboard.setDisplayName("§a§lHYZE");

        scoreboard.add(6, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8PLV" + Core.getServerInfo().getDisplayName().split("#")[1]);
        scoreboard.add(5, "");
        scoreboard.add(4, "§fCoins: §60");
        scoreboard.add(3, "§fPlayers: §b" + Core.getDataServer().getServerInfo("PROXY").getPlayers());
        scoreboard.add(2, "");
        scoreboard.add(1, "§ehyzemc.com.br");
        TagManager.setTag(player, corePlayer.getGroup().getTag());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        scoreboardMap.remove(event.getPlayer().getUniqueId());
    }
}
