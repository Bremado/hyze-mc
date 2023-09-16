package com.toddydev.duels.block;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;

import java.util.concurrent.TimeUnit;

@Getter @Setter
public class MapBlock {

    private Block block;
    private Long time;

    public MapBlock(Block block) {
        this.block = block;
        time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60);
    }
}
