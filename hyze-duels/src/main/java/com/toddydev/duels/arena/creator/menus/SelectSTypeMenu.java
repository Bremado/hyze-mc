package com.toddydev.duels.arena.creator.menus;

import com.toddydev.duels.arena.creator.ArenaCreator;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.arena.type.sub.ArenaSubType;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectSTypeMenu implements Listener {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "Selecione um Sub Tipo:");
        inventory.setItem(12, new ItemCreator(Material.IRON_SWORD, "§aSolo").build());
        inventory.setItem(14, new ItemCreator(Material.DIAMOND_SWORD, "§aDupla").build());
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        ArenaCreator arenaCreator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (inv.getName().equalsIgnoreCase("Selecione um Sub Tipo:") && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            switch (e.getRawSlot()) {
                case 12:
                    arenaCreator.getArena().setSubType(ArenaSubType.SOLO);
                    player.sendMessage("§aVocê selecionou o subtipo: §fSolo");
                    break;
                case 14:
                    arenaCreator.getArena().setSubType(ArenaSubType.DUPLA);
                    player.sendMessage("§aVocê selecionou o subtipo: §fDupla");
                    break;
            }
        }
    }
}