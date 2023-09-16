package com.toddydev.duels.platform;

import com.toddydev.duels.controller.*;
import com.toddydev.duels.data.DataPlayer;
import lombok.Getter;

public class Platform {

    @Getter
    private static DataPlayer dataPlayer = new DataPlayer();

    @Getter
    private static GameController gameController = new GameController();

    @Getter
    private static StatsController statsController = new StatsController();

    @Getter
    private static ArenaController arenaController = new ArenaController();

    @Getter
    private static BlocksController blocksController = new BlocksController();

    @Getter
    private static ArenaCreatorController arenaCreatorController = new ArenaCreatorController();

}
