package com.toddydev.duels;

import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.arena.type.sub.ArenaSubType;
import com.toddydev.duels.listeners.register.RegisterListeners;
import com.toddydev.duels.platform.Platform;
import com.toddydev.duels.recipe.Recipes;
import com.toddydev.duels.utils.LocationUtils;
import com.toddydev.hyze.bukkit.commands.loader.BukkitCommandLoader;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.games.state.GameState;
import com.toddydev.hyze.core.games.type.GameType;
import com.toddydev.hyze.core.listener.channels.ListenerChannel;
import com.toddydev.hyze.core.packets.Packets;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    @Getter @Setter
    private static Main instance;

    @Getter @Setter
    private LocationUtils location;

    @Override
    public void onLoad() {
        setInstance(this);
        setLocation(new LocationUtils());
        saveDefaultConfig();
    }

    private void loadArenas() {
        for (String name : getConfig().getConfigurationSection("arenas").getKeys(false)) {
            Arena arena = new Arena(
                    ArenaType.valueOf(getConfig().getString("arenas." + name + ".type")),
                    ArenaSubType.valueOf(getConfig().getString("arenas." + name + ".stype")),
                    name
            );

            List<Location> spawns = new ArrayList<>();
            for (String s : getConfig().getStringList("arenas." + name + ".spawns")) {
                spawns.add(location.deserialize(s));
            }
            arena.setLobby(location.deserialize(getConfig().getString("arenas." + name + ".lobby")));
            arena.setSpawns(spawns);
            Platform.getArenaController().load(arena);
            RoomType roomType = (arena.getType().equals(ArenaType.SOUP) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_SOUP_1v1 :
                    arena.getType().equals(ArenaType.SOUP) && arena.getSubType().equals(ArenaSubType.DUPLA) ? RoomType.DUELO_SOUP_2v2 :
                            arena.getType().equals(ArenaType.GLADIATOR) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_GLADIATOR_1v1 :
                                    arena.getType().equals(ArenaType.GLADIATOR) && arena.getSubType().equals(ArenaSubType.DUPLA) ? RoomType.DUELO_GLADIATOR_2v2 :
                                            arena.getType().equals(ArenaType.SIMULATOR) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_SIMULATOR_1v1 :
                                                    arena.getType().equals(ArenaType.SIMULATOR) && arena.getSubType().equals(ArenaSubType.DUPLA) ? RoomType.DUELO_SIMULATOR_2v2 :
                                                            arena.getType().equals(ArenaType.UHC) && arena.getSubType().equals(ArenaSubType.SOLO) ? RoomType.DUELO_UHC_1v1 : RoomType.DUELO_UHC_2v2);
            GameInfo gameInfo = new GameInfo(Core.getServerInfo().getName(), name, GameType.DUELS, roomType);
            arena.setMaxPlayers((arena.getSubType() == ArenaSubType.SOLO ? 2 : 4));
            gameInfo.setMaxPLayers((arena.getSubType() == ArenaSubType.SOLO ? 2 : 4));
            gameInfo.setPlayers(0);
            gameInfo.setState(GameState.ESPERANDO);
            Core.getRedisStorage().publish(ListenerChannel.GAME_START.name(), Core.getGson().toJson(gameInfo));
            Core.getGameController().load(gameInfo);
            getServer().getScheduler().runTaskLaterAsynchronously(this, arena::start, 20);
            Core.getLogger().info("A arena " + name + " foi carregada com sucesso!");
        }
    }

    @Override
    public void onEnable() {
        loadArenas();
        Recipes.loadRecipes();
        RegisterListeners.makeListeners(this);
        BukkitCommandLoader.loadCommands(this, "com.toddydev.duels");
        World world = Bukkit.getWorld("world");
        world.setAutoSave(false);

        world.setGameRuleValue("oDaylightCycle", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("randomTickSpeed", "0");

        WorldBorder border = world.getWorldBorder();
        border.setCenter(world.getSpawnLocation());
        border.setSize(120);
        world.getEntities().forEach(Entity::remove);
    }

    @Override
    public void onDisable() {

    }
}
