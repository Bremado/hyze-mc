package com.toddydev.lava.menus;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.lava.platform.Platform;
import com.toddydev.lava.player.GamePlayer;
import com.toddydev.lava.player.damage.DamageType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfigMenu implements Listener {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*4, "Configurações:");
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        int slot = 10;
        for (DamageType damageType : DamageType.values()) {
            slot++;

            if (gamePlayer.getType().equals(damageType)) {
                inventory.setItem(slot,
                        new ItemCreator(Material.STAINED_GLASS_PANE, "§c" + damageType.getDisplay()).withLore("§eSelecionado.").changeId(7).build());
                continue;
            }

            if (damageType.equals(DamageType.DAMAGE_1)) {
                inventory.setItem(slot,
                        new ItemCreator(Material.STAINED_GLASS_PANE, "§a" + damageType.getDisplay()).withLore("§eClique para selecionar.").changeId(5).build());
                continue;
            }

            if (damageType.equals(DamageType.DAMAGE_2)) {
                inventory.setItem(slot,
                        new ItemCreator(Material.STAINED_GLASS_PANE, "§a" + damageType.getDisplay()).withLore("§eClique para selecionar.").changeId(13).build());
                continue;
            }

            if (damageType.equals(DamageType.DAMAGE_3)) {
                inventory.setItem(slot,
                        new ItemCreator(Material.STAINED_GLASS_PANE, "§a" + damageType.getDisplay()).withLore("§eClique para selecionar.").changeId(4).build());
                continue;
            }

            if (damageType.equals(DamageType.DAMAGE_4)) {
                inventory.setItem(slot,
                        new ItemCreator(Material.STAINED_GLASS_PANE, "§a" + damageType.getDisplay()).withLore("§eClique para selecionar.").changeId(1).build());
                continue;
            }

            if (damageType.equals(DamageType.DAMAGE_5)) {
                inventory.setItem(slot,
                        new ItemCreator(Material.STAINED_GLASS_PANE, "§a" + damageType.getDisplay()).withLore("§eClique para selecionar.").changeId(14).build());
                continue;
            }
        }
        player.openInventory(inventory);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (inv.getName().equalsIgnoreCase("Configurações:") && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            for (DamageType damageType : DamageType.values()) {
                if (item.getItemMeta().getDisplayName().endsWith(damageType.getDisplay())) {
                    player.closeInventory();
                    gamePlayer.setType(damageType);
                    player.sendMessage("§aVocê alterou o dano para §c" + damageType.getDisplay() + "§a.");
                    Platform.getDataPlayer().save(gamePlayer);
                    return;
                }
            }
            return;
        }
    }

}
