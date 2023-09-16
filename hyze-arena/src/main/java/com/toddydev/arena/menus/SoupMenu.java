package com.toddydev.arena.menus;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SoupMenu {

    public SoupMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*6, "Sopas");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build());
        }
        player.openInventory(inventory);
    }
}
