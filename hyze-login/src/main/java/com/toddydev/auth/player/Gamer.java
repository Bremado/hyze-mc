package com.toddydev.auth.player;

import com.toddydev.auth.events.GamerLoggedEvent;
import com.toddydev.auth.player.state.AuthState;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter @Setter
public class Gamer {

    private Player player;
    private AuthState state;

    public Gamer(Player player) {
        this.player = player;
    }
}
