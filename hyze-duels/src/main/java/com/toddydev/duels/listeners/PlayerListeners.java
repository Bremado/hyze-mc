package com.toddydev.duels.listeners;

import com.toddydev.duels.Main;
import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.creator.ArenaCreator;
import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import com.toddydev.duels.scoreboard.ScoreboardConstructor;
import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.games.state.GameState;
import com.toddydev.hyze.core.games.type.GameType;
import com.toddydev.hyze.core.packets.Packets;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.connect.PlayerConnect;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.hyze.core.player.stats.StatsPlayer;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.hyze.core.server.type.ServerType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.text.SimpleDateFormat;

import static com.toddydev.hyze.core.games.state.GameState.ESPERANDO;
import static com.toddydev.hyze.core.games.state.GameState.INICIANDO;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        StatsPlayer statsPlayer;
        if (Platform.getDataPlayer().exists(player.getUniqueId())) {
            statsPlayer = Platform.getDataPlayer().getHyzePlayer(player.getUniqueId());
        } else {
            statsPlayer = new StatsPlayer(player.getUniqueId());
            Platform.getDataPlayer().create(statsPlayer);
        }

        Platform.getStatsController().load(statsPlayer);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String name = Core.getConnectController().getArena(player.getUniqueId());
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        if (!(hyzePlayer.getGroup().getRank() == Rank.ADMIN)) {
            if (Platform.getArenaController().getArena(name) == null) {
                player.kickPlayer("§cA arena (" + name + ") não foi encontrada.");
                return;
            }
        }
        Arena arena = Platform.getArenaController().getArena(name);
        GamePlayer gamePlayer = new GamePlayer(player.getUniqueId(), player.getName());
        if (arena != null) {
            if (arena.getPlayers().size() == arena.getMaxPlayers()) {
                player.kickPlayer("§cA arena (" + name + ") está cheia.");
                return;
            }
            gamePlayer.addPlayer(player, arena);
        }
        Platform.getGameController().load(gamePlayer);
        ScoreboardConstructor.createScoreboard(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ArenaCreator arenaCreator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().hasItemMeta()) {
                    if (player.getItemInHand().getItemMeta().hasDisplayName()) {
                        switch (player.getItemInHand().getItemMeta().getDisplayName()) {
                            case "§aJogar novamente":
                                for (GameInfo serverInfo : Core.getGameController().getServerInfos()) {
                                    if (serverInfo.getType().equals(GameType.DUELS)) {
                                        if (serverInfo.getRoom().equals(RoomType.DUELO_SOUP_1v1)) {
                                            if (!serverInfo.getName().equals(Core.getServerInfo().getName())) {
                                                if (serverInfo.getState() == ESPERANDO) {
                                                    PlayerConnect playerConnect = new PlayerConnect(player.getUniqueId(), serverInfo.getDisplay());
                                                    Packets.Game.Connect.publish(playerConnect);
                                                    BukkitCore.getInstance().connectServer(player, Core.getServerController().getServerInfo(serverInfo.getName()));
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                                player.sendMessage("§cNão existem partidas disponíveis no momento.");
                                break;
                            case "§cVoltar para o Lobby":
                                for (ServerInfo serverInfo : Core.getServerController().getServerInfos(ServerType.LOBBY_DUELS)) {
                                    BukkitCore.getInstance().connectServer(player, serverInfo);
                                    return;
                                }
                                break;
                            case "§aGolden":
                                e.setCancelled(true);
                                ItemStack skull = new ItemCreator(Material.SKULL_ITEM, "§aGolden").changeAmount(3).withSkullURL("http://textures.minecraft.net/texture/4abd703e5b8c88d4b1fcfa94a936a0d6a4f6aba44569663d3391d4883223c5").build();
                                if (player.getHealth() < 20.0D || player.getFoodLevel() < 20.0D) {
                                    if (player.getHealth() < 20.0D)
                                        player.setHealth(Math.min(player.getHealth() + 7D, 20.0D));
                                    else if (player.getFoodLevel() < 20)
                                        player.setFoodLevel(Math.min(player.getFoodLevel() + 7, 20));
                                }

                                if (player.getItemInHand().getAmount() > 1) {
                                    skull.setAmount(player.getItemInHand().getAmount() -1);
                                    player.setItemInHand(skull);
                                } else {
                                    player.setItemInHand(new ItemStack(Material.AIR));
                                }
                                player.addPotionEffect(PotionEffectType.SPEED.createEffect(800, 0));
                                player.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(1200, 0));
                                player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(800, 2));
                                player.sendMessage("§aVocê usou uma Golden.");
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.getItem().getItemStack().setType(Material.AIR);
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Material type = e.getItemDrop().getItemStack().getType();
        if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.MUSHROOM_SOUP) || type.equals(Material.BED) || type.equals(Material.PAPER)) {
            e.setCancelled(true);
            return;
        }

        e.getItemDrop().getItemStack().setType(Material.AIR);

        Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                e.getItemDrop().remove();
            }
        }, 5);

    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        e.setCancelled(true);
        if (!corePlayer.getPrefs().isChat()) {
            player.sendMessage("§cVocê está com o chat desativado.");
            return;
        }

        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());

        if (gamePlayer.getArena() != null) {
            if (gamePlayer.getArena().getState().equals(ESPERANDO) || gamePlayer.getArena().getState().equals(INICIANDO)) {
                player.sendMessage("§cAguarde a partida iniciar para enviar mensagens.");
                return;
            }
            if (gamePlayer.getArena().getSpecs().contains(player)) {
                player.sendMessage("§cVocê é um espectador, portanto você não pode falar no chat.");
                return;
            }
        }

        TextComponent textComponent = new TextComponent(player.getDisplayName() + "§7: " + (corePlayer.getGroup().getTag() != Rank.MEMBRO ? "§f" + e.getMessage().replace("&", "§") : "§7" + e.getMessage()));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                TextComponent.fromLegacyText(
                        "§7Tag: " + corePlayer.getGroup().getTag().getColor() + corePlayer.getGroup().getTag().getName() + "\n" +
                                "§7Data de envio: §e" + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(System.currentTimeMillis()) + "\n \n" +
                                "§eClique para as estatísticas.")
        ));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stats " + player.getName()));

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
            if (hyzePlayer.getPrefs().isChat()) {
                players.sendMessage(textComponent);
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());
        if (gamePlayer.getArena() != null) {
            gamePlayer.leaveArena(player);
        }
        Platform.getGameController().unload(player.getUniqueId());
        Platform.getStatsController().unload(player.getUniqueId());
    }
}
