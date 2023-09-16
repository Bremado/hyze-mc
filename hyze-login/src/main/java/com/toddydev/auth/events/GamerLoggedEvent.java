package com.toddydev.auth.events;

import com.toddydev.auth.player.state.AuthState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class GamerLoggedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    Player player;
    AuthState state;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
