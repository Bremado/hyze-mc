package com.toddydev.skywars.arena.team;

import com.toddydev.skywars.arena.Arena;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GameTeam {

    private Arena arena;
    private List<Player> teamPlayers = new ArrayList<>();

    public GameTeam(Arena arena) {
        this.arena = arena;
    }

    public void addPlayer(Player player) {
        teamPlayers.add(player);
    }

    public void removePlayer(Player player) {
        teamPlayers.remove(player);
    }
}
