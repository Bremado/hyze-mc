package com.toddydev.lava.player.damage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DamageType {

    DAMAGE_1("1.0", 1.0D),
    DAMAGE_2("2.0", 2.0D),
    DAMAGE_3("3.0", 3.0D),
    DAMAGE_4("4.0", 4.0D),
    DAMAGE_5("5.0", 5.0D);

    String display;
    double damage;
}
