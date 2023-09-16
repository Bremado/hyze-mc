package com.toddydev.arena.listeners;

import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.combat.Combat;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.arena.platform.Platform;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)e.getEntity();
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
            if (gamePlayer.isProtect()) {
                player.sendMessage("§cVocê perdeu a proteção do spawn.");
                gamePlayer.setProtect(false);
                e.setCancelled(true);
                    player.getInventory().clear();
                    if (!gamePlayer.getKit().equalsIgnoreCase("PvP")) {
                        Ability ability = Platform.getKitsController().getKit(gamePlayer.getKit());
                        ItemStack item = ability.getItem();
                        player.getInventory().setItem(1, item);
                    }
                    ItemStack bowl = new ItemCreator(Material.BOWL, "§aBowl").changeAmount(64).build();
                    ItemStack red = new ItemCreator(Material.RED_MUSHROOM, "§aRed Mushroom").changeAmount(64).build();
                    ItemStack brown = new ItemCreator(Material.BROWN_MUSHROOM, "§aBrown Mushroom").changeAmount(64).build();

                    ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build();
                    ItemStack sword = new ItemCreator(Material.STONE_SWORD, "§aEspada de Pedra").changeId(0).build();


                    for (int i = 0; i < player.getInventory().getSize(); i++) {
                        if (i == 0) {
                            player.getInventory().setItem(i, sword);
                            continue;
                        }

                        if (i == 13) {
                            player.getInventory().setItem(i, bowl);
                            continue;
                        }

                        if (i == 14) {
                            player.getInventory().setItem(i, red);
                            continue;
                        }

                        if (i == 15) {
                            player.getInventory().setItem(i, brown);
                            continue;
                        }

                        if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() != Material.EMERALD) {
                            continue;
                        }
                        player.getInventory().setItem(i, soup);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        Player killer = (Player) e.getDamager();

        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        if (gamePlayer.isProtect()) {
            e.setCancelled(true);
            return;
        }

        player.showPlayer((Player) e.getDamager());

        e.setDamage(2.5D);

        if (Platform.getCombatLogController().exists(player.getUniqueId())) {
            Combat combat = new Combat(player, killer, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
            Platform.getCombatLogController().replace(combat);
        } else {
            Combat combat = new Combat(player, killer, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
            Platform.getCombatLogController().load(combat);
        }

    }
}
