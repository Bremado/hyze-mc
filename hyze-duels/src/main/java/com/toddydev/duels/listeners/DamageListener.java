package com.toddydev.duels.listeners;
import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.concurrent.TimeUnit;

import static com.toddydev.hyze.core.games.state.GameState.*;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)e.getEntity();
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());

        if (gamePlayer.getArena().getState().equals(ESPERANDO) || gamePlayer.getArena().getState().equals(INICIANDO) || gamePlayer.getArena().getState().equals(ENCERRANDO)) {
            e.setCancelled(true);
        }

        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            e.setCancelled(true);
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
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());

        if (gamePlayer.getArena().getState().equals(ESPERANDO) || gamePlayer.getArena().getState().equals(INICIANDO) || gamePlayer.getArena().getState().equals(ENCERRANDO)) {
            e.setCancelled(true);
        }
    }
}
