package com.toddydev.arena.ability.kits;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class Ninja extends Ability {

	public Ninja() {
		setName("§aNinja");
		setCooldown(5);

		setIcon(new ItemCreator(Material.EMERALD, "§aNinja").withLore(getDescription()).build());

		setDescription(Arrays.asList("§7Ataque seu adversário pelas costas."));

		setItem(null);

		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}

	HashMap<UUID, UUID> tp = new HashMap<>();

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (tp.containsKey(event.getPlayer().getUniqueId())) {
			tp.remove(event.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void setTarget(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;

		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			Player d = (Player) e.getDamager();
			GamePlayer hGamePlayer = Platform.getPlayerController().getGamePlayer(d.getPlayer().getUniqueId());
			if (hGamePlayer.getKits().contains(getName())) {
				Player p = (Player) e.getEntity();
				tp.put(d.getUniqueId(), p.getUniqueId());
			}
		}
	}

	@EventHandler
	public void teleport(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		GamePlayer hGamePlayer = Platform.getPlayerController().getGamePlayer(p.getPlayer().getUniqueId());
		if (p.isSneaking() && hGamePlayer.getKits().contains(getName())) {
			if (tp.containsKey(p.getUniqueId())) {
				Player req = Bukkit.getPlayer(tp.get(p.getUniqueId()));
				if (req != null) {
					if (req.getWorld() == p.getWorld()) {
						if (p.getLocation().distance(req.getLocation()) <= 40) {
							p.teleport(req);
						} else {
							p.sendMessage("�cO jogador est� muito longe.");
						}
					} else {
						p.sendMessage("�cO jogador est� muito longe.");
					}
				}
			}
		}
	}
}