package com.toddydev.arena.ability.kits;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.combat.Combat;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.utils.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Kangaroo extends Ability {

    public Kangaroo() {
        setName("Kangaroo");
        setCooldown(5);

        setIcon(new ItemCreator(Material.FIREWORK, "§aKangaroo").withLore(getDescription()).build());

        setDescription(Arrays.asList("§7Pule como um canguru!"));

        ItemStack item = new ItemCreator(Material.FIREWORK, "§aKangaroo").build();
        setItem(item);

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }
    
    
    ArrayList<UUID> players = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && gamePlayer.getKit().equalsIgnoreCase(getName())) {
                final double damage = event.getDamage();
                if (damage > 1.0D) {
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void removeOnMove(PlayerMoveEvent e) {
        if (players.contains(e.getPlayer().getUniqueId()) &&
                (e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR || e.getPlayer().isOnGround()))
            players.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void execute(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());

        if (!e.getAction().equals(Action.PHYSICAL) && player.getItemInHand().getType().equals(Material.FIREWORK)) {
            e.setCancelled(true);
            if (!gamePlayer.getKit().equalsIgnoreCase(getName())) {
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 5.0f, 5.0f);
                player.sendMessage("§cVocê não pode utilizar este kit.");
                return;
            }

            if (Platform.getCombatLogController().exists(player.getUniqueId())) {
                Combat combat = Platform.getCombatLogController().getCombat(player.getUniqueId());
                if (!(combat.getExpires() <= System.currentTimeMillis())) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3, 5), true);
                    player.sendMessage("§cVocê levou um hit recentemente, aguarde para usar a habilidade novamente.");
                    return;
                }
            }

            if (!players.contains(player.getUniqueId())) {
                if (!player.isSneaking()) {
                    if (!player.isOnGround()) {
                        player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
                        players.add(player.getUniqueId());
                    } else if (player.isOnGround()) {
                        player.setVelocity(new Vector(player.getVelocity().getX(), 0.9D, player.getVelocity().getZ()));
                        players.add(player.getUniqueId());
                    }
                } else if (player.isSneaking()) {
                    if (!player.isOnGround()) {
                        player.setVelocity(player.getLocation().getDirection().multiply(1.75D));
                        player.setVelocity(new Vector(player.getVelocity().getX(), 0.5D, player.getVelocity().getZ()));
                        players.add(player.getUniqueId());
                    } else if (player.isOnGround()) {
                        player.setVelocity(player.getLocation().getDirection().multiply(1.35D));
                        player.setVelocity(new Vector(player.getVelocity().getX(), 0.6D, player.getVelocity().getZ()));
                        players.add(player.getUniqueId());
                    }
                }
            }
        }
    }

}
