package com.toddydev.lava.commands;

import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.group.rank.Rank;
import com.toddydev.lava.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetarCommand extends CommandBase {

    public SetarCommand() {
        super("setar");
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
            File file = new File(Main.getInstance().getDataFolder(), "config.yml");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("config.locations.spawn", Main.getInstance().getLocation().serialize(player.getLocation()));
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.sendMessage("§aO spawn foi setado com sucesso.");
            return true;
        }
        return false;
    }
}
