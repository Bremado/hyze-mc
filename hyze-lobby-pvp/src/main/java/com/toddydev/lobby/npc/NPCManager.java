package com.toddydev.lobby.npc;

import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.bukkit.npc.CoreNPC;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.packets.Packets;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.hyze.core.server.type.ServerType;
import com.toddydev.hyze.core.utils.DateUtils;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.menus.StatsMenu;
import com.toddydev.lobby.utils.Utils;
import net.jitse.npclib.api.events.NPCInteractEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.toddydev.hyze.core.server.type.ServerType.*;

public class NPCManager implements Listener {

    private CoreNPC skywars,bedwars,rankup,stats;

    private List<CoreNPC> npcs = new ArrayList<>();

    public NPCManager() {
        skywars = spawn(
                "arena",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.arena.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.arena.skin")
                ),
                "§b§lARENA",
                "§a{players} jogando agora!"
        );
        bedwars = spawn(
                "fps",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.fps.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.fps.skin")
                ),
                "§b§lFPS",
                "§a{players} jogando agora!"
        );
        rankup = spawn(
                "lava",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.lava.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.lava.skin")
                ),
                "§b§lLAVA",
                "§a{players} jogando agora!"
        );
        stats = spawn(
                "stats",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.stats.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.stats.skin")
                ),
                "§b§lESTATÍSTICAS",
                "§aClique para abrir."
        );
        npcs.add(skywars);
        npcs.add(bedwars);
        npcs.add(rankup);
        npcs.add(stats);
    }

    public CoreNPC spawn(String name, Location location, UUID skin, String... lines) {
        CoreNPC coreNPC = new CoreNPC(name,location);
        coreNPC.setHologram(Arrays.asList(lines));
        coreNPC.setSkin(Utils.getSkin(skin));
        coreNPC.setSkinName(skin);
        coreNPC.spawn();
        return coreNPC;
    }


    private HashMap<Player, Long> connectCooldown = new HashMap<>();

    @EventHandler
    public void onInterct(NPCInteractEvent e) {
        Player player = e.getWhoClicked();
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(e.getWhoClicked().getUniqueId());

        if (e.getNPC().equals(stats.getNpc())) {
            StatsMenu.open(player);
            return;
        }


        if (connectCooldown.containsKey(player) && connectCooldown.get(player) > System.currentTimeMillis()) {
            player.sendMessage("§cAguarde " + DateUtils.getTime(connectCooldown.get(player)) + " para efetuar uma nova conexão.");
            return;
        }

        connectCooldown.put(player, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
        if (e.getNPC().equals(skywars.getNpc())) {
            for (ServerInfo serverInfo : Core.getServerController().getServerInfos()) {
                if (serverInfo.getType().equals(ROOM_ARENA)) {
                    BukkitCore.getInstance().connectServer(player, serverInfo);
                    return;
                }
            }
            player.sendMessage("§cNossos servidores Arena estão indisponíveis no momento.");
            return;
        } else if (e.getNPC().equals(bedwars.getNpc())) {
            for (ServerInfo serverInfo : Core.getServerController().getServerInfos()) {
                if (serverInfo.getType().equals(ROOM_FPS)) {
                    BukkitCore.getInstance().connectServer(player, serverInfo);
                    return;
                }
            }
            player.sendMessage("§cNossos servidores FPS estão indisponíveis no momento.");
            return;
        } else if (e.getNPC().equals(rankup.getNpc())) {
            for (ServerInfo serverInfo : Core.getServerController().getServerInfos()) {
                if (serverInfo.getType().equals(ROOM_LAVA)) {
                    BukkitCore.getInstance().connectServer(player, serverInfo);
                    return;
                }
            }
            player.sendMessage("§cNossos servidores LAVA estão indisponíveis no momento.");
            return;
        }
    }

    @EventHandler
    public void onTime(UpdateEvent e) {
        for (CoreNPC npc : npcs) {
            if (npc.equals(skywars)) {
                List<String> hds = new ArrayList<>();
                for (String hd : npc.getNpc().getText()) {
                    hds.add(hd);
                }
                int i = getPlayers(ROOM_ARENA);
                hds.set(1, "§e" + i + " jogando agora!");
                npc.getNpc().setText(hds);
            } else if (npc.equals(bedwars)) {
                List<String> hds = new ArrayList<>();
                for (String hd : npc.getNpc().getText()) {
                    hds.add(hd);
                }
                int i = getPlayers(ROOM_FPS);
                hds.set(1, "§e" + i + " jogando agora!");
                npc.getNpc().setText(hds);
            } else if (npc.equals(rankup)) {
                List<String> hds = new ArrayList<>();
                for (String hd : npc.getNpc().getText()) {
                    hds.add(hd);
                }
                int i = getPlayers(ROOM_LAVA);
                hds.set(1, "§e" + i + " jogando agora!");
                npc.getNpc().setText(hds);
            }
        }
    }

    public static Integer getPlayers(ServerType serverType) {
        int players = 0;
        for (ServerInfo serverInfo : Core.getServerController().getServerInfos()) {
            if (serverInfo == null) {
                continue;
            }
            if (serverType.equals(LOBBY_SKYWARS) || serverType.equals(ROOM_SKYWARS)) {
                if (serverInfo.getType().equals(serverType)) {
                    players += serverInfo.getPlayers();
                }
            } else if (serverType.equals(LOBBY_BEDWARS) || serverType.equals(ROOM_BEDWARS)) {
                if (serverInfo.getType().equals(serverType)) {
                    players += serverInfo.getPlayers();
                }
            } else if (serverType.equals(FACTIONS)) {
                if (serverInfo.getType().equals(FACTIONS)) {
                    players += serverInfo.getPlayers();
                }
            }else if (serverInfo.getType().equals(serverType)){
                players+= serverInfo.getPlayers();
            }
        }
        return players;
    }
}
