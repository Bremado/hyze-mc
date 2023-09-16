package com.toddydev.lava.listener;

import com.toddydev.lava.platform.Platform;
import com.toddydev.lava.player.GamePlayer;
import net.minecraft.server.v1_8_R3.BlockFluids;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class DamageListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!(e.getItemDrop().getItemStack().getType().equals(Material.BOWL) || e.getItemDrop().getItemStack().getType().equals(Material.RED_MUSHROOM) || e.getItemDrop().getItemStack().getType().equals(Material.BROWN_MUSHROOM))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();

        if (e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
            for (ItemStack itemStack : ((Player) e.getEntity()).getInventory().getContents()) {
                if (itemStack != null) {
                    if (itemStack.getType().equals(Material.REDSTONE_COMPARATOR)) {
                        ((Player) e.getEntity()).getInventory().remove(itemStack);
                    }
                }
            }

            GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
            e.setDamage(gamePlayer.getType().getDamage());
        }

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }
}
