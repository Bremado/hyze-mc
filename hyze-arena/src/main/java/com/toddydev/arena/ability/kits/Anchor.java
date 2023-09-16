package com.toddydev.arena.ability.kits;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.player.HyzePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Anchor extends Ability {

	public Anchor() {
		setName("Anchor");
		setCooldown(5);

		setIcon(new ItemCreator(Material.ANVIL, "§aAnchor").withLore(getDescription()).build());

		setDescription(Arrays.asList("§7Não cause e nem receba repulsão."));

		setItem(null);

		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;

		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();

			GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(p.getUniqueId());
			GamePlayer damagerPlayer = Platform.getPlayerController().getGamePlayer(d.getUniqueId());

			if (gamePlayer.getKits().contains(getName())) {
				handle(p, d);
			} else if (damagerPlayer.getKits().contains(getName())) {
				handle(p, d);
			}
		}
	}

	public void handle(Player player1, Player player2) {
		player1.setVelocity(new Vector(0.0D, 0.0D, 0.0D));
		player2.setVelocity(new Vector(0.0D, 0.0D, 0.0D));

		player1.playSound(player1.getLocation(), Sound.ANVIL_LAND, 1, 1);
		player2.playSound(player2.getLocation(), Sound.ANVIL_LAND, 1, 1);

		Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
			player1.setVelocity(new Vector(0.0D, 0.0D, 0.0D));
			player2.setVelocity(new Vector(0.0D, 0.0D, 0.0D));
		}, 1L);
	}
}