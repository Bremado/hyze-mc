package com.toddydev.duels.arena;

import com.toddydev.duels.Main;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.arena.type.sub.ArenaSubType;
import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import com.toddydev.duels.scoreboard.ScoreboardConstructor;
import com.toddydev.duels.utils.GameUtils;
import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.state.GameState;
import com.toddydev.hyze.core.listener.channels.ListenerChannel;
import com.toddydev.hyze.core.server.ServerInfo;
import com.toddydev.hyze.core.server.type.ServerType;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Arena {

    private ArenaType type;
    private ArenaSubType subType;

    private GameState state;

    private String name;

    private Location lobby;

    private int time;

    private int maxPlayers;

    private List<Location> spawns = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<Player> specs = new ArrayList<>();

    public Arena(ArenaType type, ArenaSubType subType, String name) {
        this.type = type;
        this.subType = subType;
        this.name = name;
        setState(GameState.ESPERANDO);
    }

    public void start() {
        Main.getInstance().getServer().getScheduler().runTaskTimer(Main.getInstance(), () -> {
            GameUtils.updateGame(this);
            switch (state) {
                case ESPERANDO:
                    if (players.size() == (subType == ArenaSubType.SOLO ? 2 : 4)) {
                        time = 6;
                        setState(GameState.INICIANDO);
                        players.forEach(ScoreboardConstructor::updateScoreboard);
                        GameUtils.updateGame(this);
                    }
                    break;
                case INICIANDO:
                    time--;
                    if (players.size() != (subType == ArenaSubType.SOLO ? 2 : 4)) {
                        players.forEach(player -> {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS_DRUM, 3.0f, 3.0f);
                            player.sendMessage("§cNão existem jogadores o suficiente para iniciar a partida.");
                        });
                        setState(GameState.ESPERANDO);
                        time = 60;
                        players.forEach(ScoreboardConstructor::updateScoreboard);
                        GameUtils.updateGame(this);
                    } else if (time == 5 || time == 4 || time == 3 || time == 2 || time == 1) {
                        players.forEach(ScoreboardConstructor::updateScoreboard);
                        players.forEach(player -> {
                            player.sendTitle(new Title("§b§lDUELS",  "§eIniciando em §b" + time + "§e segundos.", (time == 5 ? 20 : 0),20,(time == 5 ? 20 : 0)));
                            player.sendMessage("§eA partida vai começar em §b" + time + "s§e.");
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0f, 2.0f);
                        });
                    } else if (time == 0) {
                        setState(GameState.EM_JOGO);
                        players.forEach(ScoreboardConstructor::updateScoreboard);
                        players.forEach(player -> {
                            player.sendTitle(new Title("§b§lLUTE!", "§eA partida começou.", 0, 20, 10));
                            player.sendMessage("§eA partida começou!");
                            player.playSound(player.getLocation(), Sound.SLIME_WALK, 2.0f, 2.0f);
                        });
                        teleport();
                        time = 0;
                        GameUtils.updateGame(this);
                    }
                    break;
                case EM_JOGO:
                    time+= 2;
                    players.forEach(ScoreboardConstructor::updateScoreboard);
                    if (players.size() <= 1) {
                        if (subType == ArenaSubType.SOLO) {
                            players.forEach(player -> {
                                player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 20, 20);
                                player.sendTitle(new Title("§a§lVITÓRIA", "§eVocê ganhou o duelo!", 40,50,40));
                                TextComponent p1 = new TextComponent("§aVocê ganhou o duelo, clique ");
                                TextComponent p2 = new TextComponent("§a para jogar novamente.");
                                TextComponent textComponent = new TextComponent("§b§lAQUI");
                                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playagain"));
                                p1.addExtra(textComponent);
                                p1.addExtra(p2);
                                player.sendMessage("");
                                player.sendMessage(p1);
                                player.sendMessage("");
                            });
                            specs.forEach(spec -> {
                                spec.sendTitle(new Title("§4§lDERROTA", "§cVocê perdeu o duelo!", 40,50,40));
                                spec.sendMessage("§cVocê perdeu o duelo!");
                                TextComponent p1 = new TextComponent("§cVocê perdeu o duelo, clique ");
                                TextComponent p2 = new TextComponent("§c para jogar novamente.");
                                TextComponent textComponent = new TextComponent("§b§lAQUI");
                                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playagain"));
                                p1.addExtra(textComponent);
                                p1.addExtra(p2);
                                spec.sendMessage("");
                                spec.sendMessage(p1);
                                spec.sendMessage("");
                            });
                            setState(GameState.ENCERRANDO);
                            time = 20;
                            GameUtils.updateGame(this);
                        }
                    }

                    if (time == 301) {
                        players.forEach(player -> {
                            player.sendMessage("§eA partida acaba em §b5§e minutos.");
                            player.sendTitle(new Title("§c§TEMPO!", "§eA partida acaba em 5 minutos.",40,50,40));
                        });
                    }

                    if (time > 600) {
                        if (players.size() == 2) {
                            players.forEach(player->{
                                player.sendMessage("§cO tempo acabou!");
                                player.sendTitle(new Title("§c§lTEMPO ESGOTADO!", "§eO tempo da partida esgotou.",40,50,40));
                            });
                            setState(GameState.ENCERRANDO);
                            GameUtils.updateGame(this);
                        }
                    }
                    break;
                case ENCERRANDO:
                    time-= 2;
                    players.forEach(player -> {
                        player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 20, 20);
                    });

                    if (time == 0) {
                        for (ServerInfo serverInfo : Core.getServerController().getServerInfos(ServerType.LOBBY_DUELS)) {
                            players.forEach(player -> {
                                BukkitCore.getInstance().connectServer(player, serverInfo);
                            });
                            specs.forEach(specs -> {
                                BukkitCore.getInstance().connectServer(specs, serverInfo);
                            });
                        }

                        players.clear();
                        specs.clear();

                        Platform.getBlocksController().getBlocks().forEach(mapBlock -> {
                            mapBlock.getBlock().setType(Material.AIR);
                        });

                        Arena a = new Arena(type, subType, name);

                        a.setLobby(lobby);
                        a.setSpawns(spawns);

                        a.start();
                        Platform.getArenaController().unload(name);
                        Platform.getArenaController().load(a);
                    }
                    break;
            }
        }, 20,20);
    }

    public void teleport() {
        if (subType.equals(ArenaSubType.SOLO)) {
            Location l1 = spawns.get(0);
            Location l2 = spawns.get(1);
            Player player = players.get(0);
            Player player2 = players.get(1);

            player.teleport(l1.add(0,0.5,0));
            player2.teleport(l2.add(0,0.5,0));

            GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());
            GamePlayer gamePlayer1 = Platform.getGameController().getGamerPlayer(player2.getUniqueId());

            gamePlayer.sendItems(player);
            gamePlayer1.sendItems(player2);
        }
    }



    public static String formatarSegundos(Integer i) {
        int minutes = i.intValue() / 60;
        int seconds = i.intValue() % 60;
        String disMinu = (minutes < 10 ? "" : "") + minutes;
        String disSec = (seconds < 10 ? "0" : "") + seconds;
        String formattedTime = disMinu + ":" + disSec;
        return formattedTime;
    }

}
