package com.toddydev.skywars.player;

import com.toddydev.skywars.arena.Arena;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class GamePlayer {

    private UUID uniqueId;

    private Arena arena;

    public GamePlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        arena = null;
    }
}
