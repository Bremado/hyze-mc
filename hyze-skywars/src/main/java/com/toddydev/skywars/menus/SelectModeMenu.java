package com.toddydev.skywars.menus;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.skywars.arena.Arena;
import com.toddydev.skywars.arena.creator.ArenaCreator;
import com.toddydev.skywars.arena.type.ArenaSubType;
import com.toddydev.skywars.arena.type.ArenaType;
import com.toddydev.skywars.platform.Platform;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectModeMenu implements Listener {


    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Selecione um Modo:");
        ItemStack normal = new ItemCreator(Material.STONE_SWORD, "§aModo Normal").build();
        ItemStack rush = new ItemCreator(Material.IRON_SWORD, "§aModo Rush").build();
        ItemStack duel = new ItemCreator(Material.GOLD_SWORD, "§aModo Duel").build();
        ItemStack ranqueado = new ItemCreator(Material.DIAMOND_SWORD, "§aModo Ranqueado").build();

        inventory.setItem(11, normal);
        inventory.setItem(12, rush);
        inventory.setItem(14, duel);
        inventory.setItem(15, ranqueado);
        player.openInventory(inventory);
    }

    public static void openS(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Selecione:");
        ItemStack solo = new ItemCreator(Material.IRON_CHESTPLATE, "§aModo Solo").build();
        ItemStack team = new ItemCreator(Material.DIAMOND_CHESTPLATE, "§aModo Team").build();
        inventory.setItem(12, solo);
        inventory.setItem(14, team);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();

        ArenaCreator arenaCreator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());
        Arena arena = arenaCreator.getArena();
        if (item == null) return;

        e.setCancelled(true);

        if (inventory.getName().equalsIgnoreCase("Selecione:")) {
            switch (e.getRawSlot()) {
                case 12:
                    player.closeInventory();
                    arena.setSubType(ArenaSubType.SOLO);
                    player.sendMessage("§eVocê selecionou o sub modo §bSolo§e.");
                    break;
                case 14:
                    player.closeInventory();
                    arena.setSubType(ArenaSubType.TEAM);
                    player.sendMessage("§eVocê selecionou o sub modo §bTeam§e.");
                    break;
            }
        }

        if (inventory.getName().equalsIgnoreCase("Selecione um Modo:")) {
            switch (e.getRawSlot()) {
                case 11:
                    player.closeInventory();
                    arena.setType(ArenaType.NORMAL);
                    player.sendMessage("§eVocê selecionou o modo §bNormal§e.");
                    break;
                case 12:
                    player.closeInventory();
                    arena.setType(ArenaType.RUSH);
                    player.sendMessage("§eVocê selecionou o modo §bRush§e.");
                    break;
                case 14:
                    player.closeInventory();
                    arena.setType(ArenaType.DUEL);
                    player.sendMessage("§eVocê selecionou o modo §bDuel§e.");
                    break;
                case 15:
                    player.closeInventory();
                    arena.setType(ArenaType.RANQUEADO);
                    player.sendMessage("§eVocê selecionou o modo §bRanqueado§e.");
                    break;
            }
        }
    }
}
