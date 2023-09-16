package com.toddydev.fps.commands;

import com.toddydev.fps.Main;
import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildCommand extends CommandBase {

    public BuildCommand() {
        super("build");
    }

    @Getter
    private static List<Player> builders = new ArrayList<>();

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
            player.sendMessage("§cSintaxe incorreta, utilize '/build [on/off]");
            return true;
        }

        if (args.length > 0) {
            String s = args[0];
            if (s.equalsIgnoreCase("on")) {
                if (builders.contains(player)) {
                    player.sendMessage("§cVocê já está com o modo construtor ativo.");
                    return true;
                }

                builders.add(player);
                player.sendMessage("§aModo construtor ativo.");
                return true;
            } else if (s.equalsIgnoreCase("off")) {
                if (!builders.contains(player)) {
                    player.sendMessage("§cVocê já está com o modo construtor desativado.");
                    return true;
                }

                builders.add(player);
                player.sendMessage("§aModo construtor desativado.");
                return true;
            }
        }
        return false;
    }
}
