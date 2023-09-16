package com.toddydev.lobby.commands;

import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuildCommand implements CommandExecutor {

    @Getter
    private static List<String> builders = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s2, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        HyzePlayer corePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        if (cmd.getLabel().equalsIgnoreCase("build")) {
            if (corePlayer.getGroup().getRank().ordinal() <= Rank.ADMIN.ordinal() || corePlayer.getGroup().getRank().equals(Rank.BUILDER)) {
                if (args.length == 0) {
                    player.sendMessage("§cSintaxe incorreta, utilize '/build [on/off]'.");
                    return true;
                } else if (args.length > 0) {
                    String s = args[0];
                    if (s.equalsIgnoreCase("on")) {
                        if (builders.contains(player.getName())) {
                            player.sendMessage("§cVocê já está com o modo construtor ativado.");
                            return true;
                        } else {
                            builders.add(player.getName());
                            player.setGameMode(GameMode.CREATIVE);
                            player.sendMessage("§aModo construtor foi ativado.");
                            return true;
                        }
                    } else if (s.equalsIgnoreCase("off")) {
                        if (!builders.contains(player.getName())) {
                            player.sendMessage("§cVocê já está com o modo construtor desativado.");
                            return true;
                        } else {
                            builders.remove(player.getName());
                            player.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage("§aModo construtor foi desativado.");
                            return true;
                        }
                    }
                }
            } else {
                player.sendMessage("§cVocê não tem permissão para executar este comando.");
                return true;
            }
        }
        return false;
    }
}
