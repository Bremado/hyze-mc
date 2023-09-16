package com.toddydev.lobby.listeners;

import com.toddydev.lobby.invite.Invite;
import com.toddydev.lobby.menus.InviteMenu;
import com.toddydev.lobby.platform.Platform;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InviteListener implements Listener {

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();

        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }

        Player clicked = (Player) e.getRightClicked();

        if (player.getItemInHand() != null) {
            if (player.getItemInHand().getType().equals(Material.BLAZE_ROD)) {
                Invite invite = new Invite(player, clicked);
                Platform.getInviteController().load(invite);
                InviteMenu.open(player);
            }
        }
    }
}
