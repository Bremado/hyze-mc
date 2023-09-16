package com.toddydev.arena.platform;

import com.toddydev.arena.controllers.CombatLogController;
import com.toddydev.arena.controllers.KitsController;
import com.toddydev.arena.controllers.PlayerController;
import com.toddydev.arena.data.DataPlayer;
import lombok.Getter;

public class Platform {

    @Getter
    private static DataPlayer dataPlayer = new DataPlayer();

    @Getter
    private static KitsController kitsController = new KitsController();

    @Getter
    private static PlayerController playerController = new PlayerController();

    @Getter
    private static CombatLogController combatLogController = new CombatLogController();
}
