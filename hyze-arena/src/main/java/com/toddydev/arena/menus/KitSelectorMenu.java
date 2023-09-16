package com.toddydev.arena.menus;

import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitSelectorMenu implements Listener {


    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*5, "Selecione um Kit:");
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        int slot = 9;
        for (String name : gamePlayer.getKits()) {
            if (Platform.getKitsController().getKit(name) == null) {
                continue;
            }

            slot++;
            Ability ability = Platform.getKitsController().getKit(name);
            if (name == gamePlayer.getKit()) {
                ItemStack itemStack = ability.getIcon();
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName("§c" + ability.getName());
                itemStack.setItemMeta(meta);
                inventory.setItem(slot, itemStack);
                continue;
            }

            ItemStack itemStack = ability.getIcon();
            inventory.setItem(slot, itemStack);
        }

        if (inventory.getContents().length == 0) {
            inventory.setItem(22, new ItemCreator(Material.BARRIER, "§cVocê não possuí kits.").build());
        }

        inventory.setItem(40, new ItemCreator(Material.EMERALD, "§aLoja").withLore("§eClique para acessar.").build());
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (inv.getName().equalsIgnoreCase("Selecione um Kit:") && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            for (String name : gamePlayer.getKits()) {
                if (Platform.getKitsController().getKit(name) == null) {
                    continue;
                }
                Ability ability = Platform.getKitsController().getKit(name);
                player.closeInventory();
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§a"+ability.getName())) {
                    if (gamePlayer.getKit().equalsIgnoreCase(ability.getName())) {
                        player.sendMessage("§cO kit §6" + ability.getName() + "§c já está selecionado.");
                        return;
                    }
                    gamePlayer.setKit(ability.getName());
                    player.sendMessage("§aVocê selecionou o kit §6" + ability.getName() + "§a.");
                    Platform.getDataPlayer().save(gamePlayer);
                    return;
                }
            }
            return;
        }
    }
}
