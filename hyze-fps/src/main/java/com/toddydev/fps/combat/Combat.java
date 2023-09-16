package com.toddydev.fps.combat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
@AllArgsConstructor
public class Combat {

    private Player player;
    private Player killer;

    private Long expires;

}
