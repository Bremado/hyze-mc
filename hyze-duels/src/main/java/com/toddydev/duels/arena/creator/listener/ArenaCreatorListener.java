package com.toddydev.duels.arena.creator.listener;

import com.toddydev.duels.Main;
import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.creator.ArenaCreator;
import com.toddydev.duels.arena.creator.menus.SelectSTypeMenu;
import com.toddydev.duels.arena.creator.menus.SelectTypeMenu;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bungee.api.yaml.YamlConfig;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.hyze.core.server.type.ServerType;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaCreatorListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ArenaCreator arenaCreator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());
        if (arenaCreator == null) {
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().hasItemMeta()) {
                    if (player.getItemInHand().getItemMeta().hasDisplayName()) {
                        Arena arena = arenaCreator.getArena();
                        switch (player.getItemInHand().getItemMeta().getDisplayName()) {
                            case "§aAdicionar Spawn":
                                arena.getSpawns().add(player.getLocation());
                                player.sendMessage("");
                                player.sendMessage("§6§lMODO DIRETOR:");
                                player.sendMessage("  §aVocê adicionou mais um spawn. Agora existem §f" + arena.getSpawns().size() + "§a spawns.");
                                player.sendMessage("");
                                break;
                            case "§aDefinir Lobby":
                                arena.setLobby(player.getLocation());
                                player.sendMessage("");
                                player.sendMessage("§6§lMODO DIRETOR:");
                                player.sendMessage("  §aVocê definiu o lobby de espera da arena.");
                                player.sendMessage("  §aLocalização: §f" + Main.getInstance().getLocation().serialize(player.getLocation()));
                                player.sendMessage("");
                                break;
                            case "§aDefinir Tipo":
                                SelectTypeMenu.open(player);
                                break;
                            case "§aDefinir Subtipo":
                                SelectSTypeMenu.open(player);
                                break;
                            case "§aSalvar":
                                if (arena.getLobby() == null) {
                                    player.sendMessage("§cVocê deve definir o lobby primeiro.");
                                    return;
                                }

                                if (arena.getSpawns().isEmpty() || arena.getSpawns().size() < 2) {
                                    player.sendMessage("§cVocê deve adicionar mais de 1 spawn.");
                                    return;
                                }

                                if (arena.getType() == null) {
                                    player.sendMessage("§cVocê deve inserir o tipo da arena.");
                                    return;
                                }

                                if (arena.getSubType() == null) {
                                    player.sendMessage("§cVocê deve inserir o subtipo da arena.");
                                    return;
                                }
                                try {
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                    List<String> spawns = new ArrayList<>();
                                    for (Location location : arena.getSpawns()) {
                                        spawns.add(Main.getInstance().getLocation().serialize(location));
                                    }
                                    configuration.set("arenas." + arena.getName() + ".lobby", Main.getInstance().getLocation().serialize(arena.getLobby()));
                                    configuration.set("arenas." + arena.getName() + ".type", arena.getType().name());
                                    configuration.set("arenas." + arena.getName() + ".stype", arena.getSubType().name());
                                    configuration.set("arenas." + arena.getName() + ".spawns", spawns);
                                    configuration.save(file);
                                    player.sendMessage("");
                                    player.sendMessage("§6§lMODO DIRETOR:");
                                    player.sendMessage("  §aA arena foi salva com sucesso.");
                                    player.sendMessage("");
                                    player.getInventory().clear();
                                    Platform.getArenaCreatorController().unload(player.getUniqueId());
                                    for (ServerInfo serverInfo : Core.getServerController().getServerInfos(ServerType.LOBBY_DUELS)) {
                                        BukkitCore.getInstance().connectServer(player, serverInfo);
                                        return;
                                    }
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
}
