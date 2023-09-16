package com.toddydev.lobby.npc.listener;

import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.bukkit.npc.CoreNPC;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.hyze.core.server.type.ServerType;
import com.toddydev.hyze.core.utils.DateUtils;
import com.toddydev.lobby.Lobby;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.events.NPCInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.toddydev.hyze.core.server.type.ServerType.*;

public class NPCListener implements Listener {

    private HashMap<Player, Long> connectCooldown = new HashMap<>();

    @EventHandler
    public void onInterct(NPCInteractEvent e) {
        NPC npc = e.getNPC();
        Player player = e.getWhoClicked();
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(e.getWhoClicked().getUniqueId());
        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");

        if (connectCooldown.containsKey(player) && connectCooldown.get(player) > System.currentTimeMillis()) {
            return;
        }

        connectCooldown.put(player, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));

        for (CoreNPC coreNPC : Lobby.getNpcManager().getNpcs()) {
            if (coreNPC.getNpc().equals(npc)) {
                if (coreNPC.getType().equals(FACTIONS)) {
                    for (ServerInfo serverInfo : Core.getServerController().getServerInfos(coreNPC.getType())) {
                        if (serverInfo.getPlayers() < serverInfo.getMaxPlayers()) {
                            BukkitCore.getInstance().connectServer(player, serverInfo);
                            return;
                        }
                    }
                    player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "Factions"));
                    return;
                }

                if (coreNPC.getType().equals(LOBBY_DUELS)) {
                    for (ServerInfo serverInfo : Core.getServerController().getServerInfos(coreNPC.getType())) {
                        if (serverInfo.getPlayers() < serverInfo.getMaxPlayers()) {
                            BukkitCore.getInstance().connectServer(player, serverInfo);
                            return;
                        }
                    }
                    player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "Duels"));
                    return;
                }

                if (coreNPC.getType().equals(LOBBY_PVP)) {
                    for (ServerInfo serverInfo : Core.getServerController().getServerInfos(coreNPC.getType())) {
                        if (serverInfo.getPlayers() < serverInfo.getMaxPlayers()) {
                            BukkitCore.getInstance().connectServer(player, serverInfo);
                            return;
                        }
                    }
                    player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "PvP"));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onTime(UpdateEvent e) {
        for (CoreNPC npc : Lobby.getNpcManager().getNpcs()) {
            List<String> hds = new ArrayList<>();
            for (String hd : npc.getNpc().getText()) {
                hds.add(hd);
            }
            int i = getPlayers(npc.getType());
            hds.set(1, "§e" + i + " jogando agora!");
            npc.getNpc().setText(hds);
        }
    }

    private Integer getPlayers(ServerType serverType) {
        int players = 0;
        for (ServerInfo serverInfo : Core.getServerController().getServerInfos()) {
            if (serverInfo == null) {
                continue;
            }
            if (serverType.equals(LOBBY_PVP)) {
                if (serverInfo.getType().equals(LOBBY_PVP) || serverInfo.getType().equals(ROOM_FPS) || serverInfo.getType().equals(ROOM_LAVA) || serverInfo.getType().equals(ROOM_ARENA)) {
                    players += serverInfo.getPlayers();
                }
            } else

            if (serverType.equals(LOBBY_DUELS)) {
                if (serverInfo.getType().equals(LOBBY_DUELS) || serverInfo.getType().equals(ROOM_UHC) || serverInfo.getType().equals(ROOM_SOUP) || serverInfo.getType().equals(ROOM_SIMULATOR) || serverInfo.getType().equals(ROOM_GLADIATOR)) {
                    players += serverInfo.getPlayers();
                }
            } else

            if (serverType.equals(serverInfo.getType())) {
                players += serverInfo.getPlayers();
            }
        }
        return players;
    }
}