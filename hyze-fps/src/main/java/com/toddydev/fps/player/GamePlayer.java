package com.toddydev.fps.player;

import com.toddydev.fps.Main;
import com.toddydev.fps.platform.Platform;
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
public class GamePlayer {

    private UUID uniqueId;

    private Integer kills;
    private Integer deaths;

    private Integer killstreak;

    private boolean protect;

    public GamePlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        kills = 0;
        deaths = 0;

        killstreak = 0;
    }

    public void refresh() {
        Player player = Main.getInstance().getServer().getPlayer(uniqueId);
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
        ItemStack bowl = new ItemCreator(Material.BOWL, "§aBowl").changeAmount(64).build();
        ItemStack red = new ItemCreator(Material.RED_MUSHROOM, "§aRed Mushroom").changeAmount(64).build();
        ItemStack brown = new ItemCreator(Material.BROWN_MUSHROOM, "§aBrown Mushroom").changeAmount(64).build();

        ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build();
        ItemStack sword = new ItemCreator(Material.DIAMOND_SWORD, "§aEspada de Diamante").build();
        ItemStack helmet = new ItemCreator(Material.IRON_HELMET, "§aCapacete de Ferro").build();
        ItemStack chestplate = new ItemCreator(Material.IRON_CHESTPLATE, "§aPeitoral de Ferro").build();
        ItemStack leggings = new ItemCreator(Material.IRON_LEGGINGS, "§aCalças de Ferro").build();
        ItemStack boots = new ItemCreator(Material.IRON_BOOTS, "§aBotas de Ferro").build();

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (i == 0) {
                player.getInventory().setItem(i, sword);
                continue;
            }

            if (i == 13) {
                player.getInventory().setItem(i, bowl);
                continue;
            }

            if (i == 14) {
                player.getInventory().setItem(i, red);
                continue;
            }

            if (i == 15) {
                player.getInventory().setItem(i, brown);
                continue;
            }

            if (player.getInventory().getItem(i) != null) {
                continue;
            }
            player.getInventory().setItem(i, soup);
        }
    }
}
