package com.toddydev.duels.commands;

import com.toddydev.duels.arena.Arena;
import com.toddydev.duels.arena.creator.ArenaCreator;
import com.toddydev.duels.platform.Platform;
import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand extends CommandBase {

    public ArenaCommand() {
        super("arena");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        if (!(hyzePlayer.getGroup().getRank() == Rank.ADMIN)) {
            player.sendMessage(NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cSintaxe incorreta, utilize '/arena criar [nome]'.");
            return true;
        }

        if (args.length > 0) {
            String s = args[0];
            if (s.equalsIgnoreCase("criar")) {
                if (args.length > 1) {
                    ArenaCreator arenaCreator = new ArenaCreator(new Arena(null, null, args[1]), player);
                    Platform.getArenaCreatorController().load(arenaCreator);
                    arenaCreator.sendItems();
                    player.sendMessage("");
                    player.sendMessage("§6§lMODO DIRETOR:");
                    player.sendMessage("  §aVocê começou a criação da arena §f" + args[1] + "§a.");
                    player.sendMessage("");
                    return true;
                }
            }
        }
        return false;
    }
}
