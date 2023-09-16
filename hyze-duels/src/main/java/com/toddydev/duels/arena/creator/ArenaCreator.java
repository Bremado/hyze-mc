package com.toddydev.duels.arena.creator;

import com.toddydev.duels.arena.Arena;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public class ArenaCreator {

    private Arena arena;
    private Player player;

    public ArenaCreator(Arena arena, Player player) {
        this.arena = arena;
        this.player = player;
    }

    public void sendItems() {
        ItemStack cancel = new ItemCreator(Material.INK_SACK, "§aCancelar").changeId(1).build();
        ItemStack spawns = new ItemCreator(Material.BEACON, "§aAdicionar Spawn").build();
        ItemStack lobby = new ItemCreator(Material.BED, "§aDefinir Lobby").build();
        ItemStack type = new ItemCreator(Material.DIAMOND_SWORD, "§aDefinir Tipo").build();
        ItemStack sType = new ItemCreator(Material.DIAMOND_PICKAXE, "§aDefinir Subtipo").build();
        ItemStack save = new ItemCreator(Material.INK_SACK, "§aSalvar").changeId(10).build();

        player.getInventory().setItem(0, type);
        player.getInventory().setItem(1, sType);
        player.getInventory().setItem(3, spawns);
        player.getInventory().setItem(5, lobby);
        player.getInventory().setItem(8, save);
        player.getInventory().setItem(9, cancel);
    }
}
