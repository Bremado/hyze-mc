package com.toddydev.duels.listeners;

import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.block.MapBlock;
import com.toddydev.duels.commands.BuildCommand;
import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.core.player.stats.uhc.UHC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class OtherListeners implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        for (MapBlock mapBlock : Platform.getBlocksController().getBlocks()) {
            if (!mapBlock.getBlock().equals(e.getBlock())) {
                e.setCancelled(true);
            }
            return;
        }

        if (!BuildCommand.getBuilders().contains(e.getPlayer())) {
            e.setCancelled(true);
        }

    }
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        MapBlock mapBlock = new MapBlock(event.getBlock());
        Platform.getBlocksController().load(mapBlock);
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent e) {
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(e.getPlayer().getUniqueId());
        if (gamePlayer.getArena() == null) {
            return;
        }

        if (gamePlayer.getArena().getType().equals(ArenaType.UHC) || gamePlayer.getArena().getType().equals(ArenaType.GLADIATOR)) {
            MapBlock mapBlock = new MapBlock(e.getBlockPlaced());
            Platform.getBlocksController().load(mapBlock);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
