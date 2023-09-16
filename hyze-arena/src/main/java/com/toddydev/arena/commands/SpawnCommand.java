package com.toddydev.arena.commands;

import com.toddydev.arena.combat.Combat;
import com.toddydev.arena.platform.Platform;
import com.toddydev.arena.player.GamePlayer;
import com.toddydev.hyze.bukkit.commands.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends CommandBase {

    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        GamePlayer gamePlayer = Platform.getPlayerController().getGamePlayer(player.getUniqueId());

        if (args.length == 0) {
            if (Platform.getCombatLogController().exists(player.getUniqueId())) {
                Combat combat = Platform.getCombatLogController().getCombat(player.getUniqueId());
                if (combat.getExpires() <= System.currentTimeMillis()) {
                    gamePlayer.refresh();
                    player.sendMessage("§aVocê foi teleportado para o spawn.");
                    return true;
                } else {
                    player.sendMessage("§cAguarde, você ainda está em combate.");
                    return true;
                }
            } else {
                gamePlayer.refresh();
                player.sendMessage("§aVocê foi teleportado para o spawn.");
                return true;
            }
        }
        return false;
    }
}
