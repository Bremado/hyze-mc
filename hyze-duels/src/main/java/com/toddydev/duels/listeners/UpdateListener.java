package com.toddydev.duels.listeners;

import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UpdateListener implements Listener {

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        Platform.getBlocksController().getBlocks().forEach(mapBlock -> {
            if (mapBlock.getTime() <= System.currentTimeMillis()) {
                mapBlock.getBlock().setType(Material.AIR);
                mapBlock.getBlock().setType(Material.AIR, true);
            }
        });
    }

}
