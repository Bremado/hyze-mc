package com.toddydev.lava.listener;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.lava.Main;
import com.toddydev.lava.platform.Platform;
import com.toddydev.lava.player.GamePlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.github.paperspigot.Title;

import java.text.SimpleDateFormat;

public class PlayerListeners implements Listener {


    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer;
        if (Platform.getDataPlayer().exists(player.getUniqueId())) {
            gamePlayer = Platform.getDataPlayer().getHyzePlayer(player.getUniqueId());
        } else {
            gamePlayer = new GamePlayer(player.getUniqueId());
            Platform.getDataPlayer().create(gamePlayer);
        }
        Platform.getPlayerController().load(gamePlayer);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        refresh(player);
        player.teleport(Main.getInstance().getLocation().getLobby());
        player.sendTitle(new Title("§a§lHYZE", "§eSeja bem-vindo!", 5, 20, 5));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        player.getInventory().clear();
        player.spigot().respawn();
        player.sendMessage("§cVocê morreu!");
        refresh(player);
        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 2.0f, 2.0f);
        e.setDeathMessage(null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        e.setCancelled(true);
        if (!corePlayer.getPrefs().isChat()) {
            player.sendMessage("§cVocê está com o chat desativado.");
            return;
        }

        TextComponent textComponent = new TextComponent(player.getDisplayName() + "§7: " + (corePlayer.getGroup().getTag() != Rank.MEMBRO ? "§f" + e.getMessage().replace("&", "§") : "§7" + e.getMessage()));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                TextComponent.fromLegacyText(
                        "§7Tag: " + corePlayer.getGroup().getTag().getColor() + corePlayer.getGroup().getTag().getName() + "\n" +
                                "§7Data de envio: §e" + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(System.currentTimeMillis()) + "\n \n" +
                                "§eClique para as estatísticas.")
        ));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stats " + player.getName()));

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(players.getUniqueId());
            if (hyzePlayer.getPrefs().isChat()) {
                players.sendMessage(textComponent);
            }
        });
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Platform.getPlayerController().unload(e.getPlayer().getUniqueId());
    }

    public void refresh(Player player) {
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

        player.teleport(Main.getInstance().getLocation().getLobby());
        ItemStack bowl = new ItemCreator(Material.BOWL, "§aBowl").changeAmount(64).build();
        ItemStack red = new ItemCreator(Material.RED_MUSHROOM, "§aRed Mushroom").changeAmount(64).build();
        ItemStack brown = new ItemCreator(Material.BROWN_MUSHROOM, "§aBrown Mushroom").changeAmount(64).build();

        ItemStack config = new ItemCreator(Material.REDSTONE_COMPARATOR, "§aConfigurações").build();
        ItemStack soup = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build();

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (i == 4) {
                player.getInventory().setItem(i, config);
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
