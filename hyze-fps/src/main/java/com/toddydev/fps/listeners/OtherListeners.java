package com.toddydev.fps.listeners;

import com.toddydev.fps.commands.BuildCommand;
import com.toddydev.fps.menus.RecraftMenu;
import com.toddydev.fps.menus.SoupMenu;
import com.toddydev.hyze.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class OtherListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();
                    if (sign.getLine(1).equalsIgnoreCase(ChatColor.AQUA + "§lSOPAS")) {
                        new SoupMenu(e.getPlayer());
                    } else if (sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "§lRECRAFT")) {
                        new RecraftMenu(e.getPlayer());
                    }
                }
            }
        }
    }


    @EventHandler
    public void onSign(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("[sopa]")) {
            e.setLine(0, ChatColor.GRAY + "==============");
            e.setLine(1, ChatColor.AQUA + "§lSOPAS");
            e.setLine(2, "§eClique para abrir!");
            e.setLine(3, ChatColor.GRAY +"==============");
        }

        if (e.getLine(0).equalsIgnoreCase("[recraft]")) {
            e.setLine(0, ChatColor.GRAY + "==============");
            e.setLine(1, ChatColor.GOLD + "§lRECRAFT");
            e.setLine(2, "§eClique para abrir!");
            e.setLine(3, ChatColor.GRAY +"==============");
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!BuildCommand.getBuilders().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onBreak(BlockPlaceEvent e) {
        if (!BuildCommand.getBuilders().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
