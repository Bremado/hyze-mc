package com.toddydev.duels.utils;

import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.arena.type.sub.ArenaSubType;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.games.type.GameType;
import com.toddydev.hyze.core.packets.Packets;

import java.util.HashMap;

public class GameUtils {

    private static HashMap<String, GameInfo> games = new HashMap<>();

    public static void updateGame(Arena arena) {
        RoomType roomType = (arena.getType().equals(ArenaType.SOUP) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_SOUP_1v1 :
                arena.getType().equals(ArenaType.SOUP) && arena.getSubType().equals(ArenaSubType.DUPLA) ? RoomType.DUELO_SOUP_2v2 :
                        arena.getType().equals(ArenaType.GLADIATOR) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_GLADIATOR_1v1 :
                                arena.getType().equals(ArenaType.GLADIATOR) && arena.getSubType().equals(ArenaSubType.DUPLA) ? RoomType.DUELO_GLADIATOR_2v2 :
                                        arena.getType().equals(ArenaType.SIMULATOR) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_SIMULATOR_1v1 :
                                                arena.getType().equals(ArenaType.SIMULATOR) && arena.getSubType().equals(ArenaSubType.DUPLA) ? RoomType.DUELO_SIMULATOR_2v2 :
                                                        arena.getType().equals(ArenaType.UHC) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_UHC_1v1 : RoomType.DUELO_UHC_2v2);
        GameInfo gameInfo = new GameInfo(Core.getServerInfo().getName(), arena.getName(), GameType.DUELS, roomType);
        arena.setMaxPlayers((arena.getSubType() == ArenaSubType.SOLO ? 2 : 4));
        gameInfo.setMaxPLayers((arena.getSubType() == ArenaSubType.SOLO ? 2 : 4));
        gameInfo.setPlayers(arena.getPlayers().size());
        gameInfo.setState(arena.getState());
        Packets.Game.Update.publish(gameInfo);
        games.put(arena.getName(), gameInfo);
    }

    public static GameInfo getGame(String name) {
        return games.get(name);
    }
}
