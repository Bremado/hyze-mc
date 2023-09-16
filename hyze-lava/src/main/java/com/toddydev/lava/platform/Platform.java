package com.toddydev.lava.platform;

import com.toddydev.lava.controllers.PlayerController;
import com.toddydev.lava.data.DataPlayer;
import lombok.Getter;

public class Platform {

    @Getter
    private static DataPlayer dataPlayer = new DataPlayer();

    @Getter
    private static PlayerController playerController = new PlayerController();
}
