package com.toddydev.duels.arena.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArenaType {

    SIMULATOR("Simulator"),
    GLADIATOR("Gladiator"),
    SUMO("Sumo"),
    UHC("UHC"),
    SOUP("Sopa");

    String name;
}
