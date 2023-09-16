package com.toddydev.fps.listeners;

import com.toddydev.fps.Main;
import com.toddydev.fps.combat.Combat;
import com.toddydev.fps.platform.Platform;
import com.toddydev.fps.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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

        if (Platform.getCombatLogController().exists(player.getUniqueId())) {
            Combat combat = new Combat(player, killer, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
            Platform.getCombatLogController().replace(combat);
        } else {
            Combat combat = new Combat(player, killer, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
            Platform.getCombatLogController().load(combat);
        }

    }
}
