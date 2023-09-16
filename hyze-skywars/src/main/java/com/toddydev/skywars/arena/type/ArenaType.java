package com.toddydev.skywars.arena.type;

public enum ArenaType {

    NORMAL,
    RANQUEADO,
    DUEL,
    RUSH;


    public static ArenaType getByName(String name) {
        for (ArenaType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
