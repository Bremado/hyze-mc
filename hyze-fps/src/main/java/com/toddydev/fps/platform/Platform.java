package com.toddydev.fps.platform;

import com.toddydev.fps.controllers.CombatLogController;
import com.toddydev.fps.controllers.PlayerController;
import com.toddydev.fps.data.DataPlayer;
import lombok.Getter;

public class Platform {

    @Getter
    private static DataPlayer dataPlayer = new DataPlayer();

    @Getter
    private static PlayerController playerController = new PlayerController();

    @Getter
    private static CombatLogController combatLogController = new CombatLogController();
}
