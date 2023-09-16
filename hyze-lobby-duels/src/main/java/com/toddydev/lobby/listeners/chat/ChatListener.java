package com.toddydev.lobby.listeners.chat;

import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
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
import java.util.Properties;
import java.util.UUID;


public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Properties properties = PropertiesCache.getProperties(corePlayer.getLanguage().getCode() + ".properties");
        e.setCancelled(true);
        if (!corePlayer.getPrefs().isChat()) {
            player.sendMessage(properties.getProperty("chat-disabled"));
            return;
        }

        TextComponent textComponent = new TextComponent(player.getDisplayName() + "§7: " + (corePlayer.getGroup().getTag() != Rank.MEMBRO ? "§f" + e.getMessage().replace("&", "§") : "§7" + e.getMessage()));

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
            if (hyzePlayer.getPrefs().isChat()) {
                players.sendMessage(textComponent);
            }
        });
    }
}
