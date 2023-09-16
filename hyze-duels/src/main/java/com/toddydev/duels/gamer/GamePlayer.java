package com.toddydev.duels.gamer;

import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.type.ArenaType;
import com.toddydev.duels.arena.type.sub.ArenaSubType;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

@Getter @Setter
public class GamePlayer {

    private UUID uniqueId;
    private String name;

    private Arena arena;

    public GamePlayer(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;

        arena = null;
    }

    public void addPlayer(Player player, Arena arena) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0D);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getActivePotionEffects().forEach(potionEffect -> {
            player.removePotionEffect(potionEffect.getType());
        });

        setArena(arena);

        arena.getPlayers().add(player);
        player.teleport(arena.getLobby().add(0, 1.5, 0));
        player.getInventory().setItem(4, new ItemCreator(Material.BED, "§cVoltar para o Lobby").build());
        arena.getPlayers().forEach(player1 -> {
            player1.sendMessage("§b" + player.getName() + "§e entrou na partida! §b(" + arena.getPlayers().size() + "/" + (arena.getSubType().equals(ArenaSubType.SOLO) ? 2 : 4) + ")");
        });
    }

    public void addSpec(Player player, Arena arena) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20.0D);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getActivePotionEffects().forEach(potionEffect -> {
            player.removePotionEffect(potionEffect.getType());
        });

        setArena(arena);
        arena.getPlayers().remove(player);
        arena.getSpecs().add(player);

        arena.getPlayers().forEach(players -> {
            players.hidePlayer(player);
        });

        player.teleport(arena.getLobby());
        player.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(999999, 555));

        player.setAllowFlight(true);
        player.setFlying(true);

        player.getInventory().setItem(3, new ItemCreator(Material.PAPER, "§aJogar novamente").build());
        player.getInventory().setItem(5, new ItemCreator(Material.BED, "§cVoltar para o Lobby").build());
    }

    public void leaveArena(Player player) {
        player.setHealth(20.0D);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getActivePotionEffects().forEach(potionEffect -> {
            player.removePotionEffect(potionEffect.getType());
        });

        arena.getPlayers().remove(player);
        arena.getSpecs().remove(player);

        arena.getPlayers().forEach(player1 -> {
            player1.sendMessage("§b" + player.getName() + "§e saiu da partida! §b(" + arena.getPlayers().size() + "/" + (arena.getSubType().equals(ArenaSubType.SOLO) ? 2 : 4) + ")");
        });
    }
    
    public void sendItems(Player player) {
        if (arena.getType().equals(ArenaType.SOUP)) {
            player.getInventory().clear();
            player.setHealth(20.0D);
            player.setFireTicks(0);
            player.setFoodLevel(20);

            ItemStack helmet = new ItemCreator(Material.IRON_HELMET, "§aCapacete de Ferro").build();
            ItemStack chestplate = new ItemCreator(Material.IRON_CHESTPLATE, "§aPeitoral de Ferro").build();
            ItemStack leggings = new ItemCreator(Material.IRON_LEGGINGS, "§aCalças de Ferro").build();
            ItemStack boots = new ItemCreator(Material.IRON_BOOTS, "§aBotas de Ferro").build();
            
            ItemStack sword = new ItemCreator(Material.DIAMOND_SWORD, "§aEspada de Diamante").build();
            ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSoup").build();
            
            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
            
            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, soup);
            player.getInventory().setItem(2, soup);
            player.getInventory().setItem(3, soup);
            player.getInventory().setItem(4, soup);
            player.getInventory().setItem(5, soup);
            player.getInventory().setItem(6, soup);
            player.getInventory().setItem(7, soup);
            player.getInventory().setItem(8, soup);
            return;
        }
        if (arena.getType().equals(ArenaType.UHC)) {
            player.getInventory().clear();
            player.setHealth(20.0D);
            player.setFireTicks(0);
            player.setFoodLevel(20);

            ItemStack helmet = new ItemCreator(Material.IRON_HELMET, "§aCapacete de Ferro").build();
            ItemStack chestplate = new ItemCreator(Material.IRON_CHESTPLATE, "§aPeitoral de Ferro").build();
            ItemStack leggings = new ItemCreator(Material.IRON_LEGGINGS, "§aCalças de Ferro").build();
            ItemStack boots = new ItemCreator(Material.IRON_BOOTS, "§aBotas de Ferro").build();

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);

            ItemStack sword = new ItemCreator(Material.DIAMOND_SWORD, "§aEspada de Diamante").changeId(0).build();
            ItemStack fish = new ItemCreator(Material.FISHING_ROD, "§aVara de Pesca").changeId(0).build();
            ItemStack archer = new ItemCreator(Material.BOW, "§aArco").changeId(0).build();
            ItemStack machado = new ItemCreator(Material.DIAMOND_AXE, "§aMachado de Diamante").changeId(0).build();
            ItemStack golden = new ItemCreator(Material.GOLDEN_APPLE, "§aMaça Dourada").changeId(0).changeAmount(6).build();
            ItemStack skull = new ItemCreator(Material.SKULL_ITEM, "§aGolden").changeId(0).changeAmount(3).withSkullURL("http://textures.minecraft.net/texture/4abd703e5b8c88d4b1fcfa94a936a0d6a4f6aba44569663d3391d4883223c5").build();

            ItemStack lava = new ItemCreator(Material.LAVA_BUCKET, "§aLava").changeId(0).build();
            ItemStack agua = new ItemCreator(Material.WATER_BUCKET, "§aAgua").changeId(0).build();

            ItemStack arrow = new ItemCreator(Material.ARROW, "§aFlechas").changeId(0).changeAmount(16).build();

            ItemStack madeira = new ItemCreator(Material.WOOD, "§aMadeira").changeId(0).changeAmount(64).build();

            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, fish);
            player.getInventory().setItem(2, archer);
            player.getInventory().setItem(3, machado);
            player.getInventory().setItem(4, golden);
            player.getInventory().setItem(5, skull);
            player.getInventory().setItem(6, lava);
            player.getInventory().setItem(7, agua);
            player.getInventory().setItem(8, madeira);
            player.getInventory().setItem(9, arrow);

            player.getInventory().setItem(33, lava);
            player.getInventory().setItem(34, agua);
            player.getInventory().setItem(35, madeira);
        }
        if (arena.getType().equals(ArenaType.GLADIATOR)) {
            player.getInventory().clear();
            player.setHealth(20.0D);
            player.setFireTicks(0);
            player.setFoodLevel(20);

            ItemStack helmet = new ItemCreator(Material.IRON_HELMET, "§aCapacete de Ferro").build();
            ItemStack chestplate = new ItemCreator(Material.IRON_CHESTPLATE, "§aPeitoral de Ferro").build();
            ItemStack leggings = new ItemCreator(Material.IRON_LEGGINGS, "§aCalças de Ferro").build();
            ItemStack boots = new ItemCreator(Material.IRON_BOOTS, "§aBotas de Ferro").build();

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);

            player.getInventory().setItem(9, helmet);
            player.getInventory().setItem(10, chestplate);
            player.getInventory().setItem(11, leggings);
            player.getInventory().setItem(12, boots);

            player.getInventory().setItem(18, helmet);
            player.getInventory().setItem(19, chestplate);
            player.getInventory().setItem(20, leggings);
            player.getInventory().setItem(21, boots);

            ItemStack bowl = new ItemCreator(Material.BOWL, "Bowl").changeAmount(64).build();
            ItemStack cooca = new ItemCreator(Material.INK_SACK, "Cocoa").changeAmount(64).changeId(3).build();

            player.getInventory().setItem(13, bowl);
            player.getInventory().setItem(22, bowl);
            player.getInventory().setItem(14, cooca);
            player.getInventory().setItem(23, cooca);
            player.getInventory().setItem(15, cooca);
            player.getInventory().setItem(24, cooca);
            player.getInventory().setItem(16, cooca);
            player.getInventory().setItem(25, cooca);

            ItemStack pickaxe = new ItemCreator(Material.STONE_PICKAXE, "Picareta de Pedra").build();
            ItemStack machado = new ItemCreator(Material.STONE_AXE, "Machado de Pedra").build();

            player.getInventory().setItem(17, pickaxe);
            player.getInventory().setItem(26, machado);

            ItemStack lava = new ItemCreator(Material.LAVA_BUCKET, "Balde de Lava").build();
            ItemStack mushroom = new ItemCreator(Material.MUSHROOM_SOUP, "Sopa").build();

            player.getInventory().setItem(27, lava);
            player.getInventory().setItem(28, lava);

            player.getInventory().setItem(29, mushroom);
            player.getInventory().setItem(30, mushroom);
            player.getInventory().setItem(31, mushroom);
            player.getInventory().setItem(32, mushroom);
            player.getInventory().setItem(33, mushroom);
            player.getInventory().setItem(34, mushroom);
            player.getInventory().setItem(35, mushroom);

            ItemStack madeira = new ItemCreator(Material.WOOD, "Madeira").changeId(0).changeAmount(64).build();
            ItemStack sword = new ItemCreator(Material.DIAMOND_SWORD, "Espada de Diamante").changeId(0).build();
            ItemStack agua = new ItemCreator(Material.WATER_BUCKET, "Balde de Água").build();

            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, madeira);
            player.getInventory().setItem(2, lava);
            player.getInventory().setItem(3, agua);

            player.getInventory().setItem(4, mushroom);
            player.getInventory().setItem(5, mushroom);
            player.getInventory().setItem(6, mushroom);
            player.getInventory().setItem(7, mushroom);

            ItemStack stairs = new ItemCreator(Material.COBBLE_WALL, "Cobblestone Walls").changeAmount(64).build();
            player.getInventory().setItem(8, stairs);

        }
    }
}
