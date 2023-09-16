package com.toddydev.lobby.platform;

import com.toddydev.hyze.bukkit.hologram.HologramManager;
import com.toddydev.lobby.controllers.InviteController;
import com.toddydev.lobby.controllers.StatsController;
import com.toddydev.lobby.controllers.SkinController;
import com.toddydev.lobby.data.DataPlayer;
import lombok.Getter;
import lombok.Setter;

public class Platform {

    @Getter
    private static DataPlayer dataPlayer = new DataPlayer();

    @Getter
    private static SkinController skinController = new SkinController();

    @Getter
    private static StatsController statsController = new StatsController();

    @Getter
    private static InviteController inviteController = new InviteController();


    @Getter @Setter
    private static HologramManager hologramManager;
}
