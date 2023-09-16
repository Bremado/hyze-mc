package com.toddydev.auth.listener;

import com.toddydev.auth.AuthPlugin;
import com.toddydev.auth.events.GamerLoggedEvent;
import com.toddydev.auth.platform.Platform;
import com.toddydev.auth.player.Gamer;
import com.toddydev.auth.utils.Utils;
import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.hyze.core.server.type.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.github.paperspigot.Title;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.toddydev.auth.player.state.AuthState.*;

public class GamerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Gamer gamer = new Gamer(player);
        if (Platform.getDataAuth().exists(player.getUniqueId())) {
            gamer.setState(LOGIN);
            player.sendMessage("§aVocê não está registrado, crie uma conta utilizando '/login [senha]'.");
            player.sendTitle(new Title("§a§lHYZE MC", "§eUtilize '/logar [senha]' para autenticar-se.", 1,99999,1));
        } else {
            gamer.setState(REGISTER);
            player.sendMessage("§aVocê não está registrado, crie uma conta utilizando '/registrar [senha]'.");
            player.sendTitle(new Title("§a§lHYZE MC", "§eUtilize '/registrar [senha]' para autenticar-se.", 1,99999,1));
        }
        player.teleport(Utils.deserializeLocation(AuthPlugin.getInstance().getConfig().getString("config.locations.spawn")));
        player.setGameMode(GameMode.ADVENTURE);
        Platform.getGamerManager().load(gamer);
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("/login") || e.getMessage().startsWith("/logar") || e.getMessage().startsWith("/register") || e.getMessage().startsWith("/registrar")) {
            e.setCancelled(true);
        }
        e.setCancelled(true);
    }


    @EventHandler
    public void onGamerLogged(GamerLoggedEvent e) {
        Player player = e.getPlayer();
        if (e.getState().equals(REGISTERED)) {
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 2.0f, 2.0f);
            player.sendTitle(new Title("§a§lSUCESSO!", "§eVocê se registrou com sucesso!", 1,99999,1));
        } else if (e.getState().equals(LOGGED)) {
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 2.0f, 2.0f);
            player.sendTitle(new Title("§a§lSUCESSO!", "§eVocê se autenticou com sucesso!", 1,99999,1));
        }

        Bukkit.getServer().getScheduler().runTaskLater(AuthPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (ServerInfo s : Core.getServerController().getServerInfos(ServerType.LOBBY)) {
                    if (s.getPlayers() >= s.getMaxPlayers()) {
                        continue;
                    }
                    BukkitCore.getInstance().connectServer(player, s);
                    return;
                }
            }
        }, 40L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Platform.getGamerManager().unload(player.getUniqueId());
        e.setQuitMessage(null);
    }
}
