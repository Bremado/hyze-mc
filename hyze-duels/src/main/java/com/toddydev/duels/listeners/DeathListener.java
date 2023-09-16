package com.toddydev.duels.listeners;

import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.arena.type.sub.ArenaSubType;
import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.api.actionbar.ActionBar;
import com.toddydev.hyze.core.player.stats.StatsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());
        StatsPlayer statsPlayer = Platform.getStatsController().getGamerPlayer(player.getUniqueId());
        player.getInventory().clear();
        if (player.getKiller() != null) {
            Player killer = player.getKiller();
            StatsPlayer killerStats = Platform.getStatsController().getGamerPlayer(killer.getUniqueId());
            if (gamePlayer.getArena().getType().equals(ArenaType.SOUP)) {
                if (gamePlayer.getArena().getSubType().equals(ArenaSubType.SOLO)) {
                    killerStats.getSoup().setKills(killerStats.getSoup().getKills() + 1);
                    killerStats.getSoup().setWinstreak(killerStats.getSoup().getWinstreak() + 1);
                }
            } else if (gamePlayer.getArena().getType().equals(ArenaType.UHC)) {
                if (gamePlayer.getArena().getSubType().equals(ArenaSubType.SOLO)) {
                    killerStats.getUhc().setKills(killerStats.getUhc().getKills() + 1);
                    killerStats.getUhc().setWinstreak(killerStats.getUhc().getWinstreak() + 1);
                }
            } else if (gamePlayer.getArena().getType().equals(ArenaType.GLADIATOR)) {
                if (gamePlayer.getArena().getSubType().equals(ArenaSubType.SOLO)) {
                    killerStats.getGladiator().setKills(killerStats.getGladiator().getKills() + 1);
                    killerStats.getGladiator().setWinstreak(killerStats.getGladiator().getWinstreak() + 1);
                }
            }
            ActionBar.sendActionBar(killer, "§aVocê matou " + player.getName() + "!");
            killer.sendMessage("§aVocê matou " + player.getName() + "!");
            if (statsPlayer.getSoup().getWinstreak() > 0) {
                statsPlayer.getSoup().setWinstreak(0);
            }
            player.sendMessage("§cVocê morreu.");
            ActionBar.sendActionBar(player, "§cVocê morreu!");
            Platform.getDataPlayer().save(killerStats);
        }
        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 2.0f, 2.0f);
        Platform.getDataPlayer().save(statsPlayer);
        e.setDeathMessage(null);
        player.spigot().respawn();
        gamePlayer.addSpec(player, gamePlayer.getArena());
    }
}
