package com.toddydev.fps.listeners;

import com.toddydev.fps.Main;
import com.toddydev.fps.combat.Combat;
import com.toddydev.fps.platform.Platform;
import com.toddydev.fps.player.GamePlayer;
import com.toddydev.hyze.bukkit.api.actionbar.ActionBar;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.text.SimpleDateFormat;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer;
        if (Platform.getDataPlayer().exists(player.getUniqueId())) {
            gamePlayer = Platform.getDataPlayer().getHyzePlayer(player.getUniqueId());
        } else {
            gamePlayer = new GamePlayer(player.getUniqueId());
            Platform.getDataPlayer().create(gamePlayer);
        }
        gamePlayer.refresh();
        Platform.getPlayerController().load(gamePlayer);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.getItem().getItemStack().setType(Material.AIR);
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Material type = e.getItemDrop().getItemStack().getType();
        if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.MUSHROOM_SOUP)) {
            e.setCancelled(true);
            return;
        }

        e.getItemDrop().getItemStack().setType(Material.AIR);

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
        if (Platform.getCombatLogController().exists(player.getUniqueId())) {
            Combat combat = Platform.getCombatLogController().getCombat(player.getUniqueId());
            if (!(Platform.getCombatLogController().getCombat(player.getUniqueId()).getExpires() <= System.currentTimeMillis())) {
                GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());
                GamePlayer killerPlayer = Platform.getPlayerController().getGamePlayer(combat.getKiller().getUniqueId());

                gamePlayer.setDeaths(gamePlayer.getDeaths().intValue() + 1);
                gamePlayer.setKillstreak(0);

                killerPlayer.setKillstreak(killerPlayer.getKillstreak().intValue() + 1);
                killerPlayer.setKills(killerPlayer.getKills().intValue() + 1);
                ActionBar.sendActionBar(combat.getKiller(), "§c" + player.getName() + " deslogou em combate.");
                Platform.getDataPlayer().save(gamePlayer);
                Platform.getDataPlayer().save(killerPlayer);
            }
            Platform.getCombatLogController().unload(combat.getKiller().getUniqueId());
            Platform.getCombatLogController().unload(player.getUniqueId());
        }
        Platform.getPlayerController().unload(player.getUniqueId());
    }

}
