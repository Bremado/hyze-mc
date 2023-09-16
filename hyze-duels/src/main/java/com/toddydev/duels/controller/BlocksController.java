package com.toddydev.duels.controller;

import com.toddydev.duels.block.MapBlock;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BlocksController {

    private List<MapBlock> blocks = new ArrayList<>();

    public void load(MapBlock block) {
        blocks.add(block);
    }

    public List<MapBlock> getBlocks() {
        return blocks;
    }
}
