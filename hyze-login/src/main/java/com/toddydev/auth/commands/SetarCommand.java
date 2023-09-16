package com.toddydev.auth.commands;

import com.toddydev.auth.AuthPlugin;
import com.toddydev.auth.utils.Utils;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String s2, String[] args) {
        if (!(s instanceof Player)) {
            return true;
        }

        Player player = (Player) s;
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        if (cmd.getLabel().equalsIgnoreCase("setar")) {
            if (hyzePlayer.getGroup().getRank().ordinal() <= Rank.ADMIN.ordinal()) {
                if (args.length == 0) {
                    player.sendMessage("§cSintaxe incorreta, utilize '/setar [spawn]'.");
                    return true;
                } else if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        File file = new File(AuthPlugin.getInstance().getDataFolder(), "config.yml");
                        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                        configuration.set("config.locations.spawn", Utils.serializeLocation(player.getLocation()));
                        try {
                            configuration.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendMessage("§aO spawn foi setado com sucesso.");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
