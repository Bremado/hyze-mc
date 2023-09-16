package com.toddydev.lava.listener;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.lava.Main;
import com.toddydev.lava.commands.BuildCommand;
import com.toddydev.lava.menus.ConfigMenu;
import com.toddydev.lava.menus.RecraftMenu;
import com.toddydev.lava.menus.SoupMenu;
import com.toddydev.lava.platform.Platform;
import com.toddydev.lava.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class OtherListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getPlayer().getItemInHand() != null) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aConfigurações")) {
                            ConfigMenu.open(e.getPlayer());
                            return;
                        }
                    }
                }
            }
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();
                    if (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "§lSPAWN")) {
                        refresh(e.getPlayer());
                    } else if (sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "§lRECRAFT")) {
                        new RecraftMenu(e.getPlayer());
                    }
                }
            }
        }
    }



    public void refresh(Player player) {
        player.setHealth(20.0D);
        player.setFireTicks(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
        }

        for (PotionEffect potion : player.getActivePotionEffects()) {
            player.removePotionEffect(potion.getType());
        }

        player.teleport(Main.getInstance().getLocation().getLobby());
        ItemStack bowl = new ItemCreator(Material.BOWL, "§aBowl").changeAmount(64).build();
        ItemStack red = new ItemCreator(Material.RED_MUSHROOM, "§aRed Mushroom").changeAmount(64).build();
        ItemStack brown = new ItemCreator(Material.BROWN_MUSHROOM, "§aBrown Mushroom").changeAmount(64).build();

        ItemStack config = new ItemCreator(Material.REDSTONE_COMPARATOR, "§aConfigurações").build();
        ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build();

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (i == 4) {
                player.getInventory().setItem(i, config);
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

            if (player.getInventory().getItem(i) != null) {
                continue;
            }
            player.getInventory().setItem(i, soup);
        }
    }

    @EventHandler
    public void onSign(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("[spawn]")) {
            e.setLine(0, ChatColor.GRAY + "==============");
            e.setLine(1, ChatColor.GREEN + "§lSPAWN");
            e.setLine(2, "§5Clique teleporte!");
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
