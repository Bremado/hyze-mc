package com.toddydev.lava.menus;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RecraftMenu {

    public RecraftMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Recraft");
        inventory.setItem(10, new ItemCreator(Material.BOWL, "§aBowl").changeAmount(64).build());
        inventory.setItem(11, new ItemCreator(Material.RED_MUSHROOM, "§aRed Mushroom").changeAmount(64).build());
        inventory.setItem(12, new ItemCreator(Material.BROWN_MUSHROOM, "§aBrown Mushroom").changeAmount(64).build());
        inventory.setItem(14, new ItemCreator(Material.BOWL, "§aBowl").changeAmount(64).build());
        inventory.setItem(15, new ItemCreator(Material.RED_MUSHROOM, "§aRed Mushroom").changeAmount(64).build());
        inventory.setItem(16, new ItemCreator(Material.BROWN_MUSHROOM, "§aBrown Mushroom").changeAmount(64).build());
        player.openInventory(inventory);
    }
}
