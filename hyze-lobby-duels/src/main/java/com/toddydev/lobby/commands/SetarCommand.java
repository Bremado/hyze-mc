package com.toddydev.lobby.commands;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        if (cmd.getLabel().equalsIgnoreCase("setar")) {
            if (hyzePlayer.getGroup().getRank() == Rank.ADMIN) {
                if (args.length == 0) {
                    player.sendMessage("§cSintaxe incorreta, utilize '/setar [spawn/npc/skin/hologram] [fps/lava/arena/duels] [kills/killstreak]'.");
                    return true;
                } else if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                        configuration.set("config.locations.spawn", Utils.serializeLocation(player.getLocation()));
                        try {
                            configuration.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendMessage("§aO spawn foi setado com sucesso.");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("npc")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("stats")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.stats.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("gladiator")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.gladiator.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("simulator")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.simulator.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("uhc")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.uhc.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("soup")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.soup.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("skin")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("stats")) {
                                if (args.length > 2) {
                                    OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                    if (skin == null) {
                                        player.sendMessage("§cUsuário não encontrado.");
                                        return true;
                                    }
                                    File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                    configuration.set("config.locations.npcs.stats.skin", skin.getUniqueId().toString());
                                    try {
                                        configuration.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    player.sendMessage("§aO spawn foi setado com sucesso.");
                                }
                                return true;
                            } else if (args[1].equalsIgnoreCase("simulator")) {
                                if (args.length > 2) {
                                    OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                    if (skin == null) {
                                        player.sendMessage("§cUsuário não encontrado.");
                                        return true;
                                    }
                                    File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                    configuration.set("config.locations.npcs.simulator.skin", skin.getUniqueId().toString());
                                    try {
                                        configuration.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    player.sendMessage("§aO spawn foi setado com sucesso.");
                                }
                                return true;
                            } else if (args[1].equalsIgnoreCase("gladiator")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.gladiator.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("uhc")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.uhc.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("soup")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.soup.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("hologram")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("fps")) {
                                if (args.length > 2) {
                                    if (args[2].equalsIgnoreCase("kills")) {
                                        File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                        configuration.set("config.locations.ranking.fps.kills", Utils.serializeLocation(player.getLocation()));
                                        try {
                                            configuration.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        player.sendMessage("§aO spawn foi setado com sucesso.");
                                    }
                                    if (args[2].equalsIgnoreCase("killstreak")) {
                                        File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                        configuration.set("config.locations.ranking.fps.killstreak", Utils.serializeLocation(player.getLocation()));
                                        try {
                                            configuration.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        player.sendMessage("§aO spawn foi setado com sucesso.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
