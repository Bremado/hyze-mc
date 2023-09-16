package com.toddydev.lobby.menus;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.utils.DateUtils;
import com.toddydev.lobby.invite.Invite;
import com.toddydev.lobby.invite.type.InviteType;
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

import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InviteMenu implements Listener {

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public static void open(Player player) {
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        Inventory inventory = Bukkit.createInventory(null, 9*3, properties.getProperty("duel-menu-title"));

        ItemStack gladiator = new ItemCreator(Material.IRON_FENCE, "§aGladiator").withLore(properties.getProperty("duel-menu-lore-click")).withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .build();
        ItemStack simulator = new ItemCreator(Material.IRON_CHESTPLATE, "§aSimulator").withLore(properties.getProperty("duel-menu-lore-click")).withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .build();
        ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").withLore(properties.getProperty("duel-menu-lore-click")).withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .build();
        ItemStack uhc = new ItemCreator(Material.GOLDEN_APPLE, "§aUHC").withLore(properties.getProperty("duel-menu-lore-click")).withItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES)
                .build();

        inventory.setItem(11, gladiator);
        inventory.setItem(12, simulator);
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
        if (inv.getName().equalsIgnoreCase(properties.getProperty("duel-menu-title")) && item != null && item.getTypeId() != 0) {
            e.setCancelled(true);
            Invite invite;
            if (cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) >= System.currentTimeMillis()) {
                player.sendMessage(properties.getProperty("cooldown-duel"));
                return;
            }
            switch (e.getRawSlot()) {
                case 11:
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
                    invite = Platform.getInviteController().getInvite(player.getName());
                    invite.setRoomType(RoomType.DUELO_GLADIATOR_1v1);
                    Platform.getInviteController().sendInvite(invite);
                    break;
                case 12:
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
                    invite = Platform.getInviteController().getInvite(player.getName());
                    invite.setRoomType(RoomType.DUELO_SIMULATOR_1v1);
                    Platform.getInviteController().sendInvite(invite);
                    break;
                case 14:
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
                    invite = Platform.getInviteController().getInvite(player.getName());
                    invite.setRoomType(RoomType.DUELO_SOUP_1v1);
                    Platform.getInviteController().sendInvite(invite);
                    break;
                case 15:
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
                    invite = Platform.getInviteController().getInvite(player.getName());
                    invite.setRoomType(RoomType.DUELO_UHC_1v1);
                    Platform.getInviteController().sendInvite(invite);
                    break;
            }
        }
    }
}
