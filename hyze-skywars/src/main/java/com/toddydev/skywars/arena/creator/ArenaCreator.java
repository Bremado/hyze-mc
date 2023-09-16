package com.toddydev.skywars.arena.creator;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.skywars.arena.Arena;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
public class ArenaCreator {

    private Player player;
    private Arena arena;

    private List<Player> minPlayers = new ArrayList<>();

    public ArenaCreator(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
        minPlayers.add(player);
    }

    public void sendItems() {
        ItemStack type = new ItemCreator(Material.DIAMOND_SWORD, "§aSelecione um Modo").build();
        ItemStack sType = new ItemCreator(Material.DIAMOND_PICKAXE, "§aSelecione um Subtipo").build();

        ItemStack spawn = new ItemCreator(Material.BEACON, "§aAdicionar Spawn").build();
        ItemStack lobby = new ItemCreator(Material.BED, "§aDefinir Lobby de Espera").build();

        ItemStack save = new ItemCreator(Material.WOOL, "§aSalvar").changeId(5).build();

        player.getInventory().setItem(0, type);
        player.getInventory().setItem(1, sType);
        player.getInventory().setItem(3, spawn);
        player.getInventory().setItem(5, lobby);
        player.getInventory().setItem(8, save);
    }

}
