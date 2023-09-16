package com.toddydev.fps.listeners;

import com.toddydev.fps.Main;
import com.toddydev.fps.platform.Platform;
import com.toddydev.fps.player.GamePlayer;
import com.toddydev.hyze.bukkit.api.actionbar.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.github.paperspigot.Title;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        player.teleport(Main.getInstance().getLocation().getLobby());
        player.getInventory().clear();
        if (player.getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            killer.hidePlayer(player);
            GamePlayer killerPlayer = Platform.getPlayerController().getGamePlayer(killer.getUniqueId());
            killerPlayer.setKills(killerPlayer.getKills().intValue() + 1);
            killerPlayer.setKillstreak(killerPlayer.getKillstreak().intValue() + 1);
            if (killerPlayer.getKillstreak() == 10 || killerPlayer.getKillstreak() == 25 || killerPlayer.getKillstreak() == 50 || killerPlayer.getKillstreak() == 75 || killerPlayer.getKillstreak() == 100 || killerPlayer.getKillstreak() > 100)  {
                Bukkit.broadcastMessage("§b" + killer.getName() + "§e atingiu um killstreak de §6" + killerPlayer.getKillstreak() + "§e!");
            }

            if (gamePlayer.getKillstreak() > 0) {
                Bukkit.broadcastMessage("§b" + player.getName() + "§e perdeu um killstreak de §6" + gamePlayer.getKillstreak() + "§e para §b" + player.getKiller().getName() + "§e!");
            }
            player.sendMessage("§cVocê morreu para " + player.getKiller().getName() + "§c.");
            ActionBar.sendActionBar(player, "§cVocê morreu para " + player.getKiller().getName() + "§c.");
            player.getKiller().playSound(player.getLocation(), Sound.NOTE_PLING, 2.0f, 2.0f);
            player.getKiller().sendMessage("§aVocê matou " + player.getName() + "§a.");
            Platform.getDataPlayer().save(killerPlayer);
        }

        if (player.getKiller() == null) {
            if (gamePlayer.getKillstreak() > 0) {
                Bukkit.broadcastMessage("§b" + player.getName() + "§e perdeu um killstreak de §6" + gamePlayer.getKillstreak() + "§e!");
            }
            player.sendMessage("§cVocê morreu.");
            ActionBar.sendActionBar(player, "§cVocê morreu!");
        }
        gamePlayer.setDeaths(gamePlayer.getDeaths().intValue() + 1);
        gamePlayer.setKillstreak(0);
        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 2.0f, 2.0f);
        Platform.getDataPlayer().save(gamePlayer);
        e.setDeathMessage(null);
        Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                gamePlayer.refresh();
            }
        }, 5L);
    }
}
