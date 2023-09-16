package com.toddydev.skywars.arena.creator.listener;

import com.toddydev.skywars.arena.Arena;
import com.toddydev.skywars.arena.creator.ArenaCreator;
import com.toddydev.skywars.menus.SelectModeMenu;
import com.toddydev.skywars.platform.Platform;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaCreatorListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ArenaCreator arenaCreator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());

        if (arenaCreator == null) {
            return;
        }

        if (player.getItemInHand() == null) {
            return;
        }

        ItemStack stack = player.getItemInHand();

        if (!stack.hasItemMeta())return;
        if (!stack.getItemMeta().hasDisplayName())return;

        Arena arena = arenaCreator.getArena();

        if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§aDefinir Lobby de Espera")) {
            arena.setLobby(player.getLocation());

            player.sendMessage("");
            player.sendMessage("§6§lMODO DIRETOR:");
            player.sendMessage(" §eVocê definiu o lobby de espera.");
            player.sendMessage((arena.getLobby() != null && !arena.getSpawns().isEmpty() && arena.getType() != null && arena.getSubType() != null ? " §eClique em §bsalvar §epara concluir a criação." : " §eContinue configurando a arena."));
            player.sendMessage("");
            return;
        }

        if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§aSelecione um Modo")) {
            SelectModeMenu.open(player);
            return;
        }

        if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§aSelecione um Subtipo")) {
            SelectModeMenu.openS(player);
            return;
        }

        if (stack.getItemMeta().getDisplayName().equalsIgnoreCase("§aAdicionar Spawn")) {
            arena.addSpawn(player.getLocation());
            player.sendMessage("");
            player.sendMessage("§6§lMODO DIRETOR:");
            player.sendMessage(" §eVocê adicionou um novo spawn, agora existem §b" + arena.getSpawns().size() + "§e spawns.");
            player.sendMessage((arena.getLobby() != null && !arena.getSpawns().isEmpty() && arena.getType() != null && arena.getSubType() != null ? " §eClique em §bsalvar §epara concluir a criação." : " §eContinue configurando a arena."));
            player.sendMessage("");
            return;
        }

        if (stack.getItemMeta().getDisplayName().equals("§aSalvar")) {
            player.sendMessage("");
            player.sendMessage("§6§lMODO DIRETOR:");
            player.sendMessage(" §eVocê salvou a arena §b" + arena.getName() + "§e!");
            player.sendMessage("");
            arena.setMaxPlayers(arena.getSpawns().size());
            Platform.getMatches().create(arena);
            Platform.getArenaCreatorController().unload(player.getUniqueId());
            player.getInventory().clear();
            return;
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        ArenaCreator creator = Platform.getArenaCreatorController().getArenaCreator(player.getUniqueId());

        if (creator == null) return;
        Arena arena = creator.getArena();

        e.setCancelled(true);
        if (creator.getMinPlayers().contains(player)) {
            if (!isInteger(e.getMessage())) {
                player.sendMessage("§cVocê deve inserir um número para continuar.");
                return;
            }
            arena.setMinPlayers(Integer.parseInt(e.getMessage()));
            creator.getMinPlayers().remove(player);

            player.sendMessage("");
            player.sendMessage("§6§lMODO DIRETOR:");
            player.sendMessage(" §eO mínimo de jogadores foi alterado para §b" + e.getMessage() + "§e.");
            player.sendMessage(" §ePara continuar, determine as localizações utilizando os itens.");
            player.sendMessage("");

            creator.sendItems();
            return;
        }

    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
