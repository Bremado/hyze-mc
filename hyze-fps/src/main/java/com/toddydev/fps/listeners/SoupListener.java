package com.toddydev.fps.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SoupListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSoup(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();
        Action a = event.getAction();

        if (item == null)
            return;

        if (item.getType() != Material.MUSHROOM_SOUP)
            return;

        if (!a.toString().contains("RIGHT_CLICK"))
            return;

        if (p.getHealth() < 20.0D || p.getFoodLevel() < 20.0D) {
            if (p.getHealth() < 20.0D)
                p.setHealth(Math.min(p.getHealth() + 7D, 20.0D));
            else if (p.getFoodLevel() < 20)
                p.setFoodLevel(Math.min(p.getFoodLevel() + 7, 20));
            item.setType(Material.BOWL);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
