package com.toddydev.auth.platform;

import com.toddydev.auth.data.DataAuth;
import com.toddydev.auth.manager.GamerManager;
import lombok.Getter;
import lombok.Setter;

public class Platform {

    @Getter @Setter
    private static DataAuth dataAuth;

    @Getter
    private static GamerManager gamerManager = new GamerManager();
}
