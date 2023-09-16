package com.toddydev.lobby.npc;

import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.bukkit.npc.CoreNPC;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.games.type.GameType;
import com.toddydev.hyze.core.packets.Packets;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.connect.PlayerConnect;
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

import static com.toddydev.hyze.core.games.state.GameState.ESPERANDO;
import static com.toddydev.hyze.core.server.type.ServerType.*;

public class NPCManager implements Listener {

    private CoreNPC skywars,bedwars,rankup,rankupUltra,stats;

    private List<CoreNPC> npcs = new ArrayList<>();

    public NPCManager() {
        skywars = spawn(
                "simulator",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.simulator.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.simulator.skin")
                ),
                "§b§lSIMULATOR",
                "§e{players} players!"
        );
        bedwars = spawn(
                "gladiator",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.gladiator.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.gladiator.skin")
                ),
                "§b§lGLADIATOR",
                "§e{players} players!"
        );
        rankup = spawn(
                "soup",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.soup.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.soup.skin")
                ),
                "§b§lSOPA",
                "§e{players} players!"
        );
        rankupUltra = spawn(
                "uhc",
                Utils.deserializeLocation(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.uhc.location"
                        )
                ),
                UUID.fromString(
                        Lobby.getInstance().getConfig().getString(
                                "config.locations.npcs.uhc.skin")
                ),
                "§b§lUHC",
                "§e{players} players!"
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
                "§eClique para abrir."
        );
        npcs.add(skywars);
        npcs.add(bedwars);
        npcs.add(rankup);
        npcs.add(rankupUltra);
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
        Properties properties = PropertiesCache.getProperties(corePlayer.getLanguage().getCode() + ".properties");

        if (e.getNPC().equals(stats.getNpc())) {
            StatsMenu.open(player);
            return;
        }

        if (connectCooldown.containsKey(player) && connectCooldown.get(player) > System.currentTimeMillis()) {
            return;
        }

        connectCooldown.put(player, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
        if (e.getNPC().equals(skywars.getNpc())) {
            for (GameInfo serverInfo : Core.getGameController().getServerInfos()) {
                if (serverInfo.getType().equals(GameType.DUELS)) {
                    if (serverInfo.getRoom().equals(RoomType.DUELO_SIMULATOR_1v1)) {
                        PlayerConnect playerConnect = new PlayerConnect(player.getUniqueId(), serverInfo.getDisplay());
                        Packets.Game.Connect.publish(playerConnect);
                        BukkitCore.getInstance().connectServer(player, Core.getServerController().getServerInfo(serverInfo.getName()));
                        return;
                    }
                }
            }
            player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "Simulator"));
            return;
        } else if (e.getNPC().equals(bedwars.getNpc())) {
            for (GameInfo serverInfo : Core.getGameController().getServerInfos()) {
                if (serverInfo.getType().equals(GameType.DUELS)) {
                    if (serverInfo.getRoom().equals(RoomType.DUELO_GLADIATOR_1v1)) {
                        PlayerConnect playerConnect = new PlayerConnect(player.getUniqueId(), serverInfo.getDisplay());
                        Packets.Game.Connect.publish(playerConnect);
                        BukkitCore.getInstance().connectServer(player, Core.getServerController().getServerInfo(serverInfo.getName()));
                        return;
                    }
                }
            }
            player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "Gladiator"));
            return;
        } else if (e.getNPC().equals(rankup.getNpc())) {
            for (GameInfo serverInfo : Core.getGameController().getServerInfos()) {
                if (serverInfo.getType().equals(GameType.DUELS)) {
                    if (serverInfo.getRoom().equals(RoomType.DUELO_SOUP_1v1)) {
                        if (serverInfo.getState() == ESPERANDO) {
                            PlayerConnect playerConnect = new PlayerConnect(player.getUniqueId(), serverInfo.getDisplay());
                            Packets.Game.Connect.publish(playerConnect);

                            Lobby.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    BukkitCore.getInstance().connectServer(player, Core.getServerController().getServerInfo(serverInfo.getName()));
                                }
                            }, 10L);
                            return;
                        }
                    }
                }
            }
            player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "Sopa"));
            return;
        }else if (e.getNPC().equals(rankupUltra.getNpc())) {
            for (GameInfo serverInfo : Core.getGameController().getServerInfos()) {
                if (serverInfo.getType().equals(GameType.DUELS)) {
                    if (serverInfo.getRoom().equals(RoomType.DUELO_UHC_1v1)) {
                        PlayerConnect playerConnect = new PlayerConnect(player.getUniqueId(), serverInfo.getDisplay());
                        Packets.Game.Connect.publish(playerConnect);
                        BukkitCore.getInstance().connectServer(player, Core.getServerController().getServerInfo(serverInfo.getName()));
                        return;
                    }
                }
            }
            player.sendMessage(properties.getProperty("menus-servers-unavailable").replace("{server}", "UHC"));
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
                int i = getPlayers(ROOM_SIMULATOR);
                hds.set(1, "§e" + i + " jogando agora!");
                npc.getNpc().setText(hds);
            } else if (npc.equals(bedwars)) {
                List<String> hds = new ArrayList<>();
                for (String hd : npc.getNpc().getText()) {
                    hds.add(hd);
                }
                int i = getPlayers(ROOM_GLADIATOR);
                hds.set(1, "§e" + i + " jogando agora!");
                npc.getNpc().setText(hds);
            } else if (npc.equals(rankup)) {
                List<String> hds = new ArrayList<>();
                for (String hd : npc.getNpc().getText()) {
                    hds.add(hd);
                }
                int i = getPlayers(ROOM_SOUP);
                hds.set(1, "§e" + i + " jogando agora!");
                npc.getNpc().setText(hds);
            }else if (npc.equals(rankupUltra)) {
                List<String> hds = new ArrayList<>();
                for (String hd : npc.getNpc().getText()) {
                    hds.add(hd);
                }
                int i = getPlayers(ROOM_UHC);
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
