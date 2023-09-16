package com.toddydev.skywars.commands;

import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.skywars.arena.Arena;
import com.toddydev.skywars.arena.creator.ArenaCreator;
import com.toddydev.skywars.platform.Platform;
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
            player.sendMessage("§cSintaxe incorreta, utilize '/arena criar [nome] [tipo]'.");
            return true;
        }
        if (args.length > 0) {
            String s = args[0];
            if (s.equalsIgnoreCase("criar")) {
                if (args.length > 1) {
                    ArenaCreator arenaCreator = new ArenaCreator(player, new Arena(args[1], null));
                    Platform.getArenaCreatorController().load(arenaCreator);
                    player.sendMessage("");
                    player.sendMessage("§6§lMODO DIRETOR:");
                    player.sendMessage(" §eVocê começou a criação da arena §b" + args[1] + "§e.");
                    player.sendMessage(" §ePor favor, digite o mínimo de jogadores.");
                    player.sendMessage("");
                    return true;
                }
            }
        }
        return false;
    }


}
