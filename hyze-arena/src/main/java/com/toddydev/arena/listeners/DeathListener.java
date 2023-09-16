package com.toddydev.arena.listeners;

import com.toddydev.arena.Main;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.arena.platform.Platform;
import com.toddydev.hyze.bukkit.api.actionbar.ActionBar;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.github.paperspigot.Title;

import java.util.HashMap;
import java.util.UUID;

public class DeathListener implements Listener {

    private HashMap<UUID, Integer> kits = new HashMap<>();
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        player.teleport(Main.getInstance().getLocation().getLobby());
        player.getInventory().clear();
        if (player.getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            killer.getInventory().setItem(0, new ItemCreator(Material.STONE_SWORD, "§aEspada de Pedra").addEnchant(Enchantment.DURABILITY, 5, true).build());
            GamePlayer killerPlayer = Platform.getPlayerController().getGamePlayer(killer.getUniqueId());
            killerPlayer.setKills(killerPlayer.getKills().intValue() + 1);
            killerPlayer.setKillstreak(killerPlayer.getKillstreak().intValue() + 1);
            if (gamePlayer.getKillstreak() > 5) {
                Bukkit.broadcastMessage("§b" + player.getName() + "§e perdeu um killstreak de §6" + gamePlayer.getKillstreak() + "§e para §b" + player.getKiller().getName() + "§e!");
            }
            killerPlayer.setCoins(killerPlayer.getCoins() + 5);
            player.sendMessage("§cVocê morreu para " + player.getKiller().getName() + "§c.");
            ActionBar.sendActionBar(player, "§cVocê morreu para " + player.getKiller().getName() + "§c.");
            player.getKiller().playSound(player.getLocation(), Sound.NOTE_PLING, 2.0f, 2.0f);
            killer.sendMessage("§6+5 coins!");
            if (killerPlayer.getKillstreak() == 5 || killerPlayer.getKillstreak() == 10 || killerPlayer.getKillstreak() == 25 || killerPlayer.getKillstreak() == 50 || killerPlayer.getKillstreak() == 75 || killerPlayer.getKillstreak() == 100 || killerPlayer.getKillstreak() > 100)  {
                killer.sendMessage("§6+100 coins!");
                killerPlayer.setCoins(killerPlayer.getCoins() + 100);
                Bukkit.broadcastMessage("§b" + killer.getName() + "§e atingiu um killstreak de §6" + killerPlayer.getKillstreak() + "§e!");
            }
            if (!kits.containsKey(player.getKiller().getUniqueId())) {
                kits.put(player.getKiller().getUniqueId(), killerPlayer.getKills() + 5);
            } else {
                if (kits.get(player.getKiller().getUniqueId()) == killerPlayer.getKills()) {
                    kits.replace(player.getKiller().getUniqueId(), killerPlayer.getKills() + 5);
                    player.getKiller().playSound(player.getKiller().getLocation(), Sound.FIREWORK_LAUNCH, 5f, 5f);
                    player.getKiller().sendTitle(new Title("§b§lKIT", "§eVocê recebeu um kit grátis."));
                    ActionBar.sendActionBar(player.getKiller(), "§aVocê recebeu um §lKIT §agrátis!");
                } else {
                    int value = (kits.get(player.getKiller().getUniqueId()) - killerPlayer.getKills());
                    player.getKiller().playSound(player.getKiller().getLocation(), Sound.NOTE_PLING, 5f, 5f);
                    ActionBar.sendActionBar(player.getKiller(),"§cRestam §6" + value + "§c kills para receber um §lKIT §cgrátis.");
                }
            }
            player.getKiller().sendMessage("§aVocê matou " + player.getName() + "§a.");
            Platform.getCombatLogController().unload(killer.getUniqueId());
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
