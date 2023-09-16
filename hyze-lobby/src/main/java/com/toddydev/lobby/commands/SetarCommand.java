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
                    player.sendMessage("§cSintaxe incorreta, utilize '/setar [spawn/npc/skin] [skywars/bedwars/factions/fullpvp]'.");
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
                            if (args[1].equalsIgnoreCase("skywars")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.skywars.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("bedwars")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.bedwars.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("factions")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.rankup.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("fullpvp")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.ultra.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }else if (args[1].equalsIgnoreCase("pvp")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.pvp.location", Utils.serializeLocation(player.getLocation()));
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }else if (args[1].equalsIgnoreCase("duels")) {
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.duels.location", Utils.serializeLocation(player.getLocation()));
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
                            if (args[1].equalsIgnoreCase("skywars")) {
                                if (args.length > 2) {
                                    OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                    if (skin == null) {
                                        player.sendMessage("§cUsuário não encontrado.");
                                        return true;
                                    }
                                    File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                    configuration.set("config.locations.npcs.skywars.skin", skin.getUniqueId().toString());
                                    try {
                                        configuration.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    player.sendMessage("§aO spawn foi setado com sucesso.");
                                }
                                return true;
                            } else if (args[1].equalsIgnoreCase("bedwars")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.bedwars.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("factions")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.rankup.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("fullpvp")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.ultra.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }else if (args[1].equalsIgnoreCase("pvp")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.pvp.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }else if (args[1].equalsIgnoreCase("duels")) {
                                OfflinePlayer skin = Bukkit.getOfflinePlayer(args[2]);
                                if (skin == null) {
                                    player.sendMessage("§cUsuário não encontrado.");
                                    return true;
                                }
                                File file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                                configuration.set("config.locations.npcs.duels.skin", skin.getUniqueId().toString());
                                try {
                                    configuration.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                player.sendMessage("§aO spawn foi setado com sucesso.");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
