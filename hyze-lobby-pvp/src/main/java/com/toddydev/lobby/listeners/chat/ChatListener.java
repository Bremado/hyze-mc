package com.toddydev.lobby.listeners.chat;

import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatListener implements Listener {

    private List<UUID> stepTwo = new ArrayList<>();

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

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
