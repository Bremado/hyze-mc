package com.toddydev.skywars.arena.type;

public enum ArenaSubType {

    SOLO,
    TEAM;

    public static ArenaSubType getByName(String name) {
        for (ArenaSubType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
