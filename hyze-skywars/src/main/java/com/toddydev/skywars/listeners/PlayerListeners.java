package com.toddydev.skywars.listeners;

import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.skywars.Instance;
import com.toddydev.skywars.arena.Arena;
import com.toddydev.skywars.platform.Platform;
import com.toddydev.skywars.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        if (!Instance.isStarted()) {
            event.setKickMessage("\n§cO servidor que você tentou acessar não iniciou completamente,\n§caguarde e tente novamente.\n");
            event.setResult(PlayerPreLoginEvent.Result.KICK_FULL);
            return;
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String a = Core.getConnectController().getArena(player.getUniqueId());
        GamePlayer gamePlayer = new GamePlayer(player.getUniqueId());
        Arena arena = Platform.getArenaController().getArena("Museum");


        HyzePlayer hyzePlayer = Core.getDataPlayer().getHyzePlayer(player.getUniqueId());
        if (arena == null) {
            if (hyzePlayer.getGroup().getRank() != Rank.ADMIN) {
                player.kickPlayer("§cA arena (" + a + ") não foi encontrada!");
                return;
            }
        }

        gamePlayer.setArena(arena);

        if (arena != null) {
            arena.addPlayer(player);
        }

        Platform.getPlayerController().load(gamePlayer);
        e.setJoinMessage(null);
    }


}
