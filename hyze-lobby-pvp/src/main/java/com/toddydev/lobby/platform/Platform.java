package com.toddydev.lobby.platform;

import com.toddydev.hyze.bukkit.hologram.HologramManager;
import com.toddydev.lobby.controllers.PlayerController;
import com.toddydev.lobby.controllers.SkinController;
import com.toddydev.lobby.data.DataFPSPlayer;
import lombok.Getter;
import lombok.Setter;

public class Platform {

    @Getter
    private static DataFPSPlayer dataFpsPlayer = new DataFPSPlayer();

    @Getter
    private static SkinController skinController = new SkinController();

    @Getter
    private static PlayerController playerController = new PlayerController();


    @Getter @Setter
    private static HologramManager hologramManager;
}
