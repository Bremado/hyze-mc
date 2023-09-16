package com.toddydev.arena.player;

import com.toddydev.arena.Main;
import com.toddydev.arena.ability.Ability;
import com.toddydev.arena.platform.Platform;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class GamePlayer {

    private UUID uniqueId;

    private Integer kills;
    private Integer deaths;

    private Integer killstreak;

    private Integer coins;

    private String kit;
    private List<String> kits = new ArrayList<>();

    private boolean protect;

    public GamePlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        kills = 0;
        deaths = 0;

        killstreak = 0;

        coins = 0;

        kit = "PvP";
        kits.add("PvP");
        kits.add("Kangaroo");
        kits.add("Anchor");
        kits.add("Fisherman");
        kits.add("Grappler");
        kits.add("Ninja");
    }

    public void refresh() {
        Player player = Main.getInstance().getServer().getPlayer(uniqueId);

        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0D);
        player.setFireTicks(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
        }

        for (PotionEffect potion : player.getActivePotionEffects()) {
            player.removePotionEffect(potion.getType());
        }

        if (Platform.getCombatLogController().exists(player.getUniqueId())) {
            Platform.getCombatLogController().unload(player.getUniqueId());
        }

        player.teleport(Main.getInstance().getLocation().getLobby());
        setProtect(true);

        ItemStack chest = new ItemCreator(Material.CHEST, "§aSelecione um Kit").build();
        ItemStack itemStack = new ItemCreator(Material.EMERALD, "§aLoja").build();

        player.getInventory().setItem(0, chest);
        player.getInventory().setItem(1, itemStack);
    }
}
