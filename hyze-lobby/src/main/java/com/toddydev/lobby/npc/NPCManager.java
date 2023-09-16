package com.toddydev.lobby.npc;

import com.toddydev.hyze.bukkit.npc.CoreNPC;
import com.toddydev.hyze.core.server.type.ServerType;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.utils.Utils;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class NPCManager {

    @Getter
    private List<CoreNPC> npcs = new ArrayList<>();

    public NPCManager() {
        CoreNPC factions = spawn(
                "factions",
                ServerType.FACTIONS,
                Utils.deserializeLocation(Lobby.getInstance().getConfig().getString("config.locations.npcs.factions.location")),
                UUID.fromString(Lobby.getInstance().getConfig().getString("config.locations.npcs.factions.skin")),
                "§b§lFACTIONS",
                "§e0 jogando agora!"
        );


        CoreNPC pvp = spawn(
                "pvp",
                ServerType.LOBBY_PVP,
                Utils.deserializeLocation(Lobby.getInstance().getConfig().getString("config.locations.npcs.pvp.location")),
                UUID.fromString(Lobby.getInstance().getConfig().getString("config.locations.npcs.pvp.skin")),
                "§b§lPVP",
                "§e0 jogando agora!"
        );

        CoreNPC duels = spawn(
                "duels",
                ServerType.LOBBY_DUELS,
                Utils.deserializeLocation(Lobby.getInstance().getConfig().getString("config.locations.npcs.duels.location")),
                UUID.fromString(Lobby.getInstance().getConfig().getString("config.locations.npcs.duels.skin")),
                "§b§lDUELS",
                "§e0 jogando agora!"
        );

        npcs.add(factions);
        npcs.add(pvp);
        npcs.add(duels);
    }

    public CoreNPC spawn(String name, ServerType type, Location location, UUID skin, String... lines) {
        CoreNPC coreNPC = new CoreNPC(name,location);
        coreNPC.setType(type);
        coreNPC.setHologram(Arrays.asList(lines));
        coreNPC.setSkin(Utils.getSkin(skin));
        coreNPC.setSkinName(skin);
        coreNPC.spawn();
        return coreNPC;
    }



}