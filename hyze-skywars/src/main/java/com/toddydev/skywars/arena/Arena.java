package com.toddydev.skywars.arena;

import com.toddydev.hyze.core.games.state.GameState;
import com.toddydev.skywars.Instance;
import com.toddydev.skywars.arena.team.GameTeam;
import com.toddydev.skywars.arena.type.ArenaSubType;
import com.toddydev.skywars.arena.type.ArenaType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Arena {

    private String name;
    private Location lobby;

    private ArenaType type;
    private ArenaSubType subType;
    private GameState state;

    private int maxPlayers;
    private int minPlayers;

    private int time;

    private List<Location> spawns = new ArrayList<>();

    private List<GameTeam> teams = new ArrayList<>();

    private List<Player> gamePlayers = new ArrayList<>();
    private List<Player> spectators = new ArrayList<>();

    public Arena(String name, Location lobby) {
        this.name = name;
        this.lobby = lobby;
        setState(GameState.ESPERANDO);
    }

    public void start() {
        Instance.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Instance.getInstance(), new Runnable() {
            @Override
            public void run() {
                switch (state){
                    case ESPERANDO:
                        if (gamePlayers.size() > minPlayers && gamePlayers.size() < maxPlayers) {
                            time = 20;
                            setState(GameState.INICIANDO);
                            gamePlayers.forEach(player -> {
                                player.playSound(player.getLocation(), Sound.NOTE_PLING, 5.0f, 5.0f);
                                player.sendTitle(new Title("§b§lSKY WARS", "§eIniciando em §b" + time + "§e segundos!", 10, 20, 10));
                                player.sendMessage("§eA partida vai começar em §b" + time + "§e segundos!");
                            });
                        }
                        break;
                    case INICIANDO:
                        time--;
                        if (gamePlayers.size() < minPlayers) {
                            gamePlayers.forEach(player -> {
                                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 5.0f, 5.0f);
                                player.sendTitle(new Title("§4§lTIMER", "§cNão existem jogadores o suficiente.", 10, 20, 10));
                                player.sendMessage("");
                                player.sendMessage("§4§lTEMPORIZADOR:");
                                player.sendMessage(" §cNão existem jogadores o suficiente para começar a partida.");
                                player.sendMessage("");
                            });
                            setState(GameState.ESPERANDO);
                        }

                        if (time == 6) {
                            // TELEPORTE
                        }

                        if (time == 10 || time == 5 || time == 4 || time == 3 || time == 2 || time == 1) {
                            gamePlayers.forEach(player -> {
                                player.playSound(player.getLocation(), Sound.NOTE_PLING, 5.0f, 5.0f);
                                player.sendTitle(new Title("§b§lSKY WARS", "§eIniciando em §b" + time + "§e segundos!", 10, 20, 10));
                                player.sendMessage("§eA partida vai começar em §b" + time + "§e segundos!");
                            });
                        }

                        if (time == 0) {
                            gamePlayers.forEach(player -> {
                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 5.0f, 5.0f);
                                player.sendTitle(new Title("§b§lSKY WARS", "§aA partida começou!", 5, 10, 5));
                                player.sendMessage("§aA partida começou!");
                            });
                            setState(GameState.EM_JOGO);
                        }
                        break;
                    case EM_JOGO:
                        time++;
                        if (gamePlayers.size() == 1) {
                            time = 15;
                            setState(GameState.ENCERRANDO);
                        }

                        if (time == 300) {
                            // ACONTECIMENTO 1
                        }

                        if (time == 360) {
                            // ACONTECIMENTO 2
                        }
                        break;
                    case ENCERRANDO:
                        time--;
                        if (time == 5) {
                            setState(GameState.REINICIANDO);
                        }
                        gamePlayers.forEach(player -> {
                            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 5.0f, 5.0f);
                            player.sendTitle(new Title("§6§lVITÓRIA", "§aVocê foi o último sobrevivente!", 5, 10, 5));
                        });
                        spectators.forEach(player -> {
                            player.sendTitle(new Title("§4§lDERROTA", "§cNão foi dessa vez!", 5, 10, 5));
                        });
                        break;
                    case REINICIANDO:
                        // RESTART ARENA
                        break;
                }
            }
        }, 0L, 20L);
    }

    public void addSpawn(Location location) {
        spawns.add(location);
    }
    public void addPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setFireTicks(0);

        gamePlayers.add(player);

        player.teleport(lobby);

        gamePlayers.forEach(p -> {
            p.sendMessage("§b" + player.getName() + "§e entrou na partida! §b(" + gamePlayers.size() + "/" + maxPlayers + ")");
        });
    }
    public void removePlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setFireTicks(0);

        gamePlayers.remove(player);
    }
    public void addSpectator(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setFireTicks(0);

        spectators.add(player);
    }
    public void removeSpectator(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setFireTicks(0);

        spectators.remove(player);
    }
}
