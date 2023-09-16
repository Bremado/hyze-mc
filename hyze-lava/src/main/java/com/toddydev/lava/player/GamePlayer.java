package com.toddydev.lava.player;

import com.toddydev.lava.player.damage.DamageType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class GamePlayer {

    private UUID uniqueId;

    private DamageType type;

    public GamePlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        type = DamageType.DAMAGE_1;
    }
}
