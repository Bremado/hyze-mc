package com.toddydev.arena.listeners;

import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.menus.KitSelectorMenu;
import com.toddydev.arena.menus.RecraftMenu;
import com.toddydev.arena.menus.SoupMenu;
import com.toddydev.arena.commands.BuildCommand;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class OtherListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getPlayer().getItemInHand() != null) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aSelecione um Kit")) {
                            KitSelectorMenu.open(e.getPlayer());
                            return;
                        }
                    }
                }
            }
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();
                    if (sign.getLine(1).equalsIgnoreCase(ChatColor.AQUA + "§lSOPAS")) {
                        new SoupMenu(e.getPlayer());
                    } else if (sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "§lRECRAFT")) {
                        new RecraftMenu(e.getPlayer());
                    }
                }
            }
        }
    }



    @EventHandler
    public void onTouch(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.SLIME_BLOCK) {
            Location clique = e.getClickedBlock().getLocation();
            clique.getBlock().setType(Material.SLIME_BLOCK);
            if (clique.getBlock().getType() == Material.SLIME_BLOCK) {
                clique.getBlock().setType(Material.AIR);
            }
        }

    }


    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block slime = e.getTo().getBlock().getRelative(BlockFace.DOWN);
        if (slime.getType() == Material.SLIME_BLOCK) {
            p.setVelocity(p.getLocation().getDirection().multiply(4));
            p.setVelocity(new Vector(p.getVelocity().getX(), 1.0D, p.getVelocity().getZ()));
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 66, 4));
            p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 5.0F, 5.0F);
        }
    }


    @EventHandler
    public void onSign(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("[sopa]")) {
            e.setLine(0, ChatColor.GRAY + "==============");
            e.setLine(1, ChatColor.AQUA + "§lSOPAS");
            e.setLine(2, "§eClique para abrir!");
            e.setLine(3, ChatColor.GRAY +"==============");
        }

        if (e.getLine(0).equalsIgnoreCase("[recraft]")) {
            e.setLine(0, ChatColor.GRAY + "==============");
            e.setLine(1, ChatColor.GOLD + "§lRECRAFT");
            e.setLine(2, "§eClique para abrir!");
            e.setLine(3, ChatColor.GRAY +"==============");
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!BuildCommand.getBuilders().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onBreak(BlockPlaceEvent e) {
        if (!BuildCommand.getBuilders().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
