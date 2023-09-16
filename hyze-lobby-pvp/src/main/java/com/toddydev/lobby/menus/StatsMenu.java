package com.toddydev.lobby.menus;

import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.lobby.platform.Platform;
import com.toddydev.lobby.player.GameFPSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class StatsMenu implements Listener {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Estatísticas");
        GameFPSPlayer gameFPSPlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        ItemStack fps = new ItemCreator(Material.DIAMOND_SWORD, "§aFPS").withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .withLore(
                        "",
                        "§7Kills: §a" + gameFPSPlayer.getKills() ,
                        "§7Deaths: §a" +  gameFPSPlayer.getDeaths(),
                        "",
                        "§7Killstreak: §a" + gameFPSPlayer.getKillstreak(),
                        ""
                ).build();

        ItemStack arena = new ItemCreator(Material.IRON_CHESTPLATE, "§aArena").withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .withLore(
                        "",
                        "§7Kills: §a0",
                        "§7Deaths: §a0",
                        "",
                        "§7Killstreak: §a0",
                        ""
                ).build();
        inventory.setItem(12, fps);
        inventory.setItem(14, arena);
        player.openInventory(inventory);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (inv.getName().equalsIgnoreCase("Estatísticas") && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            return;
        }
    }
}
