package com.toddydev.duels.scoreboard;

import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.api.scoreboard.Scoreboard;
import com.toddydev.hyze.bukkit.api.tag.TagManager;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.games.state.GameState;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.stats.StatsPlayer;
import com.toddydev.hyze.core.utils.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ScoreboardConstructor {

    private static HashMap<UUID, Scoreboard> scoreboardMap = new HashMap<>();

    public static void createScoreboard(Player player) {
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());
        Scoreboard scoreboard = new Scoreboard("§a§lDUELS");
        StatsPlayer statsPlayer = Platform.getStatsController().getGamerPlayer(player.getUniqueId());
        Arena arena = gamePlayer.getArena();
        if (arena != null) {
            int wins = (gamePlayer.getArena().getType() == ArenaType.SOUP ? statsPlayer.getSoup().getWinstreak() : gamePlayer.getArena().getType() == ArenaType.GLADIATOR ? statsPlayer.getGladiator().getWinstreak() : gamePlayer.getArena().getType() == ArenaType.SIMULATOR ? statsPlayer.getSimulator().getWinstreak() : statsPlayer.getUhc().getWinstreak());
            if (arena.getState() != GameState.ESPERANDO && arena.getState() != GameState.INICIANDO) {
                scoreboard.add(9, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8DUP" + Core.getServerInfo().getDisplayName().split("#")[1]);
                scoreboard.add(8, "");
                scoreboard.add(7, "§fTempo: §a" + new SimpleDateFormat("mm:ss").format(gamePlayer.getArena().getTime()));
                scoreboard.add(6, "§fModo: §a" + gamePlayer.getArena().getType().getName() + " " + gamePlayer.getArena().getSubType().getName());
                scoreboard.add(5, "");
                scoreboard.add(4, "§fMapa: §a" + gamePlayer.getArena().getName());
                scoreboard.add(3, "§fWinstreak: §a" + wins);
                scoreboard.add(2, "");
                scoreboard.add(1, "§ehyzemc.com.br");
            } else if (arena.getState() == GameState.INICIANDO){
                scoreboard.add(9, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8DUP" + Core.getServerInfo().getDisplayName().split("#")[1]);
                scoreboard.add(8, "");
                scoreboard.add(7, "§fIniciando em: §a" + new SimpleDateFormat("mm:ss").format(gamePlayer.getArena().getTime()));
                scoreboard.add(6, "§fModo: §a" + gamePlayer.getArena().getType().getName() + " " + gamePlayer.getArena().getSubType().getName());
                scoreboard.add(5, "");
                scoreboard.add(4, "§fMapa: §a" + gamePlayer.getArena().getName());
                scoreboard.add(3, "§fWinstreak: §a" + wins);
                scoreboard.add(2, "");
                scoreboard.add(1, "§ehyzemc.com.br");
            } else {
                scoreboard.add(9, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8DUP" + Core.getServerInfo().getDisplayName().split("#")[1]);
                scoreboard.add(8, "");
                scoreboard.add(7, "§fAguardando...");
                scoreboard.add(6, "§fModo: §a" + gamePlayer.getArena().getType().getName() + " " + gamePlayer.getArena().getSubType().getName());
                scoreboard.add(5, "");
                scoreboard.add(4, "§fMapa: §a" + gamePlayer.getArena().getName());
                scoreboard.add(3, "§fWinstreak: §a" + wins);
                scoreboard.add(2, "");
                scoreboard.add(1, "§ehyzemc.com.br");
            }
        } else {
            scoreboard.add(9, "§7" +new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()) + " §8DUP" + Core.getServerInfo().getDisplayName().split("#")[1]);
            scoreboard.add(8, "");
            scoreboard.add(7, "§fAguardando...");
            scoreboard.add(6, "§fModo: §a...");
            scoreboard.add(5, "");
            scoreboard.add(4, "§fMapa: §a...");
            scoreboard.add(3, "§fWinstreak: §a...");
            scoreboard.add(2, "");
            scoreboard.add(1, "§ehyzemc.com.br");
        }

        player.setScoreboard(scoreboard.getScoreboard());
        Bukkit.getOnlinePlayers().forEach(TagManager::update);
        scoreboardMap.put(player.getUniqueId(), scoreboard);
    }

    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());
        Arena arena = gamePlayer.getArena();
        if (scoreboard == null) {
            return;
        }

        if (arena != null) {
            if (arena.getState().equals(GameState.EM_JOGO)) {
                scoreboard.add(7, "§fTempo: §a" + formatarSegundos(arena.getTime()-1));
            } else if (arena.getState() == GameState.INICIANDO) {
                scoreboard.add(7, "§fIniciando em: §a" + formatarSegundos(arena.getTime()-1));
            } else {
                scoreboard.add(7, "§fAguardando...");
            }
        }

        TagManager.setTag(player, hyzePlayer.getGroup().getTag());
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
