package com.toddydev.lobby.hologram;

import com.toddydev.hyze.bukkit.hologram.Hologram;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.lobby.platform.Platform;
import com.toddydev.lobby.player.GameFPSPlayer;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RankingHologram extends Hologram {

    private final String name, fieldName;

    public RankingHologram(@NonNull Location location, String name, String fieldName) {
        super(location);
        this.name = name;
        this.fieldName = fieldName;
    }

    public void update() throws NoSuchFieldException, IllegalAccessException {
        List<String> lines = new ArrayList<>();
        lines.add("§b§lTOP 10 §e§l" + name.toUpperCase());

        int position = 1;
        for (GameFPSPlayer smashPlayer : Platform.getDataFpsPlayer().ranking(fieldName)) {
            if (position > 10)
                continue;

            Field field = smashPlayer.getClass().getDeclaredField(fieldName);
            HyzePlayer hyzePlayer = Core.getDataPlayer().getHyzePlayer(smashPlayer.getUniqueId());

            field.setAccessible(true);
            lines.add("§e§l" + position + ". §r§7" + hyzePlayer.getGroup().getRank().getColor() + hyzePlayer.getName() + " §7- §e" +
                    field.get(smashPlayer));
            position++;
        }

        setText(lines);
    }
}
