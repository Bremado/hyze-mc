package com.toddydev.lobby.menus;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.stats.StatsPlayer;
import com.toddydev.lobby.platform.Platform;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Properties;

public class StatsMenu implements Listener {

    public static void open(Player player) {
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        Inventory inventory = Bukkit.createInventory(null, 9*3, properties.getProperty("stats-menu-title"));
        StatsPlayer statsPlayer = Platform.getStatsController().getGamePlayer(player.getUniqueId());
        ItemStack fps = new ItemCreator(Material.IRON_FENCE, "§aGladiator").withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .withLore(Arrays.asList(
                        properties.getProperty("stats-menu-lore").replace("{kills}", statsPlayer.getGladiator().getKills() + "").replace("{winstreak}", statsPlayer.getGladiator().getWinstreak() + "").split(", ")
                        )
                ).build();

        ItemStack arena = new ItemCreator(Material.IRON_CHESTPLATE, "§aSimulator").withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .withLore(Arrays.asList(
                                properties.getProperty("stats-menu-lore").replace("{kills}", statsPlayer.getSimulator().getKills() + "").replace("{winstreak}", statsPlayer.getSimulator().getWinstreak() + "").split(", ")
                        )
                ).build();

        ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .withLore(Arrays.asList(
                                properties.getProperty("stats-menu-lore").replace("{kills}", statsPlayer.getSoup().getKills() + "").replace("{winstreak}", statsPlayer.getSoup().getWinstreak() + "").split(", ")
                        )
                ).build();

        ItemStack uhc = new ItemCreator(Material.GOLDEN_APPLE, "§aUHC").withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .withLore(Arrays.asList(
                                properties.getProperty("stats-menu-lore").replace("{kills}", statsPlayer.getUhc().getKills() + "").replace("{winstreak}", statsPlayer.getUhc().getWinstreak() + "").split(", ")
                        )
                ).build();
        inventory.setItem(11, fps);
        inventory.setItem(12, arena);
        inventory.setItem(14, soup);
        inventory.setItem(15, uhc);
        player.openInventory(inventory);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (inv.getName().equalsIgnoreCase(properties.getProperty("stats-menu-title")) && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            return;
        }
    }
}
