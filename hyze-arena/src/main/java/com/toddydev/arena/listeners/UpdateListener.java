package com.toddydev.arena.listeners;

import com.toddydev.arena.platform.Platform;
import com.toddydev.hyze.bukkit.api.actionbar.ActionBar;
import com.toddydev.hyze.bukkit.events.update.UpdateEvent;
import com.toddydev.hyze.core.utils.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UpdateListener implements Listener {

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        Player player = e.getPlayer();

        if (Platform.getCombatLogController().exists(player.getUniqueId())) {
            if (Platform.getCombatLogController().getCombat(player.getUniqueId()).getExpires() <= System.currentTimeMillis()) {
                Platform.getCombatLogController().unload(player.getUniqueId());
                return;
            }

            ActionBar.sendActionBar(player, "§cVocê poderá deslogar em " + DateUtils.getTime(Platform.getCombatLogController().getCombat(player.getUniqueId()).getExpires()) + ".");
        }
    }
}
