package com.toddydev.duels.arena.type.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArenaSubType {

    SOLO("1v1"),
    DUPLA("2v2");

    String name;
}
