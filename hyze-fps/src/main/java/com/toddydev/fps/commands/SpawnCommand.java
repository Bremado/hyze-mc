package com.toddydev.fps.commands;

import com.toddydev.fps.Main;
import com.toddydev.fps.combat.Combat;
import com.toddydev.fps.platform.Platform;
import com.toddydev.fps.player.GamePlayer;
import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

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
