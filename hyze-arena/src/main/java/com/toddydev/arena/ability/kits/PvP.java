package com.toddydev.arena.ability.kits;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PvP extends Ability {

	public PvP() {
		setName("PvP");
		setCooldown(5);

		setIcon(new ItemCreator(Material.STONE_SWORD, "§aPvP").withLore(getDescription()).build());

		setItem(new ItemCreator(Material.STONE_SWORD, "§aPvP").build());

		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}	
}