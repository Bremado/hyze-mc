package com.toddydev.lobby.player;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.UUID;

@Getter @Setter
public class GameFPSPlayer {

    private UUID uniqueId;

    private Integer kills;
    private Integer deaths;

    private Integer killstreak;

    private boolean protect;

    public GameFPSPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        kills = 0;
        deaths = 0;

        killstreak = 0;
    }

}
