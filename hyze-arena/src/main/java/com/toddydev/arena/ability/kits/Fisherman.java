package com.toddydev.arena.ability.kits;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.player.HyzePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Fisherman extends Ability {

	public Fisherman() {
		setName("Fisherman");
		setCooldown(5);

		setIcon(new ItemCreator(Material.FISHING_ROD, "§aFisherman").withLore(getDescription()).build());

		setDescription(Arrays.asList("§7Use sua Vara de pesca", "§7para pescar seus", "§7adversários."));

		setItem(new ItemCreator(Material.FISHING_ROD, "§aFisherman").build());

		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}

	@EventHandler
	public void pescar(PlayerFishEvent e) {
		if ((e.getPlayer() instanceof Player) && (e.getCaught() instanceof Player)) {
			Player p = e.getPlayer();
			GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(p.getUniqueId());
			if (gamePlayer.getKits().contains(getName())) {
				Player d = (Player) e.getCaught();
				d.teleport(p.getLocation());
			}
		}
	}
}