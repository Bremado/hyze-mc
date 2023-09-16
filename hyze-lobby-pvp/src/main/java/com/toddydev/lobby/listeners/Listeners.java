package com.toddydev.lobby.listeners;

import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.bukkit.menus.cosmetics.CosmeticMenu;
import com.toddydev.hyze.bukkit.menus.lobbies.LobbiesMenu;
import com.toddydev.hyze.bukkit.menus.profile.ProfileMenu;
import com.toddydev.hyze.bukkit.menus.servers.ServersMenu;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.hyze.core.server.type.ServerType;
import com.toddydev.hyze.core.utils.DateUtils;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.commands.BuildCommand;
import com.toddydev.lobby.platform.Platform;
import com.toddydev.lobby.player.GameFPSPlayer;
import com.toddydev.lobby.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.github.paperspigot.Title;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Listeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.getInventory().clear();
        sendItems(player);
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        GameFPSPlayer gameFPSPlayer;
        if (Platform.getDataFpsPlayer().exists(player.getUniqueId())) {
            gameFPSPlayer = Platform.getDataFpsPlayer().getFpsPlayer(player.getUniqueId());
        } else {
            gameFPSPlayer = new GameFPSPlayer(player.getUniqueId());
            Platform.getDataFpsPlayer().create(gameFPSPlayer);
        }
        Platform.getPlayerController().load(gameFPSPlayer);

        if (corePlayer.getStatus().isPremium()) {
            if (Platform.getSkinController().getSkin(player.getUniqueId()) == null) {
                Platform.getSkinController().load(player.getUniqueId(), Utils.getSkin(player.getUniqueId()));
            }
        }

        player.setGameMode(GameMode.ADVENTURE);
        if (corePlayer.getGroup().getRank() != Rank.MEMBRO) {
            player.teleport(Utils.deserializeLocation(Lobby.getInstance().getConfig().getString("config.locations.spawn")).add(0,4.0,0));
        } else {
            player.teleport(Utils.deserializeLocation(Lobby.getInstance().getConfig().getString("config.locations.spawn")));
        }
        if (corePlayer.getGroup().getRank() != Rank.MEMBRO && corePlayer.getGroup().getTag() != Rank.MEMBRO) {
            if (corePlayer.getPrefs().isFly()) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }
        }
        Core.getNPCController().getNpcs().forEach(npc -> {
            npc.getNpc().show(player);
            if (npc.getName().equalsIgnoreCase("stats")) {
                npc.getNpc().setSkin(Platform.getSkinController().getSkin(player.getUniqueId()));
            }
        });
        if (!corePlayer.getPrefs().isVisibility()) {
            for (Player players : Lobby.getInstance().getServer().getOnlinePlayers()) {
                HyzePlayer hyzePlayers = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
                if (!hyzePlayers.getPrefs().isVisibility()) {
                    players.hidePlayer(players);
                }
                player.hidePlayer(players);
            }
        }

        Bukkit.getOnlinePlayers().forEach(players -> {
            HyzePlayer p = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
            if (corePlayer.getPrefs().isVanish()) {
                if (!(p.getGroup().getRank() == Rank.ADMIN)) {
                    players.hidePlayer(player);
                }
            }
            if (p.getPrefs().isVanish()) {
                if (!(corePlayer.getGroup().getRank() == Rank.ADMIN)) {
                    player.hidePlayer(players);
                }
            }
            if (!p.getPrefs().isVisibility()) {
                if (!(corePlayer.getGroup().getRank().ordinal() <= Rank.STREAM.ordinal()) && !(corePlayer.getGroup().getTag().ordinal() <= Rank.STREAM.ordinal())) {
                    players.hidePlayer(player);
                }
            }
        });

        if (corePlayer.getGroup().getRank() != Rank.MEMBRO && corePlayer.getGroup().getTag() != Rank.MEMBRO && !corePlayer.getPrefs().isVanish()) {
            e.setJoinMessage(corePlayer.getPrefs().getJoinMessage().getMessage().replace("{player}", player.getDisplayName()));
        } else {
            e.setJoinMessage(null);
        }
        player.sendTitle(new Title("§a§lHYZE", "§eSeja bem-vindo!", 20, 20, 20));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!BuildCommand.getBuilders().contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWheather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!BuildCommand.getBuilders().contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    private HashMap<Player, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getPlayer().getItemInHand().hasItemMeta()) {
            if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aPerfil")) {
                new ProfileMenu(e.getPlayer());
            } else if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu de Jogos")) {
                new ServersMenu(player);
            } else if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobbies")) {
                new LobbiesMenu(player, ServerType.LOBBY);
            } else if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§fPlayers: §aOn")) {
                if (cooldown.containsKey(player) && cooldown.get(player) > System.currentTimeMillis()) {
                    player.sendMessage("§cAguarde " + DateUtils.getTime(cooldown.get(player)) + " para efetuar uma nova alteração.");
                    return;
                }
                HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
                cooldown.put(player, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                corePlayer.getPrefs().setVisibility(false);
                for (Player players : Lobby.getInstance().getServer().getOnlinePlayers()) {
                    HyzePlayer skyPlayer = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
                    if (!(skyPlayer.getGroup().getRank().ordinal() <= Rank.STREAM.ordinal())) {
                        player.hidePlayer(players);
                    }
                }
                player.getInventory().clear();
                sendItems(player);
            } else if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§fPlayers: §cOff")) {
                if (cooldown.containsKey(player) && cooldown.get(player) > System.currentTimeMillis()) {
                    player.sendMessage("§cAguarde " + DateUtils.getTime(cooldown.get(player)) + " para efetuar uma nova alteração.");
                    return;
                }
                HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
                cooldown.put(player, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                corePlayer.getPrefs().setVisibility(true);
                for (Player players : Lobby.getInstance().getServer().getOnlinePlayers()) {
                    HyzePlayer skyPlayer = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
                    if (!(skyPlayer.getGroup().getRank().ordinal() <= Rank.STREAM.ordinal())) {
                        player.showPlayer(players);
                    }
                }
                player.getInventory().clear();
                sendItems(player);
            } else if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aColecionáveis")) {
                new CosmeticMenu(player);
                return;
            }
        }
        if (!BuildCommand.getBuilders().contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    public void sendItems(Player player) {
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        ItemStack item = (corePlayer.getPrefs().isVisibility() ? new ItemCreator(Material.INK_SACK,"§fPlayers: §aOn").changeId(10).build() : new ItemCreator(Material.INK_SACK,"§fPlayers: §cOff").changeId(8).build());
        player.getInventory().setItem(0, new ItemCreator(Material.COMPASS, "§aMenu de Jogos").build());
        player.getInventory().setItem(1, new ItemCreator(Material.SKULL_ITEM, "§aPerfil").withSkullOwner(player.getName()).build());
        player.getInventory().setItem(4, new ItemCreator(Material.CHEST, "§aColecionáveis").build());
        player.getInventory().setItem(7, item);
        player.getInventory().setItem(8, new ItemCreator(Material.NETHER_STAR, "§aLobbies").build());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Platform.getPlayerController().unload(e.getPlayer().getUniqueId());
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
            p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 3.0F, 3.0F);
        }

    }
}
