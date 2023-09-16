package com.toddydev.duels.arena.creator.menus;

import com.toddydev.duels.arena.creator.ArenaCreator;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.menus.cosmetics.join.JoinMessageMenu;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectTypeMenu implements Listener {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Selecione um Tipo:");
        inventory.setItem(11, new ItemCreator(Material.STRING, "§aSimulator").build());
        inventory.setItem(12, new ItemCreator(Material.IRON_BARDING, "§aGladiator").build());
        inventory.setItem(14, new ItemCreator(Material.GOLDEN_APPLE, "§aUHC").build());
        inventory.setItem(15, new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build());
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        ArenaCreator arenaCreator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (inv.getName().equalsIgnoreCase("Selecione um Tipo:") && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            switch (e.getRawSlot()) {
                case 11:
                    arenaCreator.getArena().setType(ArenaType.SIMULATOR);
                    player.sendMessage("§aVocê selecionou o tipo: §fSimulator");
                    break;
                case 12:
                    arenaCreator.getArena().setType(ArenaType.GLADIATOR);
                    player.sendMessage("§aVocê selecionou o tipo: §fGladiator");
                    break;
                case 14:
                    arenaCreator.getArena().setType(ArenaType.UHC);
                    player.sendMessage("§aVocê selecionou o tipo: §fUHC");
                    break;
                case 15:
                    arenaCreator.getArena().setType(ArenaType.SOUP);
                    player.sendMessage("§aVocê selecionou o tipo: §fSopa");
                    break;
            }
        }
    }

}
