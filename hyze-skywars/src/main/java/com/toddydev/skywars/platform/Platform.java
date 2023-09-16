package com.toddydev.skywars.platform;

import com.toddydev.skywars.Matches;
import com.toddydev.skywars.controllers.ArenaController;
import com.toddydev.skywars.controllers.ArenaCreatorController;
import com.toddydev.skywars.controllers.PlayerController;
import com.toddydev.skywars.serializer.Serializer;
import lombok.Getter;

public class Platform {

    @Getter
    private static Matches matches = new Matches();

    @Getter
    private static Serializer serializer = new Serializer();

    @Getter
    private static ArenaController arenaController = new ArenaController();

    @Getter
    private static PlayerController playerController = new PlayerController();

    @Getter
    private static ArenaCreatorController arenaCreatorController = new ArenaCreatorController();

}
